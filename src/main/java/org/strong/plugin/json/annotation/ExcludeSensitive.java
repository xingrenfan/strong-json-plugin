package org.strong.plugin.json.annotation;

import org.strong.plugin.json.enums.SerializeType;
import org.strong.plugin.json.handler.DesensitizationHandler;
import org.strong.plugin.json.jackson.JacksonDesensitizationSerialize;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 说明: 脱敏-注释
 *
 * @author: Glendon.Li
 * @date: 2023-03-12 17:34
 * @version: V1.0.0
 **/
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = JacksonDesensitizationSerialize.class)
public @interface ExcludeSensitive {
    /**
     * 填充值 默认使用"*"填充
     *
     * @return
     */
    char fillChar() default '*';

    /**
     * 脱敏执行器
     *
     * @return
     */
    Class<? extends DesensitizationHandler> executor();

    /**
     * 开启脱敏 - 有些程序可能暂时不需要脱敏，以后需要先加上注解以后方便查找和修改
     *
     * @return
     */
    boolean enable() default true;

    /**
     * 排除序列化类型
     * 这里排除的不会进行脱敏
     *
     * @return {@link SerializeType[]}
     */
    SerializeType[] excludeSerializeType() default {};
}
