package org.strong.plugin.json.handler.impl;

import cn.hutool.core.util.StrUtil;
import org.strong.plugin.json.handler.DesensitizationHandler;

/**
 * 说明: 手机号脱敏
 * 【手机号码】前三位，后4位，其他隐藏，比如135****2210
 *
 * @author: Glendon.Li
 * @date: 2023-03-09 20:25
 * @version: V1.0.0
 **/
public class PhoneExecutor implements DesensitizationHandler {
    @Override
    public String executor(String value, char fillChar) {
        return StrUtil.replace(value, 3, 7, fillChar);
    }
}
