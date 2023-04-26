package org.strong.json.plugin.handler.impl;

import cn.hutool.core.util.StrUtil;
import org.strong.json.plugin.handler.DesensitizationHandler;

/**
 * 说明: 中文名脱敏执行器
 * * 李**
 *
 * @author: Glendon.Li
 * @date: 2023-03-13 10:16
 * @version: V1.0.0
 **/
public class ChineseNameExecutor implements DesensitizationHandler {
    @Override
    public String executor(String value, char fillChar) {
        return StrUtil.replace(value, 1, value.length(), fillChar);
    }
}
