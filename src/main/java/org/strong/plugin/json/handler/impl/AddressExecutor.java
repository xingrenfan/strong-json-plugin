package org.strong.plugin.json.handler.impl;

import cn.hutool.core.util.StrUtil;
import org.strong.plugin.json.handler.DesensitizationHandler;

/**
 * 说明： 地址脱敏执行器
 * 北京市海淀区****
 *
 * @version V 1.0.0
 * @Author: Glendon.Li
 * @Create: 2021-10-27 23:51
 */
public class AddressExecutor implements DesensitizationHandler {
    @Override
    public String executor(String value, char fillChar) {
        return StrUtil.replace(value, 6, value.length(), fillChar);
    }
}
