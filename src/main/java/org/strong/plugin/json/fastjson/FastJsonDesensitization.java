package org.strong.plugin.json.fastjson;

import org.strong.plugin.json.handler.DesensitizationHandler;
import com.alibaba.fastjson.JSON;

import java.util.Objects;

/**
 * 说明: Json序列化程序 继承${@link JSON}
 *
 * @author: Glendon.Li
 * @date: 2023-03-13 12:44
 * @version: V1.0.0
 **/
public class FastJsonDesensitization extends JSON {
    /**
     * 对象数据脱敏
     *
     * @param object 对象
     * @return {@link String}
     */
    public static String toJsonString(Object object) {
        return toJSONString(object, new FastDesensitizationSerialize());
    }

    /**
     * 字符串脱敏
     *
     * @param content                内容
     * @param desensitizationHandler 脱敏处理程序 不能为空
     * @param fillChar               填充字符 可为空
     * @return {@link String}
     */
    public static String toDesensitization(String content, DesensitizationHandler desensitizationHandler, char fillChar) {
        if (Objects.isNull(desensitizationHandler)) {
            throw new RuntimeException("Please specify desensitization handler.");
        }
        return desensitizationHandler.executor(content, Objects.isNull(fillChar) ? '*' : fillChar);
    }
}
