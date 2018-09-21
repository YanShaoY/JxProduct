package com.vdin.JxProduct.Gson;

import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/20
 * @描述 Vdin成都研发部
 */
public class WorkAddRegistGson {


    /**
     * address : 成都市
     * bizPhotoDTOList : [{"order_number":0,"photo_name":"业务照片","photo_url":"https://baidu.com"}]
     * customerInfo : {"birthDate":"2017-06-04","currentAddress":"现住详址","ethnicityCode":"HA","genderCode":"1","idNumber":"510105201405013191","idPhotoUrl":"www.baidu.com","identificationType":2,"issuingAuthority":"签发机关","name":"姓名","permanentAddress":"户籍地址","phone":"13345678123","validityFromDate":"2015-05-01","validityThruDate":"2025-05-01"}
     * latitude : 107
     * longitude : 107
     * plateNumber : 川A54110
     * position : {"gcjLat":30.000002,"gcjLon":120.000002,"wgsLat":30.000084,"wgsLon":120.000084}
     * serviceDescription : 装防弹玻璃
     * urgentBiz : false
     * vehicleColorCode : string
     * vehicleEngineNumber : 发动机号样例
     * vehicleIdentifyNumber : 车架号样例
     * vehicleModel : 红旗，防弹版
     */

    private String address;
    private CustomerInfoBean customerInfo;
    private int latitude;
    private int longitude;
    private String plateNumber;
    private PositionBean position;
    private String serviceDescription;
    private boolean urgentBiz;
    private String vehicleColorCode;
    private String vehicleEngineNumber;
    private String vehicleIdentifyNumber;
    private String vehicleModel;
    private List<BizPhotoDTOListBean> bizPhotoDTOList;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomerInfoBean getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfoBean customerInfo) {
        this.customerInfo = customerInfo;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public boolean isUrgentBiz() {
        return urgentBiz;
    }

    public void setUrgentBiz(boolean urgentBiz) {
        this.urgentBiz = urgentBiz;
    }

    public String getVehicleColorCode() {
        return vehicleColorCode;
    }

    public void setVehicleColorCode(String vehicleColorCode) {
        this.vehicleColorCode = vehicleColorCode;
    }

    public String getVehicleEngineNumber() {
        return vehicleEngineNumber;
    }

    public void setVehicleEngineNumber(String vehicleEngineNumber) {
        this.vehicleEngineNumber = vehicleEngineNumber;
    }

    public String getVehicleIdentifyNumber() {
        return vehicleIdentifyNumber;
    }

    public void setVehicleIdentifyNumber(String vehicleIdentifyNumber) {
        this.vehicleIdentifyNumber = vehicleIdentifyNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public List<BizPhotoDTOListBean> getBizPhotoDTOList() {
        return bizPhotoDTOList;
    }

    public void setBizPhotoDTOList(List<BizPhotoDTOListBean> bizPhotoDTOList) {
        this.bizPhotoDTOList = bizPhotoDTOList;
    }

    public static class CustomerInfoBean {
        /**
         * birthDate : 2017-06-04
         * currentAddress : 现住详址
         * ethnicityCode : HA
         * genderCode : 1
         * idNumber : 510105201405013191
         * idPhotoUrl : www.baidu.com
         * identificationType : 2
         * issuingAuthority : 签发机关
         * name : 姓名
         * permanentAddress : 户籍地址
         * phone : 13345678123
         * validityFromDate : 2015-05-01
         * validityThruDate : 2025-05-01
         */

        private String birthDate;
        private String currentAddress;
        private String ethnicityCode;
        private String genderCode;
        private String idNumber;
        private String idPhotoUrl;
        private int identificationType;
        private String issuingAuthority;
        private String name;
        private String permanentAddress;
        private String phone;
        private String validityFromDate;
        private String validityThruDate;

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getCurrentAddress() {
            return currentAddress;
        }

        public void setCurrentAddress(String currentAddress) {
            this.currentAddress = currentAddress;
        }

        public String getEthnicityCode() {
            return ethnicityCode;
        }

        public void setEthnicityCode(String ethnicityCode) {
            this.ethnicityCode = ethnicityCode;
        }

        public String getGenderCode() {
            return genderCode;
        }

        public void setGenderCode(String genderCode) {
            this.genderCode = genderCode;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getIdPhotoUrl() {
            return idPhotoUrl;
        }

        public void setIdPhotoUrl(String idPhotoUrl) {
            this.idPhotoUrl = idPhotoUrl;
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
    }

    public static class PositionBean {
        /**
         * gcjLat : 30.000002
         * gcjLon : 120.000002
         * wgsLat : 30.000084
         * wgsLon : 120.000084
         */

        private double gcjLat;
        private double gcjLon;
        private double wgsLat;
        private double wgsLon;

        public double getGcjLat() {
            return gcjLat;
        }

        public void setGcjLat(double gcjLat) {
            this.gcjLat = gcjLat;
        }

        public double getGcjLon() {
            return gcjLon;
        }

        public void setGcjLon(double gcjLon) {
            this.gcjLon = gcjLon;
        }

        public double getWgsLat() {
            return wgsLat;
        }

        public void setWgsLat(double wgsLat) {
            this.wgsLat = wgsLat;
        }

        public double getWgsLon() {
            return wgsLon;
        }

        public void setWgsLon(double wgsLon) {
            this.wgsLon = wgsLon;
        }
    }

    public static class BizPhotoDTOListBean {
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
