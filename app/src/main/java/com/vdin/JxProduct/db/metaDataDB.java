package com.vdin.JxProduct.db;

import org.litepal.crud.LitePalSupport;

/**
 * @开发者 YanSY
 * @日期 2018/9/12
 * @描述 Vdin成都研发部
 */
public class metaDataDB extends LitePalSupport {

    // 移动端-登录
    private String session;
    // 移动端-激活账户
    private String confirmation;
    // 移动端-生成备案验证码
    private String confirmation_code;
    // 移动端-重置密码
    private String password_recovery;
    // 移动端-设置密码
    private String password;
    // 移动端-生成忘记密码验证码
    private String password_recovery_code;
    // 移动端-校验验证码
    private String load_password_recovery_code;
    // 移动端-获取时间
    private String get_time;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    public String getPassword_recovery() {
        return password_recovery;
    }

    public void setPassword_recovery(String password_recovery) {
        this.password_recovery = password_recovery;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_recovery_code() {
        return password_recovery_code;
    }

    public void setPassword_recovery_code(String password_recovery_code) {
        this.password_recovery_code = password_recovery_code;
    }

    public String getLoad_password_recovery_code() {
        return load_password_recovery_code;
    }

    public void setLoad_password_recovery_code(String load_password_recovery_code) {
        this.load_password_recovery_code = load_password_recovery_code;
    }

    public String getGet_time() {
        return get_time;
    }

    public void setGet_time(String get_time) {
        this.get_time = get_time;
    }
}
