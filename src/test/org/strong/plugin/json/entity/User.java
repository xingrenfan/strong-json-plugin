package org.strong.plugin.json.entity;

import org.strong.plugin.json.annotation.ExcludeSensitive;
import org.strong.plugin.json.handler.impl.EmailExecutor;
import org.strong.plugin.json.handler.impl.PhoneExecutor;

import java.io.Serializable;

public class User implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    @ExcludeSensitive(executor = PhoneExecutor.class)
    private String phone;

    /**
     * 电子邮件
     */
    @ExcludeSensitive(executor = EmailExecutor.class)
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private static final long serialVersionUID = 1L;
}