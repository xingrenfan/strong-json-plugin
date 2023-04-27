package org.strong.plugin.json.handler.impl;

import cn.hutool.core.util.StrUtil;
import org.strong.plugin.json.handler.DesensitizationHandler;

/**
 * 说明: 邮箱脱敏
 * 【电子邮箱】邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示，比如：d**@126.com
 *
 * @author: Glendon.Li
 * @date: 2023-03-09 20:25
 * @version: V1.0.0
 **/
public class EmailExecutor implements DesensitizationHandler {
    @Override
    public String executor(String value, char fillChar) {
        return StrUtil.replace(value, 1, StrUtil.indexOf(value, '@'), fillChar);
    }
}
