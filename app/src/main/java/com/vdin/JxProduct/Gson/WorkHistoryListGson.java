package com.vdin.JxProduct.Gson;

import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/25
 * @描述 Vdin成都研发部
 */
public class WorkHistoryListGson {

    private boolean success;
    private String message;
    private List<CollectionBean> collection;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CollectionBean> getCollection() {
        return collection;
    }

    public void setCollection(List<CollectionBean> collection) {
        this.collection = collection;
    }

    public static class CollectionBean {

        private MotorVehicleMaintenanceBean motorVehicleMaintenance;
        private List<MaintenancePhotosBean> maintenancePhotos;

        public MotorVehicleMaintenanceBean getMotorVehicleMaintenance() {
            return motorVehicleMaintenance;
        }

        public void setMotorVehicleMaintenance(MotorVehicleMaintenanceBean motorVehicleMaintenance) {
            this.motorVehicleMaintenance = motorVehicleMaintenance;
        }

        public List<MaintenancePhotosBean> getMaintenancePhotos() {
            return maintenancePhotos;
        }

        public void setMaintenancePhotos(List<MaintenancePhotosBean> maintenancePhotos) {
            this.maintenancePhotos = maintenancePhotos;
        }

        public static class MotorVehicleMaintenanceBean {

            private String createdAt;
            private String id;
            private String name;
            private String plateNumber;
            private PractitionerBean practitioner;
            private String serviceDescription;
            private VehicleColorBean vehicleColor;
            private String vehicleModel;

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPlateNumber() {
                return plateNumber;
            }

            public void setPlateNumber(String plateNumber) {
                this.plateNumber = plateNumber;
            }

            public PractitionerBean getPractitioner() {
                return practitioner;
            }

            public void setPractitioner(PractitionerBean practitioner) {
                this.practitioner = practitioner;
            }

            public String getServiceDescription() {
                return serviceDescription;
            }

            public void setServiceDescription(String serviceDescription) {
                this.serviceDescription = serviceDescription;
            }

            public VehicleColorBean getVehicleColor() {
                return vehicleColor;
            }

            public void setVehicleColor(VehicleColorBean vehicleColor) {
                this.vehicleColor = vehicleColor;
            }

            public String getVehicleModel() {
                return vehicleModel;
            }

            public void setVehicleModel(String vehicleModel) {
                this.vehicleModel = vehicleModel;
            }

            public static class PractitionerBean {
                /**
                 * name : 游拿
                 */

                private String name;

                private GenderBean gender;


                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public GenderBean getGender() {
                    return gender;
                }

                public void setGender(GenderBean gender) {
                    this.gender = gender;
                }

                public static class GenderBean{
                    /**
                     * name : 男
                     */
                    private String name;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }

            public static class VehicleColorBean {
                /**
                 * name : 紫色
                 */

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

            }

        }

        public static class MaintenancePhotosBean {
            /**
             * photoUrl : https://picasso-dev.oss-cn-qingdao.aliyuncs.com/JXApp/20180918152857-c97f8071-5868-4369-9fa9-062543250591.jpg
             */

            private String photoUrl;

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }
        }
    }
}
