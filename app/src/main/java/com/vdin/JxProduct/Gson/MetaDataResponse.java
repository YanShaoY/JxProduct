package com.vdin.JxProduct.Gson;

import java.util.List;

/**
 * 元数据返回
 * @开发者 YanSY
 * @日期 2018/9/12
 * @描述 Vdin成都研发部
 */
public class MetaDataResponse {

    /**
     * success : true
     * code : success
     * message : 成功
     * size : 1
     * collection : [{"name":"治安综合平台门户API v1(从业者)","links":[{"rel":"dosser/create/session","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/sessions.json","title":"移动端-登录"},{"rel":"dosser/create/confirmation","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_activations.json","title":"移动端-激活账户"},{"rel":"dosser/create/confirmation_code","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_activation_sms_tokens.json","title":"移动端-生成备案验证码"},{"rel":"dosser/create/password_recovery","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_passwords.json","title":"移动端-重置密码"},{"rel":"dosser/create/password","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_passwords.json","title":"移动端-设置密码"},{"rel":"dosser/create/password_recovery_code","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_sms_tokens.json","title":"移动端-生成忘记密码验证码"},{"rel":"dosser/load/password_recovery_code","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_sms_tokens.json","title":"移动端-校验验证码"},{"rel":"dosser/get/time","href":"http://192.168.0.226/picasso-wan/common-api/v1/time","title":"移动端-获取时间"},{"rel":"dosser/load/lrs_records","href":"http://192.168.0.226/picasso-wan/lrs-api/v3/practitioners/records.json","title":"长租短租app从业人员注册"},{"rel":"dosser/create/sessions","href":"http://192.168.0.226/picasso-wan/governance-api/app/v1/sessions.json","title":"警用app登录"}]}]
     * errors : {}
     * meta : {"requestId":"9650afc2-681a-4fcf-9687-afb189c18743","timestamp":1536723330252}
     */

    private boolean success;
    private String code;
    private String message;
    private int size;
    private ErrorsBean errors;
    private MetaBean meta;
    private List<CollectionBean> collection;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ErrorsBean getErrors() {
        return errors;
    }

    public void setErrors(ErrorsBean errors) {
        this.errors = errors;
    }

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<CollectionBean> getCollection() {
        return collection;
    }

    public void setCollection(List<CollectionBean> collection) {
        this.collection = collection;
    }

    public static class ErrorsBean {
    }

    public static class MetaBean {
        /**
         * requestId : 9650afc2-681a-4fcf-9687-afb189c18743
         * timestamp : 1536723330252
         */

        private String requestId;
        private long timestamp;

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class CollectionBean {
        /**
         * name : 治安综合平台门户API v1(从业者)
         * links : [{"rel":"dosser/create/session","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/sessions.json","title":"移动端-登录"},{"rel":"dosser/create/confirmation","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_activations.json","title":"移动端-激活账户"},{"rel":"dosser/create/confirmation_code","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_activation_sms_tokens.json","title":"移动端-生成备案验证码"},{"rel":"dosser/create/password_recovery","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_passwords.json","title":"移动端-重置密码"},{"rel":"dosser/create/password","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_passwords.json","title":"移动端-设置密码"},{"rel":"dosser/create/password_recovery_code","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_sms_tokens.json","title":"移动端-生成忘记密码验证码"},{"rel":"dosser/load/password_recovery_code","href":"http://192.168.0.226/picasso-wan/practitioner-api/v1/session_sms_tokens.json","title":"移动端-校验验证码"},{"rel":"dosser/get/time","href":"http://192.168.0.226/picasso-wan/common-api/v1/time","title":"移动端-获取时间"},{"rel":"dosser/load/lrs_records","href":"http://192.168.0.226/picasso-wan/lrs-api/v3/practitioners/records.json","title":"长租短租app从业人员注册"},{"rel":"dosser/create/sessions","href":"http://192.168.0.226/picasso-wan/governance-api/app/v1/sessions.json","title":"警用app登录"}]
         */

        private String name;
        private List<LinksBean> links;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<LinksBean> getLinks() {
            return links;
        }

        public void setLinks(List<LinksBean> links) {
            this.links = links;
        }

        public static class LinksBean {
            /**
             * rel : dosser/create/session
             * href : http://192.168.0.226/picasso-wan/practitioner-api/v1/sessions.json
             * title : 移动端-登录
             */

            private String rel;
            private String href;
            private String title;

            public String getRel() {
                return rel;
            }

            public void setRel(String rel) {
                this.rel = rel;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
