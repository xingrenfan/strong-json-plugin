package org.strong.plugin.json.fastjson;

import org.strong.plugin.json.annotation.ExcludeSensitive;
import org.strong.plugin.json.enums.SerializeType;
import org.strong.plugin.json.handler.DesensitizationHandler;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 说明: fastjson脱敏程序
 *
 * @author: Glendon.Li
 * @date: 2023-03-12 17:31
 * @version: V1.0.0
 **/
public class FastDesensitizationSerialize implements ValueFilter {
    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(FastDesensitizationSerialize.class);

    @Override
    public Object process(Object object, String name, Object value) {
        if (Objects.nonNull(value) && value instanceof String && Objects.toString(value, "").length() != 0) {
            // 不为空 判断是否脱敏
            try {
                Field field = object.getClass().getDeclaredField(name);
                // 获取字段上注解
                ExcludeSensitive excludeSensitive = field.getAnnotation(ExcludeSensitive.class);
                if (enableDesensitization(excludeSensitive)) {
                    // 获取脱敏处理程序
                    Class<? extends DesensitizationHandler> desensitizationHandler = excludeSensitive.executor();
                    if (Objects.nonNull(desensitizationHandler)) {
                        // 脱敏执行器
                        DesensitizationHandler desensitizationExecutor = desensitizationHandler.newInstance();
                        logger.debug("Fastjson desensitized source value:{}", value);
                        String desensitizedValue = desensitizationExecutor.executor(Objects.toString(value), excludeSensitive.fillChar());
                        logger.debug("Fastjson desensitized end value:{}", desensitizedValue);
                        return desensitizedValue;
                    }
                }
            } catch (Exception e) {
                logger.error("Fastjson serialize desensitization fail skip, ", e);
                return value;
            }
        }
        return value;
    }

    /**
     * 开启脱敏程序
     *
     * @param excludeSensitive 排除敏感
     * @return boolean
     */
    private boolean enableDesensitization(ExcludeSensitive excludeSensitive) {
        return Objects.nonNull(excludeSensitive) &&
                excludeSensitive.enable() &&
                Objects.nonNull(excludeSensitive.executor()) &&
                !Stream.of(excludeSensitive.excludeSerializeType()).anyMatch(dto -> SerializeType.FASTJSON.equals(dto));
    }
}
