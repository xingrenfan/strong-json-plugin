package org.strong.plugin.json.handler.impl;

import cn.hutool.core.util.StrUtil;
import org.strong.plugin.json.handler.DesensitizationHandler;

/**
 * 说明: 汽车拍照
 * 陕A12345D -》 陕A1****D
 *
 * @author: Glendon.Li
 * @date: 2023-03-09 20:35
 * @version: V1.0.0
 **/
public class CarLicenseExecutor implements DesensitizationHandler {
    @Override
    public String executor(String value, char fillChar) {
        return StrUtil.replace(value, 3, value.length() - 1, fillChar);
    }
}
