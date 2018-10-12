package com.vdin.JxProduct.Util;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.vdin.JxProduct.App.MainApplication;

import static com.baidu.location.LocationClientOption.LocationMode;

/**
 * @开发者 YanSY
 * @日期 2018/10/9
 * @描述 Vdin成都研发部
 */
public class BaiduLocationUtil {

    /******************************************************** 本类参数及变量定义 ***********************************************************/

    // 定位客户端
    private LocationClient myLocationClient = null;
    // 定位监听回调
    private MyLocationListener myLocationListener;

    /******************************************************** 定位回调接口 ***********************************************************/

    /**
     * 定位回调接口
     */
    public interface MyLocationListener{

        /**
         * 定位回调
         * @param isSuccess 定位是否成功
         * @param location 定位信息
         */
        void locationResult(boolean isSuccess,BDLocation location);

    }

    /******************************************************** 本类实例构造方法 ***********************************************************/

    /**
     * YanSY标准单例构造方法
     * @return 本类实例
     */
    public static BaiduLocationUtil getInstance() {
        return LocationHolder.GETINSTANCE;
    }
    // 内部静态类
    private static class LocationHolder {
        private static final BaiduLocationUtil GETINSTANCE = new BaiduLocationUtil();
    }
    // 私有化构造方法
    private BaiduLocationUtil(){
    }

    /******************************************************** 本类方法调用 ***********************************************************/

    /**
     * 获取当前位置 成功后就停止
     */
    public void requestLocation(MyLocationListener locationListener) {

        // 赋值定位客户端
        myLocationClient = new LocationClient(MainApplication.getContext());
        // 赋值定位监听
        myLocationListener = locationListener;
        // 设置定位参数
        myLocationClient.setLocOption(getOption());

        // 注册位置监听器
        myLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {

                // 成功
                if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError && bdLocation.getAddrStr() != null) {
                    // 停止
//                    myLocationClient.stop();
                    // 返回成功信息
                    myLocationListener.locationResult(true,bdLocation);

                }else { // 失败

                    // 自动重新开始 并返回错误信息
//                    myLocationClient.start();
                    myLocationListener.locationResult(false,bdLocation);

                }
            }

        });

        // 开始启动
        myLocationClient.start();

        /*
         * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。
         * 调用requestLocation()后，每隔设定的时间，定位SDK就会进行一次定位。
         * 如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
         * 定时定位时，调用一次requestLocation，会定时监听到定位结果。
         */

//        myLocationClient.requestLocation();
    }

    /**
     * 手动停止定位
     */
    public void stopLocation(){
        if (myLocationClient != null){
            myLocationClient.stop();
            myLocationListener = null;
        }
    }

    /**
     * 获取定位SDK参数
     * @return
     */
    public LocationClientOption getOption(){

        // 设置定位条件
        LocationClientOption option = new LocationClientOption();

        // 可选，设置定位模式，默认高精度
        // LocationMode.Hight_Accuracy：高精度；
        // LocationMode. Battery_Saving：低功耗；
        // LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationMode.Hight_Accuracy);

        // 可选，设置返回经纬度坐标类型，默认GCJ02
        // GCJ02：国测局坐标；
        // BD09ll：百度经纬度坐标；
        // BD09：百度墨卡托坐标；
        // 海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        option.setCoorType("GCJ02");

        // 可选，设置发起定位请求的间隔，int类型，单位ms
        // 如果设置为0，则代表单次定位，即仅定位一次，默认为0
        // 如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(2000);

        // 可选，设置是否使用gps，默认false
        // 使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        // 可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(false);

        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);

        // 可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        // 可选，V7.2版本新增能力
        // 如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        option.setWifiCacheTimeOut(5*60*1000);

        // 可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        // 可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        return option;
    }



}










