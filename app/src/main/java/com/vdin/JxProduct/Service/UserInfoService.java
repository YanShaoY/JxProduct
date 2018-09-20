package com.vdin.JxProduct.Service;

import com.vdin.JxProduct.Gson.LoginDataResponse;
import com.vdin.JxProduct.db.loginDataLinks;
import com.vdin.JxProduct.db.loginDataUserInfo;

import org.litepal.LitePal;

import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/19
 * @描述 Vdin成都研发部
 */
public class UserInfoService {

    private loginDataUserInfo userInfo;

    /**
     * 构造单例
     *
     * @return 返回本类实例
     */
    public static UserInfoService getInstance() {
        return UserInfoServiceHolder.instance;
    }

    private static class UserInfoServiceHolder {
        private static final UserInfoService instance = new UserInfoService();
    }

    private UserInfoService() {
    };

    /**
     * 将数据存入数据库
     *
     * @param response 请求到的数据Gson模型
     */
    public void saveJsonToDataBase(LoginDataResponse response) {

        // 删除所有用户数据
        LitePal.deleteAll(loginDataUserInfo.class);
        LitePal.deleteAll(loginDataLinks.class);

        // 获取collection数据
        LoginDataResponse.CollectionBean collectionBean = response.getCollection().get(0);

        // 赋值数据库
        loginDataUserInfo userInfo = new loginDataUserInfo();

        userInfo.setHead_photo_url(collectionBean.getHead_photo_url());
        userInfo.setHotel_code(collectionBean.getHotel_code());
        userInfo.setHotel_pay_end_date(collectionBean.getHotel_pay_end_date());
        userInfo.setIdentification_number(collectionBean.getIdentification_number());
        userInfo.setName(collectionBean.getName());
        userInfo.setPhone_number(collectionBean.getPhone_number());
        userInfo.setPlace_id(collectionBean.getPlace_id());
        userInfo.setPlace_name(collectionBean.getPlace_name());
        userInfo.setPractitioner_id(collectionBean.getPractitioner_id());
        userInfo.setSession_id(collectionBean.getSession_id());
        userInfo.setSex(collectionBean.getSex());
        userInfo.setTencent_im_sign(collectionBean.getTencent_im_sign());

        this.userInfo = userInfo;
        userInfo.save();

        // 遍历Links集合 并存入links中
        for (LoginDataResponse.CollectionBean.LinksBean linksBean : collectionBean.getLinks()) {

            loginDataLinks dataLinks = new loginDataLinks();
            dataLinks.setRel(linksBean.getRel());
            dataLinks.setHref(linksBean.getHref());
            dataLinks.setTitle(linksBean.getTitle());
            dataLinks.save();
        }
    }

    /**
     * 获取用户个人信息
     *
     * @return 返回用户个人信息
     */
    public loginDataUserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * 传入接口对应的rel 查询接口
     *
     * @param rel 接口对应的rel
     * @return 查询到的接口url
     */
    public String queryUrlWithRel(String rel) {
        List<loginDataLinks> linkInfos = LitePal.select("href")
                .where("rel = ?", rel)
                .limit(1)
                .find(loginDataLinks.class);
        if (linkInfos.size() > 0) {
            String href = linkInfos.get(0).getHref();
            return href;
        }
        return "";
    }

    //获取登出接口
    public String getLogoutUrl() {
        return queryUrlWithRel("dosser/destroy/session");
    }

    //获取 APP 配置接口
    public String getAppConfigureUrl() {
        return queryUrlWithRel("dosser/load/app_configuration");
    }

    //获取 oss 配置接口
    public String getOssConfigureUrl() {
        return queryUrlWithRel("dosser/load/oss_secret");
    }

    //查询位置跟踪策略接口
    public String getLocationProfileUrl() {
        return queryUrlWithRel("dosser/load/tracking_profile");
    }

    //查询电子工牌接口
    public String getBadgeUrl() {
        return queryUrlWithRel("dosser/load/badge");
    }

    //身份证复用查询
    public String getReIdentityCardUrl() {
        return queryUrlWithRel("dosser/load/re_use_identity_card");
    }

    //新增报警
    public String getAlarmUrl() {
        return queryUrlWithRel("dosser/create/alarm");
    }

    //查询消息
    public String getMessageUrl() {
        return queryUrlWithRel("dosser/load/message");
    }

    //查询通知通告类型
    public String getMessageClassUrl() {
        return queryUrlWithRel("dosser/show/message_class");
    }

    //webview消息详情
    public String getWebMessageUrl() {
        return queryUrlWithRel("webview/show/message");
    }

    //签收消息
    public String getMessageReadUrl() {
        return queryUrlWithRel("dosser/create/message_reading");
    }

    //上传位置跟踪
    public String getPositionTrackingsUrl() {
        return queryUrlWithRel("dosser/create/position_trackings");
    }

    //查询室内定位信标的相对位置
    public String getBeaconsUrl() {
        return queryUrlWithRel("dosser/load/beacons");
    }

    //查询新版本信息
    public String getReleaseUrl() {
        return queryUrlWithRel("dosser/index/release");
    }

    //查询皮肤信息
    public String getAppSkinUrl() {
        return queryUrlWithRel("dosser/index/app_skin");
    }

    //查询广告信息
    public String getAppAdvertisementUrl() {
        return queryUrlWithRel("dosser/index/app_advertisement");
    }

    //查询好友
    public String getFriendUrl() {
        return queryUrlWithRel("dosser/index/friend");
    }

    //查询字典展示
    public String getDictDisplaysUrl() {
        return queryUrlWithRel("dosser/index/sys_dict_displays");
    }

    //新增登记
    public String getAddRegisterUrl() {
        return queryUrlWithRel("dosser/create/vehicle_maintenances");
    }

    //历史业务查询
    public String getVehicleMaintenancesUrl() {
        return queryUrlWithRel("dosser/load/vehicle_maintenances");
    }

    //历史业务详情
    public String getVehicleMaintenancesInfoUrl() {
        return queryUrlWithRel("dosser/show/vehicle_maintenance_infos");
    }

}



















