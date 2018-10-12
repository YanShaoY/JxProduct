package com.vdin.JxProduct.Service;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcB;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.readTwoGeneralCard.ActiveCallBack;
import com.readTwoGeneralCard.EidUserInfo;
import com.readTwoGeneralCard.OTGReadCardAPI;
import com.readTwoGeneralCard.PassportInfo;
import com.readTwoGeneralCard.Serverinfo;
import com.vdin.JxProduct.Activity.BaseActivity;
import com.vdin.JxProduct.OSSService.BitmapUtil;
import com.vdin.JxProduct.OSSService.DateUtils;
import com.vdin.JxProduct.OSSService.PermissionUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @开发者 YanSY
 * @日期 2018/9/28
 * @描述 Vdin成都研发部
 */
public class IDCardReadService implements ActiveCallBack {

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

    /*********************************************构造器和ACtivity需调用的方法**************************************************/

    /**
     * 构造器
     *
     * @param activity 句柄上下文
     * @param block    服务回调
     */
    public IDCardReadService(Activity activity, IdCardServiceBlock block) {

        this.myActivity = activity;
        this.myBlock = block;
        // 读卡模式 NFC
        readMode = 1;
        // 内部线程处理器
        myHandler = new MyHandler(this);

        // 初始化读卡服务接口
        initReadCardAPI();
        // 设置OTG 监听
//        registerUDiskReceiver();
        // 初始化 NFC扫描
        initNFC();

    }

    /**
     * 开始身份证读取
     *
     * @return
     */
    public boolean startToReadIdCard() {

        if (readMode == 1) {

            if (nfcAdapter == null) {
                return false;
            } else {
                nfcAdapter.enableForegroundDispatch(myActivity, pendingIntent, new IntentFilter[]{intentFilter}, nfcTechLists);
                return true;
            }


        } else if (readMode == 2) {
            // TODO OTG阅读
            Log.d(TAG, "startToReadIdCard: ");
        } else {

            return false;
        }

        return false;
    }

    /**
     * 停止身份证读取
     *
     * @return
     */
    public boolean stopToReadIdCard() {

        if (readMode == 1) {

            if (nfcAdapter == null) {
                return false;
            } else {
                nfcAdapter.disableForegroundDispatch(myActivity);
                return true;
            }

        } else if (readMode == 2) {
            // TODO OTG阅读
            Log.d(TAG, "startToReadIdCard: ");

        } else {
            return false;
        }
        return false;
    }


    /* myActivity 生命周期回调 */

    /**
     * 响应新的intent时调用
     *
     * @param intent
     */
    public void onNewIntent(Intent intent) {
        onPacNewIntent = intent;
        readMode = 1;
        myHandler.sendEmptyMessageDelayed(readMode, 0);
    }

    /**
     * 界面注销时调用
     *
     * @param activity
     */
    public void onActivityDestroyed(Activity activity) {
        if (activity.equals(myActivity)) {
            this.myActivity = null;
            myHandler.removeCallbacksAndMessages(null);

        }
    }

    /********************************************* 私有初始化方法 ACtivity不用管 **************************************************/
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
            // 虎鞭的正式 服务器信息
            serverinfo = new Serverinfo("id.vdin01.com", 8323);
            // 虎鞭的正式 appkey
            appKeyFactory = "4ba7441a8f7181c8c2cfd9e3de12995b";
        }

        // 身份证解码服务器列表
        ArrayList<Serverinfo> twoCardServerlist = new ArrayList<>();
        twoCardServerlist.add(serverinfo);
        // eidServerList:eid验证服务器列表，可以为null

        // 设置服务器列表（初始化后最先调用，必须） false正式 true测试
        readCardAPI.setServerInfo(twoCardServerlist, null, false);

    }

    /**
     * 初始化NFC读取
     */
    private void initNFC() {

        // 设置NFC适配器
        nfcAdapter = NfcAdapter.getDefaultAdapter(myActivity);

        if (nfcAdapter == null) {

            // 判断是否API是否已打开 可能存在OTG正在读取情况
            if (!readCardAPI.isPiccOpenFlag()) {
                // 未打开 且模式为NFC 返回失败信息
                if (readMode == 1) {
                    myBlock.onFailure(this, readMode, 102);
                }
                // 切换NFC状态为无
                nfcType = 2;
            }

            return;
        }


        // 初始化等待意图
        Intent newItent = new Intent(myActivity, myActivity.getClass());
        newItent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(myActivity, 0, newItent, 0);

        // 初始化意图过滤器
        intentFilter = new IntentFilter(nfcAdapter.ACTION_TECH_DISCOVERED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        // NFC技术数据列表
        nfcTechLists = new String[][]{
                new String[]{
                        NfcB.class.getName()
                }
        };

        if (nfcAdapter.isEnabled()) {
            nfcType = 1;
        } else {
            nfcType = 3;
            myBlock.onFailure(this, readMode, 103);
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

    /*********************************************    MyHandler 事件回调响应  **************************************************/

    /**
     * MyHandler-handleMessage 响应事件
     *
     * @param msg 收到的消息
     */
    public void handleMessageAction(Message msg) {

        // 返回状态码
        int code = 0;

        // 判断读卡模式进行读卡
        switch (msg.what) {
            // NFC
            case 1:
                code = readCardAPI.NfcReadCard(appKeyFactory, onPacNewIntent);
                break;
            // OTG
            case 2:
                // TODO OTG 读卡
//                code = readCardAPI.OTGReadCard(appKeyFactory,)
                break;

            // Other
            default:
                break;
        }

        // 判断状态码 进行相应操作
        switch (code) {
            case 0:
                myBlock.onFailure(this, readMode, 10100);
                break;

            case 2:
                myBlock.onFailure(this, readMode, 10102);
                break;

            case 41:
                myBlock.onFailure(this, readMode, 10141);
                break;

            case 42:
                myBlock.onFailure(this, readMode, 10142);
                break;

            case 43:
                myBlock.onFailure(this, readMode, 10143);
                break;

            case 90:
                readCardInfo();
                break;

        }

    }

    /**
     * 读取卡片信息
     */
    private void readCardInfo() {

        Identityinfo identityinfo = new Identityinfo();

        //是否是查询数据
        identityinfo.reuse = "false";

        //身份证号
        identityinfo.customer_identification_number = readCardAPI.CardNo().trim();

        //姓名
        identityinfo.customer_name = readCardAPI.Name().trim();

        //身份证有效起始日期
        String activity = readCardAPI.Activity();
        identityinfo.customer_identity_card_validity_from_date = "";

        //身份证有效结束日期
        identityinfo.customer_identity_card_validity_thru_date = "";

        if (!TextUtils.isEmpty(activity)) {
            String[] activitys = activity.split("-");
            if (activitys != null && activitys.length >= 1) {
                if (!TextUtils.isEmpty(activitys[0])) {
                    identityinfo.customer_identity_card_validity_from_date = DateUtils.getYMDDateStrByYmd(activitys[0]);
                }
                if (!TextUtils.isEmpty(activitys[1]) && !activitys[1].equals("长期")) {
                    identityinfo.customer_identity_card_validity_thru_date = DateUtils.getYMDDateStrByYmd(activitys[1]);
                }
            }
        }

        //身份证签发机关
        identityinfo.customer_identity_card_issuing_authority_name = readCardAPI.Police();

        //身份证性别代号
        identityinfo.customer_identity_card_gender_code = readCardAPI.SexL().equals("男") ? "1" : "2";

        //身份证民族代号
        identityinfo.customer_identity_card_ethnicity_code = NationUtil.nationTransfor(readCardAPI.NationL().trim() + "族");

        //身份证地址
        identityinfo.customer_identity_card_address = readCardAPI.Address().trim();

        //身份证的生日信息
        identityinfo.customer_identity_card_birth_date = readCardAPI.Born().substring(0, 4) + "-" + readCardAPI.Born().substring(4, 6) + "-" + readCardAPI.Born().substring(6, 8);

        //身份证头像
        String headAddress = "";
        if (readCardAPI.GetImage() != null) {
            headAddress = bytesToImageFile(readCardAPI.GetImage());
        }
        identityinfo.customer_identity_card_portraits = headAddress;

        //证件类型 1:读卡器识别,2:手工输入身份证号,3:手工输入手机号识别 ,
        identityinfo.idtype = "1";

        //入住门卡号
        identityinfo.check_in_card_num = NFCUtil.ChangeSNID(readCardAPI.GetSNID().trim());

        /* 人员备案添加 */
        //身份证性别
        identityinfo.customer_identity_card_gender = readCardAPI.SexL().trim();

        //身份证民族
        identityinfo.customer_identity_card_ethnicity = readCardAPI.NationL().trim();

        //OTG外接nfc添加
        identityinfo.headImgData = readCardAPI.GetImage();

        readCardAPI.release();

        myBlock.completeBlock(this, readMode, identityinfo);

    }

    /**
     * 图片转换
     *
     * @param bytes 图片数据
     * @return 图片本地路径
     */
    private String bytesToImageFile(byte[] bytes) {
        // 请求权限 存取权限
        if (!PermissionUtil.checkExternalStorage(myActivity)){
            return "";
        }

        try {
//            File file = new File(FileUtils.PIC_TEMP_DIR + "zp.jpg");
            Bitmap bitmap = BitmapUtil.bytes2Bimap(bytes);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = myActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES); // 放在这个目录较为安全，只要手机不被root，该目录就无法被访问
            File f = null;
            try {
                f = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filePath = f.getPath();
            BitmapUtil.saveBitmap(bitmap, filePath);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /********************************************* ActiveCallBack 相关接口函数 **************************************************/

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

    /********************************************* 内部类定义 **************************************************/

    /**
     * 内部线程处理器
     */
    private static class MyHandler extends Handler {

        // 存储传入的服务对象
        private final WeakReference<IDCardReadService> myService;

        public MyHandler(IDCardReadService service) {
            myService = new WeakReference<>(service);
        }

        /**
         * 收到消息
         *
         * @param msg 消息
         */
        @Override
        public void handleMessage(Message msg) {

            // 获取执行服务
            if (myService.get() != null) {
                myService.get().handleMessageAction(msg);
            }

//            // 创建线程池 最多支持1个子线程
//            ExecutorService exec = Executors.newFixedThreadPool(1);
//            // 线程计数
//            final CountDownLatch mCountDownLatch = new CountDownLatch(1);
//
//            Runnable upTask = new Runnable() {
//                @Override
//                public void run() {
//
//                    // 获取执行服务
//                    if (myService.get() != null) {
//                        myService.get().handleMessageAction(msg);
//                    }
//                    mCountDownLatch.countDown();
//                }
//            };
//            exec.submit(upTask);
//
//            try {
//                mCountDownLatch.await();
//                // 关闭线程池
//                exec.shutdown();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

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

    /**
     * 民族代码与名字切换
     */
    public static class NationUtil {
        static HashMap<String, String> min = new HashMap<>();
        static String[] cn = {"汉族", "蒙古族", "回族", "藏族", "维吾尔族", "苗族", "彝族", "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族", "哈萨克族", "傣族", "黎族", "傈僳族", "佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族", "羌族", "布朗族", "撒拉族", "毛南族", "仡佬族", "锡伯族", "阿昌族", "普米族", "塔吉克族", "怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族", "塔塔尔族", "独龙族", "鄂伦春族", "赫哲族", "门巴族", "珞巴族", "基诺族", "其他"};
        static String[] en = {"HA", "MG", "HU", "ZA", "UG", "MH", "YI", "ZH", "BY", "CS", "MA", "DO", "YA", "BA", "TJ", "HN", "KZ", "DA", "LI", "LS", "VA", "SH", "GS", "LH", "SU", "DX", "NX", "JP", "KG", "TU", "DU", "ML", "QI", "BL", "SL", "MN", "GL", "XB", "AC", "PM", "TA", "NU", "UZ", "RS", "EW", "DE", "BN", "YG", "GI", "TT", "DR", "OR", "HZ", "MB", "LB", "JN", "ZZ"};

        public static String nationTransfor(String nation_name) {
            min.clear();
            for (int i = 0; i < cn.length; i++) {
                min.put(cn[i], en[i]);
            }
            return min.get(nation_name);
        }
    }

    /**
     * 入住门卡号切换
     */
    public static class NFCUtil {

        public static String ChangeSNID(String snid) {
            String nid = "";
            int st = 0;
            int se = 2;
            ArrayList<String> ss = new ArrayList<>();
            for (int i = 0; i < (snid.length() / 2); i++) {
                String s1 = snid.substring(i + st, i + se);
                st++;
                se++;
                ss.add(s1);
            }
            for (int i = ss.size() - 1; i >= 0; i--) {
                nid = nid + ss.get(i);
            }
            return nid;
        }
    }

    /**
     * 本类服务读卡回调接口
     */
    public interface IdCardServiceBlock {

        /**
         * errorCode
         * 102 不支持NFC功能
         * 103 NFC未开启
         * 10100 读卡错误
         * 10102 接受数据超时
         * 10141 读卡失败
         * 10142 网络连接错误
         * 10143 服务器繁忙
         */

        /**
         * 发生错误时回调
         *
         * @param service   服务
         * @param readMode  发生错误的模式
         * @param errorCode 错误信息
         */
        void onFailure(IDCardReadService service, int readMode, int errorCode);

        /**
         * 身份证读卡回调
         *
         * @param service  服务
         * @param readMode 发生错误的模式
         * @param info     错误信息
         */
        void completeBlock(IDCardReadService service, int readMode, Identityinfo info);
    }


}















