package com.vdin.JxProduct.Service;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.readTwoGeneralCard.ActiveCallBack;
import com.readTwoGeneralCard.EidUserInfo;
import com.readTwoGeneralCard.OTGReadCardAPI;
import com.readTwoGeneralCard.PassportInfo;
import com.readTwoGeneralCard.Serverinfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @开发者 YanSY
 * @日期 2018/9/28
 * @描述 Vdin成都研发部
 */
public class IDCardReadService implements ActiveCallBack,Application.ActivityLifecycleCallbacks{

    /* 公共参数定义 */

    // log 打印日志TAG
    final static String TAG = "IDCardReadService";
    // 是否是测试还是正式版
    final static boolean isTestService = false;

    // 调用服务的上下文
    public Activity myActivity;
    // 服务回调接口
    public IdCardServiceBlock myBlock;
    // 读卡模式 1:NFC 2：OTG (默认NFC)
    public int readMode;
    // 本类线程处理器
    MyHandler myHandler;
    // 身份信息模型
    public Identityinfo identityinfo;

    // 私有化 读卡接口
    private OTGReadCardAPI readCardAPI;
    // 私有化 APPkey (读卡操作的Key，正式版需要跟后台申请)
    private String appKeyFactory;

    // 私有化 OTG接收器
    private BroadcastReceiver myOTGReceiver;
    
    // 私有化 NFC 适配器
    private NfcAdapter nfcAdapter;
    // NFC 状态 1:正常 2:无NFC 3:未打开NFC
    public int nfcType;
    // 私有化 等待意图
    private PendingIntent pendingIntent;
    // 私有化 意图过滤器
    private IntentFilter intentFilter;
    // 私有化 NFC技术数据列表
    private String[][] nfcTechLists;
    // 私有化 读卡意图
    private Intent onPacNewIntent;

    /**
     * 构造器
     *
     * @param activity 句柄上下文
     * @param block 服务回调
     */
    public IDCardReadService(Activity activity, IdCardServiceBlock block) {
        this.myActivity = activity;
        myActivity.getApplication().registerActivityLifecycleCallbacks(this);
        this.myBlock = block;
        // 初始化默认参数
        initParameter();
        // 初始化读卡服务接口
        initReadCardAPI();
        // 设置OTG 监听
        registerUDiskReceiver();
        // 初始化 NFC扫描
        initNFC();

    }

    /**
     * 开始身份证读取
     * @return 
     */
    public boolean startToReadIdCard(){

        if (readMode == 1){

            if (nfcAdapter == null){
                return false;
            }else {
                nfcAdapter.enableForegroundDispatch(myActivity,pendingIntent,new IntentFilter[]{intentFilter},nfcTechLists);
                return true;
            }


        }else if (readMode == 2){
            // TODO OTG阅读
            Log.d(TAG, "startToReadIdCard: ");
        }else {

            return false;
        }

        return false;
    }

    /**
     * 停止身份证读取
     * @return
     */
    public boolean stopToReadIdCard() {

        if (readMode == 1){

            if (nfcAdapter == null){
                return false;
            }else {
                nfcAdapter.disableForegroundDispatch(myActivity);
                return true;
            }

        }else if (readMode == 2){
            // TODO OTG阅读
            Log.d(TAG, "startToReadIdCard: ");

        }else {
            return false;
        }
        return false;
    }

    /**
     * 初始化默认参数
     */
    private void initParameter() {
        // 读卡模式 NFC
        readMode = 1;
        // 内部线程处理器
        myHandler = new MyHandler(this);
        // 身份信息模型
        identityinfo = new Identityinfo();
        identityinfo.reuse = "false";
    }

    /**
     * 初始化读卡服务接口
     */
    private void initReadCardAPI() {

        readCardAPI = new OTGReadCardAPI(myActivity, this, true);
        // 服务器信息
        Serverinfo serverinfo;
        // 判断测试或者正式版本
        if (isTestService) {
            // 虎鞭的测试 服务器信息
            serverinfo = new Serverinfo("id.vdin01.com", 12345);
            // 虎鞭的测试 appkey
            appKeyFactory = "厂商五";

        } else {
            // 胡蓉的正式 服务器信息
            serverinfo = new Serverinfo("id.vdin01.com", 80);
            // 胡蓉的正式 appkey
            appKeyFactory = "62e0b6c6c2777368f563a743d8c092e3";
        }

        // 身份证解码服务器列表
        ArrayList<Serverinfo> twoCardServerlist = new ArrayList<>();
        twoCardServerlist.add(serverinfo);
        // eidServerList:eid验证服务器列表，可以为null

        // 设置服务器列表（初始化后最先调用，必须） false正式 true测试
        readCardAPI.setServerInfo(twoCardServerlist, null, false);

    }

    public void initNFC(){

        // 设置NFC适配器
        nfcAdapter = NfcAdapter.getDefaultAdapter(myActivity);

        if (nfcAdapter == null){

            // 判断是否API是否已打开 可能存在OTG正在读取情况
            if (!readCardAPI.isPiccOpenFlag()){
                // 未打开 且模式为NFC 返回失败信息
                if (readMode == 1){
                    myBlock.onFailure(this,readMode,102);
                }
                // 切换NFC状态为无
                nfcType = 2;
            }

            return;
        }


        // 初始化等待意图
        Intent newItent = new Intent(myActivity,myActivity.getClass());
        newItent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(myActivity,0,newItent,0);

        // 初始化意图过滤器
        intentFilter = new IntentFilter(nfcAdapter.ACTION_TECH_DISCOVERED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        // NFC技术数据列表
        nfcTechLists = new String[][]{
                new String[]{
                        NfcB.class.getName()
                }
        };

        if (nfcAdapter.isEnabled()){
            nfcType = 1;
        }else {
            nfcType = 3;
            myBlock.onFailure(this,readMode,103);
        }

    }


    /* OTG相关实现 */

    // USB许可事件
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    /**
     * OTG 广播监听注册
     */
    private void registerUDiskReceiver() {

        // 初始化OTG接收器
        myOTGReceiver = initMyOTGReceiver();

        //监听OTG插入 拔出
        IntentFilter usbDeviceStateFilter = new IntentFilter();
        usbDeviceStateFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        usbDeviceStateFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);

        // 注册监听USB许可事件
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);

        // 注册监听器
        myActivity.registerReceiver(myOTGReceiver, usbDeviceStateFilter);
        myActivity.registerReceiver(myOTGReceiver, filter);

    }

    /**
     * 初始化 OTG接收器
     */
    private BroadcastReceiver initMyOTGReceiver() {

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 获取传入intent 的事件
                String action = intent.getAction();
                // 判断事件
                switch (action) {

                    // 01 接收权限请求广播
                    case ACTION_USB_PERMISSION:

                        // 获取usb设备
                        UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        // 获取USB连接权限
                        boolean booleanExtra = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                        if (booleanExtra) {
                            if (usbDevice != null) {
                                // 切换为 OTG模式
                                readMode = 2;
                                // 向处理器发送消息
                                myHandler.sendEmptyMessageDelayed(readMode, 0);

                            } else {
                                Log.d(TAG, "onReceive: " + "未插入U盘");
                            }

                        } else {
                            Log.d(TAG, "onReceive: " + "未获取到U盘使用权限");
                        }

                        break;

                    // 02 接收到USB设备插入广播
                    case UsbManager.ACTION_USB_DEVICE_ATTACHED:

                        // 获取usb设备
                        UsbDevice device_add = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (device_add != null) {
                            // TODO 尝试读取U盘数据
                        }

                        break;

                    //接收到U盘设设备拔出广播
                    case UsbManager.ACTION_USB_DEVICE_DETACHED:
                        Log.d(TAG, "onReceive: " + "U盘已拔出");
                        break;
                }

            }
        };

        return receiver;
    }


    /* ActiveCallBack 相关接口函数 */

    /**
     * 获取EID卡片PIN码，因为在获取EID签名的时候只有PIN码授权后才可以读卡片中签名信息
     *
     * @param i PIN码剩余验证次数
     * @return 返回EID卡片的PIN码字符串
     */
    @Override
    public String GetEidPin(int i) {
        return null;
    }

    /**
     * 获取卡片所属人的姓名和身份证号，
     * 因为EID验证身份是把卡片所属人的相关信息与EID卡片签名信息发送给公安三所进行验证，所以无法从卡片中获取身份信息，需要第三方手动提供
     *
     * @return 返回用户身份证号码和姓名
     * szName == 姓名 ;
     * szCardNO == 证件号码;
     * eCardType == 证件类型;
     */
    @Override
    public EidUserInfo GetEidUserInfo() {
        return null;
    }

    /**
     * 获取护照号、出生日期及有效期。读取电子护照必须要这三个要素才可以进行，所以必须由第三方提供
     *
     * @return szPassportNO：护照号
     * szBirthday：出生日期(YYMMdd:如890123表示89年1月23)
     * szEndDate：有效日期(YYMMdd:如890123表示89年1月23)
     */
    @Override
    public PassportInfo GetPassportUserInfo() {
        return null;
    }

    /**
     * 获取读卡进度
     *
     * @param i >20 就可以算作成功了
     */
    @Override
    public void readProgress(int i) {
        Log.d(TAG, "readProgress: " + "读取进度" + i);
    }

    /**
     * 用户贴卡操作回调
     *
     * @param intent
     */
    @Override
    public void onPacNewIntent(Intent intent) {

        // 给NFC读取意图赋值
        this.onPacNewIntent = intent;
        // 读卡模式切换
        this.readMode = 1;
        // 向处理器发送消息
        myHandler.sendEmptyMessageDelayed(readMode, 0);
    }

    /* myActivity 生命周期回调 */

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted: ");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed: ");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused: ");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "onActivityStopped: ");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG, "onActivitySaveInstanceState: ");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed: ");
    }

    /* 内部类定义 */

    /**
     * 内部线程处理器
     */
    private static class MyHandler extends Handler {

        //
        private final WeakReference<IDCardReadService> myService;

        /**
         * 构建函数
         *
         * @param service 服务实例化
         */
        public MyHandler(IDCardReadService service) {
            myService = new WeakReference<>(service);
        }

        /**
         * 发送消息
         *
         * @param msg 消息
         */
        @Override
        public void handleMessage(Message msg) {
            // 打印消息
            Log.d(TAG, "handleMessage: " + msg);
            if (myService.get() != null) {
                // TODO 执行传入消息事件
                Error error = new Error();

            }
        }
    }

    /**
     * 身份信息模型
     */
    public static class Identityinfo implements Parcelable {
        public String reuse;//是否是查询数据
        public String field_identification_code;//是否是扫描数据
        public String customer_identification_number;//身份证号
        public String customer_mobile_phone_number;//登记手机号
        public String customer_name;//姓名
        public String customer_identity_card_validity_from_date;//身份证有效起始日期
        public String customer_identity_card_validity_thru_date;//身份证有效结束日期
        public String customer_identity_card_issuing_authority_name;//身份证签发机关
        public String customer_identity_card_gender_code;//身份证性别代号
        public String customer_identity_card_ethnicity_code;//身份证民族代号
        public String customer_identity_card_address;//身份证地址
        public String customer_identity_card_birth_date;//身份证的生日信息
        public String customer_identity_card_portraits;//身份证头像
        public String customer_identity_card_photos;//现场照
        public String idtype; //证件类型 1:读卡器识别,2:手工输入身份证号,3:手工输入手机号识别 ,
        public String insert_type;
        public String check_in_card_num = "";//入住门卡号
        public String isRzSuss = "";//是否人证对比通过了
        //人员备案添加
        public String customer_identity_card_gender;//身份证性别
        public String customer_identity_card_ethnicity;//身份证民族
        //OTG外接nfc添加
        public byte[] headImgData;


        protected Identityinfo(Parcel in) {
            reuse = in.readString();
            field_identification_code = in.readString();
            customer_identification_number = in.readString();
            customer_mobile_phone_number = in.readString();
            customer_name = in.readString();
            customer_identity_card_validity_from_date = in.readString();
            customer_identity_card_validity_thru_date = in.readString();
            customer_identity_card_issuing_authority_name = in.readString();
            customer_identity_card_gender_code = in.readString();
            customer_identity_card_ethnicity_code = in.readString();
            customer_identity_card_address = in.readString();
            customer_identity_card_birth_date = in.readString();
            customer_identity_card_portraits = in.readString();
            customer_identity_card_photos = in.readString();
            idtype = in.readString();
            insert_type = in.readString();
            check_in_card_num = in.readString();
            isRzSuss = in.readString();
            customer_identity_card_gender = in.readString();
            customer_identity_card_ethnicity = in.readString();
            headImgData = in.createByteArray();
        }

        public Identityinfo() {

        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(reuse);
            dest.writeString(field_identification_code);
            dest.writeString(customer_identification_number);
            dest.writeString(customer_mobile_phone_number);
            dest.writeString(customer_name);
            dest.writeString(customer_identity_card_validity_from_date);
            dest.writeString(customer_identity_card_validity_thru_date);
            dest.writeString(customer_identity_card_issuing_authority_name);
            dest.writeString(customer_identity_card_gender_code);
            dest.writeString(customer_identity_card_ethnicity_code);
            dest.writeString(customer_identity_card_address);
            dest.writeString(customer_identity_card_birth_date);
            dest.writeString(customer_identity_card_portraits);
            dest.writeString(customer_identity_card_photos);
            dest.writeString(idtype);
            dest.writeString(insert_type);
            dest.writeString(check_in_card_num);
            dest.writeString(isRzSuss);
            dest.writeString(customer_identity_card_gender);
            dest.writeString(customer_identity_card_ethnicity);
            dest.writeByteArray(headImgData);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Identityinfo> CREATOR = new Creator<Identityinfo>() {
            @Override
            public Identityinfo createFromParcel(Parcel in) {
                return new Identityinfo(in);
            }

            @Override
            public Identityinfo[] newArray(int size) {
                return new Identityinfo[size];
            }
        };
    }

    public interface IdCardServiceBlock {

        /**
         * errorCode
         * 102 不支持NFC功能
         * 103 NFC未开启
         */

        /**
         * 发生错误时回调
         * @param service 服务
         * @param readMode 发生错误的模式
         * @param errorCode 错误信息
         */
        void onFailure(IDCardReadService service,int readMode, int errorCode);

        /**
         * 自定义请求回调接口
         */
        void completeBlock(boolean isSuccess, Object object);
    }

}















