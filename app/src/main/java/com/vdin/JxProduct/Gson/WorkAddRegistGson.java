package com.vdin.JxProduct.Gson;

import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/20
 * @描述 Vdin成都研发部
 */
public class WorkAddRegistGson {

    /**
     * brand : 奥迪A6
     * brithDate : 2017-05-23
     * carNumber : 川A-88888
     * color : 1
     * customerEthnicityCode : HA
     * customerGenderCode : 1
     * gcjLatitude : 30.666667
     * gcjLongitude : 104.666667
     * idCardPhotoList : [{"order_number":0,"photo_name":"业务照片","photo_url":"https://baidu.com"}]
     * idNumber : 220401197101245690
     * identificationType : 2
     * issuingAuthority : 高新区公安局
     * name : 张三
     * nameCpaFisrt : zs
     * nameCpaFull : zhangsan
     * permanentAddress : 成都市高新区
     * phone : 13512345678
     * rentAddress : 成都市高新区
     * rentTime : 2017-05-23 12:00:00
     * returnAddress : 成都市高新区
     * returnTime : 2017-05-23 12:00:00
     * scenePhotoList : [{"order_number":0,"photo_name":"业务照片","photo_url":"https://baidu.com"}]
     * validityFromDate : 2017-05-23
     * validityThruDate : 2017-05-23
     * wgsLatitude : 30.666667
     * wgsLongitude : 104.666667
     */

    private String brand;
    private String brithDate;
    private String carNumber;
    private int color;
    private String customerEthnicityCode;
    private String customerGenderCode;
    private double gcjLatitude;
    private double gcjLongitude;
    private String idNumber;
    private int identificationType;
    private String issuingAuthority;
    private String name;
    private String nameCpaFisrt;
    private String nameCpaFull;
    private String permanentAddress;
    private String phone;
    private String rentAddress;
    private String rentTime;
    private String returnAddress;
    private String returnTime;
    private String validityFromDate;
    private String validityThruDate;
    private double wgsLatitude;
    private double wgsLongitude;
    private List<IdCardPhotoListBean> idCardPhotoList;
    private List<ScenePhotoListBean> scenePhotoList;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrithDate() {
        return brithDate;
    }

    public void setBrithDate(String brithDate) {
        this.brithDate = brithDate;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCustomerEthnicityCode() {
        return customerEthnicityCode;
    }

    public void setCustomerEthnicityCode(String customerEthnicityCode) {
        this.customerEthnicityCode = customerEthnicityCode;
    }

    public String getCustomerGenderCode() {
        return customerGenderCode;
    }

    public void setCustomerGenderCode(String customerGenderCode) {
        this.customerGenderCode = customerGenderCode;
    }

    public double getGcjLatitude() {
        return gcjLatitude;
    }

    public void setGcjLatitude(double gcjLatitude) {
        this.gcjLatitude = gcjLatitude;
    }

    public double getGcjLongitude() {
        return gcjLongitude;
    }

    public void setGcjLongitude(double gcjLongitude) {
        this.gcjLongitude = gcjLongitude;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(int identificationType) {
        this.identificationType = identificationType;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCpaFisrt() {
        return nameCpaFisrt;
    }

    public void setNameCpaFisrt(String nameCpaFisrt) {
        this.nameCpaFisrt = nameCpaFisrt;
    }

    public String getNameCpaFull() {
        return nameCpaFull;
    }

    public void setNameCpaFull(String nameCpaFull) {
        this.nameCpaFull = nameCpaFull;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRentAddress() {
        return rentAddress;
    }

    public void setRentAddress(String rentAddress) {
        this.rentAddress = rentAddress;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getValidityFromDate() {
        return validityFromDate;
    }

    public void setValidityFromDate(String validityFromDate) {
        this.validityFromDate = validityFromDate;
    }

    public String getValidityThruDate() {
        return validityThruDate;
    }

    public void setValidityThruDate(String validityThruDate) {
        this.validityThruDate = validityThruDate;
    }

    public double getWgsLatitude() {
        return wgsLatitude;
    }

    public void setWgsLatitude(double wgsLatitude) {
        this.wgsLatitude = wgsLatitude;
    }

    public double getWgsLongitude() {
        return wgsLongitude;
    }

    public void setWgsLongitude(double wgsLongitude) {
        this.wgsLongitude = wgsLongitude;
    }

    public List<IdCardPhotoListBean> getIdCardPhotoList() {
        return idCardPhotoList;
    }

    public void setIdCardPhotoList(List<IdCardPhotoListBean> idCardPhotoList) {
        this.idCardPhotoList = idCardPhotoList;
    }

    public List<ScenePhotoListBean> getScenePhotoList() {
        return scenePhotoList;
    }

    public void setScenePhotoList(List<ScenePhotoListBean> scenePhotoList) {
        this.scenePhotoList = scenePhotoList;
    }

    public static class IdCardPhotoListBean {
        /**
         * order_number : 0
         * photo_name : 业务照片
         * photo_url : https://baidu.com
         */

        private int order_number;
        private String photo_name;
        private String photo_url;

        public int getOrder_number() {
            return order_number;
        }

        public void setOrder_number(int order_number) {
            this.order_number = order_number;
        }

        public String getPhoto_name() {
            return photo_name;
        }

        public void setPhoto_name(String photo_name) {
            this.photo_name = photo_name;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }
    }

    public static class ScenePhotoListBean {
        /**
         * order_number : 0
         * photo_name : 业务照片
         * photo_url : https://baidu.com
         */

        private int order_number;
        private String photo_name;
        private String photo_url;

        public int getOrder_number() {
            return order_number;
        }

        public void setOrder_number(int order_number) {
            this.order_number = order_number;
        }

        public String getPhoto_name() {
            return photo_name;
        }

        public void setPhoto_name(String photo_name) {
            this.photo_name = photo_name;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }
    }

}
