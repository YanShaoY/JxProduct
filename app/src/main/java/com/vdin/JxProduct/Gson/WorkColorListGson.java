package com.vdin.JxProduct.Gson;

import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/19
 * @描述 Vdin成都研发部
 */
public class WorkColorListGson {

    /**
     * success : true
     * code : success
     * message : 成功
     * size : 1
     * collection : [{"vehicle_color":[{"code":"A","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000001","isDefault":0,"name":"白色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"B","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000002","isDefault":0,"name":"灰色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"C","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000003","isDefault":0,"name":"黄色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"D","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000004","isDefault":0,"name":"粉色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"E","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000005","isDefault":0,"name":"红色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"F","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000006","isDefault":0,"name":"紫色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"G","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000007","isDefault":0,"name":"绿色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"H","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000008","isDefault":0,"name":"蓝色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"I","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000009","isDefault":0,"name":"棕色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"J","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000010","isDefault":0,"name":"黑色","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"},{"code":"Z","createdAt":"2018-08-16 14:43:03","deptId":"100","dictType":"vehicle_color","id":"vehicle0-0004-0000-0002-color0000011","isDefault":0,"name":"其他","orderNumber":0,"remark":"\"注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！\"","state":0,"updatedAt":"2018-08-16 14:43:03"}]}]
     * errors : {}
     * meta : {"requestId":"f27d1388-b6a8-4ca1-b102-0b622416fabd","timestamp":1537333009571}
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
         * requestId : f27d1388-b6a8-4ca1-b102-0b622416fabd
         * timestamp : 1537333009571
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
        private List<VehicleColorBean> vehicle_color;

        public List<VehicleColorBean> getVehicle_color() {
            return vehicle_color;
        }

        public void setVehicle_color(List<VehicleColorBean> vehicle_color) {
            this.vehicle_color = vehicle_color;
        }

        public static class VehicleColorBean {
            /**
             * code : A
             * createdAt : 2018-08-16 14:43:03
             * deptId : 100
             * dictType : vehicle_color
             * id : vehicle0-0004-0000-0002-color0000001
             * isDefault : 0
             * name : 白色
             * orderNumber : 0
             * remark : "注意：字典编码影响 文字识别，请勿任意修改，如不明情况，请联系开发人员！"
             * state : 0
             * updatedAt : 2018-08-16 14:43:03
             */

            private String code;
            private String createdAt;
            private String deptId;
            private String dictType;
            private String id;
            private int isDefault;
            private String name;
            private int orderNumber;
            private String remark;
            private int state;
            private String updatedAt;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getDeptId() {
                return deptId;
            }

            public void setDeptId(String deptId) {
                this.deptId = deptId;
            }

            public String getDictType() {
                return dictType;
            }

            public void setDictType(String dictType) {
                this.dictType = dictType;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(int isDefault) {
                this.isDefault = isDefault;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(int orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }
        }
    }
}
