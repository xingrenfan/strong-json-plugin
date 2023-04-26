package org.strong.json.plugin.handler;

/**
 * 说明： 脱敏程序
 *
 * @version V 1.0.0
 * @Author: Glendon.Li
 * @Create: 2021-10-25 22:54
 */
public interface DesensitizationHandler {
    /**
     * 脱敏执行器
     *
     * @param value    需要脱敏数据
     * @param fillChar 填充字符
     * @return {@link String}
     */
    String executor(String value, char fillChar);
}
