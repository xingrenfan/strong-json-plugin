package org.strong.json.plugin.handler.impl;

import cn.hutool.core.util.StrUtil;
import org.strong.json.plugin.handler.DesensitizationHandler;

/**
 * 说明: 密码脱敏
 * 【密码】密码的全部字符都用*代替，比如：******
 *
 * @author: Glendon.Li
 * @date: 2023-03-09 20:25
 * @version: V1.0.0
 **/
public class PasswordExecutor implements DesensitizationHandler {
    @Override
    public String executor(String value, char fillChar) {
        return StrUtil.replace(value, 0, value.length(), fillChar);
    }
}
