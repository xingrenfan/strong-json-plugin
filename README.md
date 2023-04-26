# json-data-plugin

## 介绍

Json数据序列化时脱敏工具，支持日志脱敏、接口数据脱敏。再也不用为敏感信息的输出而烦恼。

### 依赖

+ [x] `springboot` 2.6.2
+ [x] `jdk` 1.8+
+ [x] `jackson` 2.13.5
+ [x] `fastjson` 1.2.83
+ [x] `hutool-core` 5.8.15

## 安装教程

### 使用说明

#### 添加依赖

```xml

<dependency>
    <groupId>cn.strong.plugin.json</groupId>
    <artifactId>json-data-plugin</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### 代码中使用

##### 字段脱敏

1. 添加注解，并且指定脱敏执行器

```
    @ExcludeSensitive(executor = PhoneExecutor.class)
    private String phone;
```

2. 排除脱敏框架

```
    @ExcludeSensitive(executor = PhoneExecutor.class, excludeSerializeType = {SerializeType.FASTJSON})
    private String phone;
```

说明：fastjson框架序列化的代码不脱敏。目前只支持JACKSON(jackson框架)和FASTJSON(fastjson框架)

3. 关闭脱敏(本地调试时可能不需要脱敏)

```
    @ExcludeSensitive(executor = PhoneExecutor.class, enable = false, excludeSerializeType = {SerializeType.FASTJSON})
    private String phone;
```

##### 内置脱敏执行器

+ [x] 地址脱敏 AddressExecutor.class
+ [x] 车牌 CarLicenseExecutor.class
+ [x] 中文名 ChineseNameExecutor.class
+ [x] 邮箱 EmailExecutor.class
+ [x] 固话 FixedPhoneExecutor.class
+ [x] 身份证 IdCardExecutor.class
+ [x] 密码 PasswordExecutor.class
+ [x] 手机号 PhoneExecutor.class

##### fastjson特别说明

由于fastjson特殊性，需要注意以下情况：

1. 在日志输出时改变一下写法

```java
import cn.hutool.core.collection.ListUtil;
import User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class FastJsonTest {
    private Logger logger = LoggerFactory.getLogger(FastJsonTest.class);

    @Test
    void toJsonString() throws JsonProcessingException {
        User contact = new User();
        contact.setPhone("12345678901");
        contact.setName("Glendon");
        contact.setEmail("Glendon@163.com");
        List<User> result = ListUtil.toList(contact);
        // 使用fastjson输出信息
        logger.info("Fastjson message:{}", FastJson.toJsonString(result));
    }
}
```

2. 配置全局默认转换(覆盖默认的HttpMessageConverters，spring默认使用jackson)

```java
import FastDesensitizationSerialize;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明: 默认转换器 覆盖默认的HttpMessageConverters
 *
 * @author: Glendon.Li
 * @date: 2023-03-13 15:40
 * @version: V1.0.0
 **/
@Configuration
public class FastJsonWebSerializationConfiguration implements WebMvcConfigurer {


    @Bean(name = "httpMessageConverters")
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1.定义一个converters转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 中文乱码解决方案
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);//设定json格式且编码为UTF-8
        fastConverter.setSupportedMediaTypes(mediaTypes);
        fastJsonConfig.setSerializeFilters(new FastDesensitizationSerialize());//添加自己写的拦截器
        // 3.在converter中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4.将converter赋值给HttpMessageConverter
        HttpMessageConverter<?> converter = fastConverter;
        // 5.返回HttpMessageConverters对象
        return new HttpMessageConverters(converter);
    }
}
```

# 开源协议

本程序使用MIT开源协议，若需要使用或者修改请联系本人获取授权。
