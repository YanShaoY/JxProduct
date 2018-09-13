package com.vdin.JxProduct.Gson;

import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/13
 * @描述 Vdin成都研发部
 */
public class LoginDataResponse {

    /**
     * success : true
     * code : success
     * message : 成功
     * size : 1
     * collection : [{"head_photo_url":"http://oss-cn-qingdao.aliyuncs.com/picasso-dev/default_head.png","hotel_code":"","hotel_pay_end_date":"","identification_number":"7768776677","links":[{"rel":"dosser/destroy/session","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f.json","title":"移动端-登出"},{"rel":"dosser/create/password","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/passwords/~","title":"移动端-直接修改密码"},{"rel":"dosser/get/session","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f.json","title":"移动端-获取session"},{"rel":"dosser/load/app_configuration","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/app_configurations.json","title":"移动端-获得app配置"},{"rel":"dosser/load/oss_secret","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/oss_secrets.json","title":"移动端-获得oss配置"},{"rel":"dosser/load/tracking_profile","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/tracking_profiles.json","title":"移动端-查询位置跟踪策略"},{"rel":"dosser/load/badge","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/badges.json","title":"移动端-查询电子工牌"},{"rel":"dosser/load/re_use_identity_card","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/re_use_identity_cards.json","title":"移动端-身份证复用查询"},{"rel":"dosser/create/alarm","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/alarms.json","title":"移动端-新增报警"},{"rel":"dosser/load/message","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/messages.json","title":"移动端-查询消息"},{"rel":"dosser/show/message_class","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/message_classes.json","title":"移动端-查询通知通告类型"},{"rel":"webview/show/message","href":"http://zdht-cd.imwork.net:5432/picasso-lan-web-source/public/assets/governance/v1/message_details_app_show.html?id={id}&session_id=0d6c7620-f23f-426d-980e-98e68d4a769f","title":"移动端-webview消息详情"},{"rel":"dosser/create/message_reading","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/messages/{id}/readings.json","title":"移动端-签收消息"},{"rel":"dosser/create/position_trackings","href":"http://zdht-cd.imwork.net:5432/roraima-wan/practitioner-api/v1/practitioner/0d6c7620-f23f-426d-980e-98e68d4a769f/position_trackings.json","title":"上传位置跟踪"},{"rel":"dosser/load/beacons","href":"http://zdht-cd.imwork.net:5432/roraima-wan/practitioner-api/v1/practitioner/0d6c7620-f23f-426d-980e-98e68d4a769f/beacons.json","title":"查询室内定位信标的相对位置"},{"rel":"dosser/index/app_info","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/app_packages.json","title":"web端-查询app信息"},{"rel":"dosser/create/app_info","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/app_packages.json","title":"web端-新建app信息"},{"rel":"dosser/create/release","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/releases.json","title":"web端-新增版本信息"},{"rel":"dosser/index/app_skin","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/app_skins.json","title":"web端-查询皮肤信息"},{"rel":"dosser/index/app_advertisement","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/app_advertisements.json","title":"web端-查询广告信息"},{"rel":"dosser/index/release","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/releases.json","title":"移动端-查询新版本信息"},{"rel":"dosser/index/app_skin","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/app_skins.json","title":"移动端-查询皮肤信息"},{"rel":"dosser/index/app_advertisement","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/app_advertisements.json","title":"移动端-查询广告信息"},{"rel":"dosser/load/messages_legacy","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/messages.json","title":"移动端-查询消息（一体机使用）"},{"rel":"dosser/index/friend","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/friends.json","title":"移动端-查询好友"},{"rel":"dosser/index/entity_attribute_defines","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/entity_attribute_defines.json","title":"查询扩展数据字段"},{"rel":"dosser/index/sys_dict_displays","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/sys_dict_displays.json","title":"查询字典展示"},{"rel":"dosser/create/vehicle_maintenances","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/vehicle_maintenances.json","title":"新增登记"},{"rel":"dosser/load/vehicle_maintenances","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/vehicle_maintenances.json","title":"历史业务查询"},{"rel":"dosser/show/vehicle_maintenance_infos","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/vehicle_maintenance_infos/{maintenanceId}.json","title":"历史业务详情"}],"name":"李二","phone_number":"14258585201","place_id":"","place_name":"test机动车维修业","practitioner_id":"08b009fc-0e01-4979-b2e8-a15a63d8d6eb","session_id":"0d6c7620-f23f-426d-980e-98e68d4a769f","sex":"男","tencent_im_sign":"eJxlj01TgzAURff8Coatoi98Ju7UtkhtUQSt7YYhJNhMKQUaq*D431XsjMy4Pufde9*HoqqqFs*iszTLdq*lTGRbcU29UDXQTv9gVQmWpDIxG-YP8vdKNDxJc8mbHiLbtg2AoSMYL6XIxdEATAFInunAAekWcYlODY71FNmpYzLMHE4H13u2SfoJv-HWdzZg07GGinjp4Xy8vPbD0eakg0M7Qef3Bho56zr33ooid-gqoOChKIjvFjhYtUU9Cf31ZTCzb8ZV7YXzJ1qlsdEdyIK0bReQZ-dxG-lTuWR0eiVu5cOgUootP-5rOhhhF6MBPfBmL3ZlLxiAbGQA*dmtKZ-KF83GZIg_"}]
     * errors : {}
     * meta : {"requestId":"41890595-50b5-43a1-89f9-223846987e0b","timestamp":1536818781198}
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
         * requestId : 41890595-50b5-43a1-89f9-223846987e0b
         * timestamp : 1536818781198
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
         * head_photo_url : http://oss-cn-qingdao.aliyuncs.com/picasso-dev/default_head.png
         * hotel_code :
         * hotel_pay_end_date :
         * identification_number : 7768776677
         * links : [{"rel":"dosser/destroy/session","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f.json","title":"移动端-登出"},{"rel":"dosser/create/password","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/passwords/~","title":"移动端-直接修改密码"},{"rel":"dosser/get/session","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f.json","title":"移动端-获取session"},{"rel":"dosser/load/app_configuration","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/app_configurations.json","title":"移动端-获得app配置"},{"rel":"dosser/load/oss_secret","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/oss_secrets.json","title":"移动端-获得oss配置"},{"rel":"dosser/load/tracking_profile","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/tracking_profiles.json","title":"移动端-查询位置跟踪策略"},{"rel":"dosser/load/badge","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/badges.json","title":"移动端-查询电子工牌"},{"rel":"dosser/load/re_use_identity_card","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/re_use_identity_cards.json","title":"移动端-身份证复用查询"},{"rel":"dosser/create/alarm","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/alarms.json","title":"移动端-新增报警"},{"rel":"dosser/load/message","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/messages.json","title":"移动端-查询消息"},{"rel":"dosser/show/message_class","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/message_classes.json","title":"移动端-查询通知通告类型"},{"rel":"webview/show/message","href":"http://zdht-cd.imwork.net:5432/picasso-lan-web-source/public/assets/governance/v1/message_details_app_show.html?id={id}&session_id=0d6c7620-f23f-426d-980e-98e68d4a769f","title":"移动端-webview消息详情"},{"rel":"dosser/create/message_reading","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/messages/{id}/readings.json","title":"移动端-签收消息"},{"rel":"dosser/create/position_trackings","href":"http://zdht-cd.imwork.net:5432/roraima-wan/practitioner-api/v1/practitioner/0d6c7620-f23f-426d-980e-98e68d4a769f/position_trackings.json","title":"上传位置跟踪"},{"rel":"dosser/load/beacons","href":"http://zdht-cd.imwork.net:5432/roraima-wan/practitioner-api/v1/practitioner/0d6c7620-f23f-426d-980e-98e68d4a769f/beacons.json","title":"查询室内定位信标的相对位置"},{"rel":"dosser/index/app_info","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/app_packages.json","title":"web端-查询app信息"},{"rel":"dosser/create/app_info","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/app_packages.json","title":"web端-新建app信息"},{"rel":"dosser/create/release","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/releases.json","title":"web端-新增版本信息"},{"rel":"dosser/index/app_skin","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/app_skins.json","title":"web端-查询皮肤信息"},{"rel":"dosser/index/app_advertisement","href":"http://zdht-cd.imwork.net:5432/picasso-wan/governance-api/v1/app_advertisements.json","title":"web端-查询广告信息"},{"rel":"dosser/index/release","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/releases.json","title":"移动端-查询新版本信息"},{"rel":"dosser/index/app_skin","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/app_skins.json","title":"移动端-查询皮肤信息"},{"rel":"dosser/index/app_advertisement","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/app_advertisements.json","title":"移动端-查询广告信息"},{"rel":"dosser/load/messages_legacy","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/messages.json","title":"移动端-查询消息（一体机使用）"},{"rel":"dosser/index/friend","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/friends.json","title":"移动端-查询好友"},{"rel":"dosser/index/entity_attribute_defines","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/entity_attribute_defines.json","title":"查询扩展数据字段"},{"rel":"dosser/index/sys_dict_displays","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/sys_dict_displays.json","title":"查询字典展示"},{"rel":"dosser/create/vehicle_maintenances","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/vehicle_maintenances.json","title":"新增登记"},{"rel":"dosser/load/vehicle_maintenances","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/vehicle_maintenances.json","title":"历史业务查询"},{"rel":"dosser/show/vehicle_maintenance_infos","href":"http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f/vehicle_maintenance_infos/{maintenanceId}.json","title":"历史业务详情"}]
         * name : 李二
         * phone_number : 14258585201
         * place_id :
         * place_name : test机动车维修业
         * practitioner_id : 08b009fc-0e01-4979-b2e8-a15a63d8d6eb
         * session_id : 0d6c7620-f23f-426d-980e-98e68d4a769f
         * sex : 男
         * tencent_im_sign : eJxlj01TgzAURff8Coatoi98Ju7UtkhtUQSt7YYhJNhMKQUaq*D431XsjMy4Pufde9*HoqqqFs*iszTLdq*lTGRbcU29UDXQTv9gVQmWpDIxG-YP8vdKNDxJc8mbHiLbtg2AoSMYL6XIxdEATAFInunAAekWcYlODY71FNmpYzLMHE4H13u2SfoJv-HWdzZg07GGinjp4Xy8vPbD0eakg0M7Qef3Bho56zr33ooid-gqoOChKIjvFjhYtUU9Cf31ZTCzb8ZV7YXzJ1qlsdEdyIK0bReQZ-dxG-lTuWR0eiVu5cOgUootP-5rOhhhF6MBPfBmL3ZlLxiAbGQA*dmtKZ-KF83GZIg_
         */

        private String head_photo_url;
        private String hotel_code;
        private String hotel_pay_end_date;
        private String identification_number;
        private String name;
        private String phone_number;
        private String place_id;
        private String place_name;
        private String practitioner_id;
        private String session_id;
        private String sex;
        private String tencent_im_sign;
        private List<LinksBean> links;

        public String getHead_photo_url() {
            return head_photo_url;
        }

        public void setHead_photo_url(String head_photo_url) {
            this.head_photo_url = head_photo_url;
        }

        public String getHotel_code() {
            return hotel_code;
        }

        public void setHotel_code(String hotel_code) {
            this.hotel_code = hotel_code;
        }

        public String getHotel_pay_end_date() {
            return hotel_pay_end_date;
        }

        public void setHotel_pay_end_date(String hotel_pay_end_date) {
            this.hotel_pay_end_date = hotel_pay_end_date;
        }

        public String getIdentification_number() {
            return identification_number;
        }

        public void setIdentification_number(String identification_number) {
            this.identification_number = identification_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }

        public String getPractitioner_id() {
            return practitioner_id;
        }

        public void setPractitioner_id(String practitioner_id) {
            this.practitioner_id = practitioner_id;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTencent_im_sign() {
            return tencent_im_sign;
        }

        public void setTencent_im_sign(String tencent_im_sign) {
            this.tencent_im_sign = tencent_im_sign;
        }

        public List<LinksBean> getLinks() {
            return links;
        }

        public void setLinks(List<LinksBean> links) {
            this.links = links;
        }

        public static class LinksBean {
            /**
             * rel : dosser/destroy/session
             * href : http://zdht-cd.imwork.net:5432/picasso-wan/practitioner-api/v1/sessions/0d6c7620-f23f-426d-980e-98e68d4a769f.json
             * title : 移动端-登出
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
