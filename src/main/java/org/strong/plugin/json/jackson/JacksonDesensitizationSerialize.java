package org.strong.plugin.json.jackson;

import org.strong.plugin.json.annotation.ExcludeSensitive;
import org.strong.plugin.json.enums.SerializeType;
import org.strong.plugin.json.handler.DesensitizationHandler;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 说明: Jackson脱敏程序
 *
 * @author: Glendon.Li
 * @date: 2023-03-12 17:31
 * @version: V1.0.0
 **/
public class JacksonDesensitizationSerialize<T> extends JsonSerializer<String> implements ContextualSerializer {
    /**
     * 日志记录器
     */
    private final Logger logger = LoggerFactory.getLogger(JacksonDesensitizationSerialize.class);

    private DesensitizationHandler desensitizationHandler;

    private ExcludeSensitive excludeSensitive;

    public JacksonDesensitizationSerialize() {
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if (Objects.nonNull(desensitizationHandler)) {
            logger.debug("Jackson desensitized source value:{}", value);
            String desensitizedValue = desensitizationHandler.executor(value, excludeSensitive.fillChar());
            logger.debug("Jackson desensitized end value:{}", desensitizedValue);
            jsonGenerator.writeString(desensitizedValue);
        } else {
            jsonGenerator.writeString(value);
        }
    }


    public JacksonDesensitizationSerialize(final DesensitizationHandler handler, final ExcludeSensitive excludeSensitive) {
        this.desensitizationHandler = handler;
        this.excludeSensitive = excludeSensitive;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        try {
            if (beanProperty != null) {
                ExcludeSensitive excludeSensitive = beanProperty.getAnnotation(ExcludeSensitive.class);
                if (Objects.isNull(excludeSensitive)) {
                    excludeSensitive = beanProperty.getContextAnnotation(ExcludeSensitive.class);
                }
                // 如果能得到注解
                if (enableDesensitization(excludeSensitive)) {
                    // 获取脱敏处理程序
                    Class<? extends DesensitizationHandler> desensitizationHandler = excludeSensitive.executor();
                    if (Objects.nonNull(desensitizationHandler)) {
                        // 执行脱敏
                        return new JacksonDesensitizationSerialize(desensitizationHandler.newInstance(), excludeSensitive);
                    }
                }
                return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
            }
        } catch (Exception e) {

        }
        return serializerProvider.findNullValueSerializer(beanProperty);
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
                !Stream.of(excludeSensitive.excludeSerializeType()).anyMatch(dto -> SerializeType.JACKSON.equals(dto));
    }
}
