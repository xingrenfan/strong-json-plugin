package org.strong.plugin.json.handler.impl;

import cn.hutool.core.util.StrUtil;
import org.strong.plugin.json.handler.DesensitizationHandler;

/**
 * 说明: 身份证号脱敏
 * 【身份证号】前3位 和后4位
 *
 * @author: Glendon.Li
 * @date: 2023-03-09 20:25
 * @version: V1.0.0
 **/
public class IdCardExecutor implements DesensitizationHandler {
    @Override
    public String executor(String value, char fillChar) {
        return StrUtil.replace(value, 3, value.length() - 4, fillChar);
    }
}
