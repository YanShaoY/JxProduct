package com.vdin.JxProduct.db;

import com.vdin.JxProduct.Gson.LoginDataResponse;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/13
 * @描述 Vdin成都研发部
 */
public class loginDataUserInfo extends LitePalSupport {

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
    private List<loginDataLinks> links = new ArrayList<loginDataLinks>();

    public List<loginDataLinks> getLinks() {
        return links;
    }

    public void setLinks(List<loginDataLinks> links) {
        this.links = links;
    }

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
}
