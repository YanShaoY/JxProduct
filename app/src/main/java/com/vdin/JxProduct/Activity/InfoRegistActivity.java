package com.vdin.JxProduct.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vdin.JxProduct.API.NetWorkCallBack;
import com.vdin.JxProduct.API.WorkApiRequest;
import com.vdin.JxProduct.Adapter.WorkRegistAddPicAdapter;
import com.vdin.JxProduct.Gson.BaseResponse;
import com.vdin.JxProduct.Gson.WorkAddRegistGson;
import com.vdin.JxProduct.Gson.WorkColorListGson;
import com.vdin.JxProduct.Model.WorkRegistAddPicInfo;
import com.vdin.JxProduct.OSSService.BitmapUtil;
import com.vdin.JxProduct.OSSService.FileUtils;
import com.vdin.JxProduct.OSSService.PermissionUtil;
import com.vdin.JxProduct.OSSService.PhotoUtils;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Service.IDCardReadService;
import com.vdin.JxProduct.Service.OssPhotoUpLoadService;
import com.vdin.JxProduct.Util.ActivityConllector;
import com.vdin.JxProduct.Util.AllCapTransformationMethod;
import com.vdin.JxProduct.Util.BaiduLocationUtil;
import com.vdin.JxProduct.Util.CoordinateUtils;
import com.vdin.JxProduct.Util.HttpUtil;
import com.vdin.JxProduct.Util.StringUtils;
import com.vdin.JxProduct.Util.ToolUtil;
import com.vdin.JxProduct.View.ConfirmDialog;
import com.vdin.JxProduct.View.GAConfirmDialog;
import com.vdin.JxProduct.View.WorkPicGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoRegistActivity extends BaseActivity {

    // 本类实例
    public static InfoRegistActivity myActivity;
    // 上个界面传入intent
    public Intent myIntent;
    // 身份证信息模型
    private IDCardReadService.Identityinfo myIdentityinfo;
    // 定位信息
    private BDLocation currentLocation = null;

    // 选择颜色name列表
    ArrayList<String> colorNameArr;
    // 选择颜色code列表
    ArrayList<String> colorCodeArr;
    // 当前选中颜色
    int selectcolor = 0;

    // 加急业务按钮选中状态
    Boolean urgent_business_button_type;

    // 默认拍照按钮信息
    public static WorkRegistAddPicInfo defaultAddPic = new WorkRegistAddPicInfo();
    // 存储拍照图片信息数组
    ArrayList<WorkRegistAddPicInfo> picInfoArrayList = new ArrayList<>();
    // 拍照GridView适配器
    WorkRegistAddPicAdapter addPicAdapter;
    // 最大拍照数量
    public static final int MaxPicSize = 9;

    @BindView(R.id.license_plate_number_edit)
    EditText licensePlateNumberEdit;
    @BindView(R.id.che_jia_number_edit)
    EditText cheJiaNumberEdit;
    @BindView(R.id.fa_dong_ji_number_edit)
    EditText faDongJiNumberEdit;
    @BindView(R.id.xuan_tian_text)
    TextView xuanTianText;
    @BindView(R.id.car_type_edit)
    EditText carTypeEdit;
    @BindView(R.id.color_choose_button)
    Button colorChooseButton;
    @BindView(R.id.desc_info_edit)
    EditText descInfoEdit;
    @BindView(R.id.desc_info_warn)
    TextView descInfoWarn;
    @BindView(R.id.regist_pic_grid_view)
    WorkPicGridView registPicGridView;
    @BindView(R.id.urgent_business_button)
    ImageButton urgentBusinessButton;
    @BindView(R.id.complete_button_id)
    Button completeButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_regist);
        ButterKnife.bind(this);

        // 导航栏初始化
        initNavBar();
        // 参数初始化
        initParameter();
        // 初始化视图监听
        initViewLisener();
        // 初始化请求颜色列表
        initColorsList();
        // 初始化拍照列表视图
        initWorkPicGridView();

        // 初始化测试数据
//        testData();
    }

    /**
     * 导航栏初始化
     */
    private void initNavBar() {
//        fullScreen(this);
//        setFitsSystemWindows(this, true);
        setHeaderleftTurnBack("");
        setHeaderTitle("新增业务登记");
    }

    /**
     * 初始化参数
     */
    private void initParameter() {

        // 本类实例
        myActivity = this;
        // 上个界面传入的intent
        myIntent = getIntent();
        // 上个界面传入身份信息
        myIdentityinfo = (IDCardReadService.Identityinfo) myIntent.getParcelableExtra("identityinfo");
        // 加急按钮选中状态
        urgent_business_button_type = false;
    }

    /**
     * 初始化默认数据 测试用
     */
    private void testData() {

        licensePlateNumberEdit.setText("川A-88888");
        cheJiaNumberEdit.setText("WDDHF5EB0AA071919");
        faDongJiNumberEdit.setText("6A-6108");
        carTypeEdit.setText("奥迪A6");
        descInfoEdit.setText("测试的数据，简单的描述了一下");
    }

    /**
     * 初始化视图监听
     */
    private void initViewLisener() {

        // 设置发动机监听事件 改变标签选填显示状态
        faDongJiNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (faDongJiNumberEdit.getText().toString().equals("")) {
                    xuanTianText.setVisibility(View.VISIBLE);
                } else {
                    xuanTianText.setVisibility(View.GONE);
                }
            }
        });

        // 设置业务描述输入框监听 改变提示字数警告标签文字
        descInfoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                descInfoWarn.setText("还可以输入" + (200 - descInfoEdit.getText().length()) + "个字");
            }
        });

        // 自动替换车架号大写
        cheJiaNumberEdit.setTransformationMethod(new AllCapTransformationMethod(true));
        // 设置车架号输入框监听
        cheJiaNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

//                if (cheJiaNumberEdit.getText().length() >= 17) {
//                        showToastWithMessage("车架号不正确");
//                }
            }
        });

        // 自动替换车牌大小写
        licensePlateNumberEdit.setTransformationMethod(new AllCapTransformationMethod(true));

    }

    /**
     * 初始化请求颜色列表
     */
    private void initColorsList() {

        WorkApiRequest.queryDicColor((isSuccess, object) -> {

            if (isSuccess) {

                // json字符串
                String responseStr = (String) object;

                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    WorkColorListGson colorListGson = new Gson().fromJson(jsonObject.toString(), WorkColorListGson.class);
                    // 保存颜色的name数组，和code数组
                    saveColorListArrWith(colorListGson);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            } else {
                Log.d(this.toString(), "completeBlock: " + object);
            }

        });

    }

    /**
     * 保存颜色的name数组，和code数组
     *
     * @param gson Gson解析模型
     */
    private void saveColorListArrWith(WorkColorListGson gson) {

        // 请求成功
        if (gson.isSuccess()) {
            // 初始化颜色存储数组
            colorNameArr = new ArrayList<>();
            colorCodeArr = new ArrayList<>();
            // 获取颜色列表
            List<WorkColorListGson.CollectionBean.VehicleColorBean> vehicleColor = gson.getCollection().get(0).getVehicle_color();
            for (WorkColorListGson.CollectionBean.VehicleColorBean vehicleColorBean : vehicleColor) {
                colorNameArr.add(vehicleColorBean.getName());
                colorCodeArr.add(vehicleColorBean.getCode());
            }
        }
    }

    /**
     * 初始化拍照列表视图
     */
    private void initWorkPicGridView() {

        // 默认拍照按钮信息
        defaultAddPic.bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.btn_photo);
        defaultAddPic.path = "-1";
        defaultAddPic.url = null;

        // 加入默认拍照视图
        picInfoArrayList.clear();
        picInfoArrayList.add(defaultAddPic);

        // 初始化GridView适配器
        addPicAdapter = new WorkRegistAddPicAdapter(this, picInfoArrayList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取主线程
                android.os.Handler mainHandle = new android.os.Handler(Looper.getMainLooper());
                mainHandle.post(() -> {
                    // 拍摄照片
                    PhotoUtils.takePicture(myActivity);
                });
            }
        });

        // GridView加载适配器
        registPicGridView.setAdapter(addPicAdapter);

    }

    /******************************************************** Activity 生命周期 ***********************************************************/

    /**
     * 拍摄照片以后的回传接口
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //拍照 成功
        if (requestCode == PhotoUtils.RESULT_TAKE_CAMERA && resultCode == RESULT_OK) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (picInfoArrayList.size() <= MaxPicSize) {
                        // 获取图片的Bitmap
                        Bitmap bitmap = BitmapUtil.compressFile(mContext, PhotoUtils.currPicUrl, 100, 100);
                        // 用URL字符串存储拍照本地路径
                        String countPath = PhotoUtils.currPicUrl;
                        // 删除本地路径的原图
                        FileUtils.delFileByLocalPath(countPath);
                        // 并将压缩后的图片存储到本地
                        boolean result = BitmapUtil.saveBitmap(bitmap, countPath);
                        if (!result) {
                            countPath = null;
                            showToastWithMessage("图片存储本地失败，请重新拍照");

                        } else {
                            // 删除默认拍照视图
                            picInfoArrayList.remove(defaultAddPic);
                            // 创建图片信息
                            WorkRegistAddPicInfo info = new WorkRegistAddPicInfo();
                            info.bitmap = bitmap;
                            info.path = countPath;
                            // 添加图片
                            picInfoArrayList.add(info);
                            // 判断图片数量 添加拍照按钮
                            if (picInfoArrayList.size() < MaxPicSize) {
                                picInfoArrayList.add(defaultAddPic);
                            }
                            addPicAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }, 100);

        } else {
//            Drawable top = getResources().getDrawable(R.mipmap.ksdj_btn_camera_big);
//            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
//            cardSceneButton.setCompoundDrawables(null, top, null, null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 请求定位
        PermissionUtil.checkLocationPermission(this);
        // 开始请求定位
        BaiduLocationUtil.getInstance().requestLocation((isSuccess, location) -> {
            if (isSuccess){
                currentLocation = location;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 手动结束定位
        BaiduLocationUtil.getInstance().stopLocation();
    }

    /******************************************************** 按钮响应事件 ***********************************************************/

    /**
     * 颜色选择按钮点击响应事件
     */
    @OnClick(R.id.color_choose_button)
    public void onColorChooseButtonClicked() {
        // 判断颜色数组
        if (colorNameArr == null || colorNameArr.size() == 0) {
            showToastWithMessage("正在重新请求颜色列表，请稍后重试！");
            initColorsList();
            return;
        }

        // 初始化dialog数据源
        final String[] array = (String[]) colorNameArr.toArray(new String[colorNameArr.size()]);
        // dialog构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置标题
        builder.setTitle("颜色选择");
        builder.setSingleChoiceItems(array, selectcolor, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectcolor = which;
                colorChooseButton.setText(array[which]);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 加急按钮点击响应事件
     */
    @OnClick(R.id.urgent_business_button)
    public void onUrgentBusinessButtonClicked() {

        if (!urgent_business_button_type) {

            ConfirmDialog dialog = new ConfirmDialog(this);
            dialog.showWarningDialog("确定开启加急业务吗？", "选中后审核优先级为最高", new ConfirmDialog.ConfirmDialogAction() {
                @Override
                public void sureButtonAction() {
                    urgent_business_button_type = true;
                    urgentBusinessButton.setImageResource(R.mipmap.open_icon);
                }

                @Override
                public void cancelButtonAction() {
                    urgent_business_button_type = false;
                    urgentBusinessButton.setImageResource(R.mipmap.close);
                }
            });

        } else {
            urgent_business_button_type = false;
            urgentBusinessButton.setImageResource(R.mipmap.close);
        }

    }

    /**
     * 提交按钮点击响应事件
     */
    @OnClick(R.id.complete_button_id)
    public void onCompleteButtonIdClicked() {

        // 判断网络状态
        if (!HttpUtil.isNetworkConnected(this)) {
            showToastWithMessage("无可用网络，请检查网路设置");
            return;
        }

        // 判断是否是加急业务
        if (!urgent_business_button_type) {

            // 条件判断
            String showMsgStr = null;
            if (licensePlateNumberEdit.getText().length() < 6) {
                showMsgStr = "请正确填写车牌号";
            } else if (!ToolUtil.checkVIN(cheJiaNumberEdit.getText().toString().trim().toUpperCase())) {
                showMsgStr = "请输入正确的车架号";
            } else if (carTypeEdit.getText().length() == 0) {
                showMsgStr = "请填写车型";
            } else if (colorChooseButton.getText().equals("未选择")) {
                showMsgStr = "请选择车辆颜色";
            } else if (descInfoEdit.getText().length() == 0) {
                showMsgStr = "请填写业务描述";
            } else if (picInfoArrayList.size() < 2) {
                showMsgStr = "请拍摄业务照片";
            }

            // 提示弹窗
            if (!StringUtils.isEmpty(showMsgStr)) {
                showToastWithMessage(showMsgStr);
                return;
            }
        }

        // 判断是否获取到定位
        if (currentLocation == null || StringUtils.isEmpty(currentLocation.getAddrStr())){

            if (!PermissionUtil.checkPermissionsIsSuccess(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showDialogTipUserGoToAppSettting("定位");
            }else {
                showToastWithMessage("尚未获取到定位信息，请检查定位设置或稍后重试");
            }
            return;
        }

        // 关闭提交按钮响应
        completeButtonId.setClickable(false);
        // 显示加载弹窗
        showProgressDialog("数据提交中···");
        // 删除默认拍照按钮
        picInfoArrayList.remove(defaultAddPic);

        String scenePhotoPath = "";
        if (myIdentityinfo.idtype.equals("1")) {
            scenePhotoPath = myIdentityinfo.customer_identity_card_portraits;
        } else {
            scenePhotoPath = myIdentityinfo.customer_identity_card_photos;
        }
        String scenePhotoUrl = myIntent.getStringExtra("scenePhotoUrl");
        // 若未上传身份证照片 则上传身份证照片
        if (!StringUtils.isEmpty(scenePhotoPath) && StringUtils.isEmpty(scenePhotoUrl)) {
            upLoadIdCardPhoto(scenePhotoPath);
        } else { // 若已上传 则开始上传业务图片
            upLoadPicInfoArrPhoto();
        }

    }

    /******************************************************** 数据上传 ***********************************************************/

    /**
     * 上传身份证照片
     *
     * @param photoPath 身份证照片本地路径地址
     */
    public void upLoadIdCardPhoto(String photoPath) {

        // 上传身份证照片
        WorkApiRequest.upLoadPhoto(photoPath, -1, new NetWorkCallBack() {
            @Override
            public void completeBlock(boolean isSuccess, Object object) {
                // 获取返回信息
                HashMap<String, Object> map = (HashMap<String, Object>) object;
                // 位置
                int index = (int) map.get("index");
                if (index != -1) return;

                if (isSuccess) {
                    // 获取URL
                    String url = (String) map.get("netUrl");
                    myActivity.myIntent.putExtra("scenePhotoUrl", url);
                    // 删除本地图片
                    String path = (String) map.get("filePath");
                    if (!StringUtils.isEmpty(path)) {
                        FileUtils.delFileByLocalPath(path);
                    }
                    // 上传业务图片
                    upLoadPicInfoArrPhoto();

                } else {
                    closeProgressDialog();
                    // 开启提交按钮响应
                    completeButtonId.setClickable(true);
                    showUploadError("身份信息图片上传失败，请重试！！！");
                }
            }
        });
    }

    /**
     * 上传业务图片数组
     */
    public void upLoadPicInfoArrPhoto() {

        if (urgent_business_button_type && picInfoArrayList.size() == 0) {
            // 数据提交
            dataSubmitted();

        } else {

            OssPhotoUpLoadService.upLoadPhoto(picInfoArrayList, (isSuccess, object) -> {

                ArrayList<WorkRegistAddPicInfo> arrayList = (ArrayList<WorkRegistAddPicInfo>) object;
                picInfoArrayList = arrayList == null ? arrayList : picInfoArrayList;
                if (!isSuccess) {
                    closeProgressDialog();
                    // 开启提交按钮响应
                    completeButtonId.setClickable(true);
                    showUploadError("业务图片上传失败，请重试！！！");

                } else {
                    dataSubmitted();
                }

            });

        }


    }

    /**
     * 数据提交
     */
    private void dataSubmitted() {

        //上传填入的信息

        // 数据模型
        WorkAddRegistGson gson = new WorkAddRegistGson();

        // 身份信息
        String scenePhotoUrl = myIntent.getStringExtra("scenePhotoUrl");

        WorkAddRegistGson.CustomerInfoBean infoBean = new WorkAddRegistGson.CustomerInfoBean();

        infoBean.setBirthDate(myIdentityinfo.customer_identity_card_birth_date);
        infoBean.setCurrentAddress(myIdentityinfo.customer_identity_card_address);
        infoBean.setEthnicityCode(myIdentityinfo.customer_identity_card_ethnicity_code);
        infoBean.setGenderCode(myIdentityinfo.customer_identity_card_gender_code);
        infoBean.setIdNumber(myIdentityinfo.customer_identification_number);
        infoBean.setIdPhotoUrl(scenePhotoUrl);
        infoBean.setIdentificationType(Integer.parseInt(myIdentityinfo.idtype));
        infoBean.setIssuingAuthority(myIdentityinfo.customer_identity_card_issuing_authority_name);
        infoBean.setName(myIdentityinfo.customer_name);
        infoBean.setPermanentAddress(myIdentityinfo.customer_identity_card_address);
        infoBean.setPhone(myIdentityinfo.customer_mobile_phone_number);
        infoBean.setValidityFromDate(myIdentityinfo.customer_identity_card_validity_from_date);
        infoBean.setValidityThruDate(myIdentityinfo.customer_identity_card_validity_thru_date);

        gson.setCustomerInfo(infoBean);

        // 地址
        gson.setAddress(currentLocation.getAddrStr());

        // GCJ02 坐标
        double gcjLat = currentLocation.getLatitude();
        double gcjLon = currentLocation.getLongitude();
        // WGS84 坐标
        double[] wgsLocation = CoordinateUtils.gcj02ToWGS84(gcjLon,gcjLat);
        double wgsLat = wgsLocation[1];
        double wgsLon = wgsLocation[0];

                // 经纬度
        gson.setLatitude(gcjLat);
        gson.setLongitude(gcjLon);

        // 内置经纬度封装
        WorkAddRegistGson.PositionBean positionBean = new WorkAddRegistGson.PositionBean();
        positionBean.setGcjLat(gcjLat);
        positionBean.setGcjLon(gcjLon);
        positionBean.setWgsLat(wgsLat);
        positionBean.setWgsLon(wgsLon);
        gson.setPosition(positionBean);


        // 车牌号
        gson.setPlateNumber(licensePlateNumberEdit.getText().toString());
        // 业务描述
        gson.setServiceDescription(descInfoEdit.getText().toString());
        // 是否加急
        gson.setUrgentBiz(urgent_business_button_type);
        // 颜色code
        if (colorChooseButton.getText().equals("未选择")) {
            gson.setVehicleColorCode("");
        } else {
            gson.setVehicleColorCode(colorCodeArr.get(selectcolor));
        }

        // 发动机号
        gson.setVehicleEngineNumber(faDongJiNumberEdit.getText().toString());
        // 车架号
        gson.setVehicleIdentifyNumber(cheJiaNumberEdit.getText().toString());
        // 车型
        gson.setVehicleModel(carTypeEdit.getText().toString());
        // 业务照片
        List<WorkAddRegistGson.BizPhotoDTOListBean> beans = new ArrayList<>();
        for (int i = 0; i < picInfoArrayList.size(); i++) {
            WorkAddRegistGson.BizPhotoDTOListBean bean = new WorkAddRegistGson.BizPhotoDTOListBean();
            bean.setOrder_number(i);
            bean.setPhoto_name("业务照片");
            bean.setPhoto_url(picInfoArrayList.get(i).url);
            beans.add(bean);
        }
        gson.setBizPhotoDTOList(beans);

        // 数据提交
        WorkApiRequest.addRegister(gson, (isSuccess, object) -> {

            runOnUiThread(() -> {

                closeProgressDialog();
                // 开启提交按钮响应
                completeButtonId.setClickable(true);

                if (isSuccess) {
                    // 数据解析
                    String responseStr = (String) object;
                    Gson myGson = new GsonBuilder().serializeNulls().create();
                    BaseResponse response = myGson.fromJson(responseStr,BaseResponse.class);

                    // 验证成功
                    if (response.isSuccess()){
                        showAlertDialog();
                    }else {
                        String msg = response.getMessage();
                        msg = msg.length() > 0 ? msg : "网络错误";
                        showUploadError(msg);
                    }

                } else {
                    showUploadError("数据提交失败，请重试！！！");
                }
            });
        });

    }

    /******************************************************** 弹窗提示 ***********************************************************/

    /**
     * 显示成功弹窗
     */
    public void showAlertDialog() {

        // 提示弹窗
        ConfirmDialog dialog = new ConfirmDialog(this);
        dialog.dialogBgimgRes = R.mipmap.success;
        dialog.titleMsg = "登记提交成功";
        dialog.titleColor = "#69b978";
        dialog.tipMsg = "您可以选择继续登记或返回首页";
        dialog.sureBtTitle = "返回首页";
        dialog.cancelBtTitle = "继续登记";
        dialog.dialogAction = new ConfirmDialog.ConfirmDialogAction() {
            @Override
            public void sureButtonAction() {
                // 删除所有图片
                DeleteAllPic();
                // 回到主页
                ActivityConllector.popToMainActivity();
            }

            @Override
            public void cancelButtonAction() {
                // 删除所有图片
                DeleteAllPic();
                // 继续登记 初始化数据
                reloadDefaultData();
            }
        };

        dialog.show();
    }

    /**
     * 信息提交失败
     */
    public void showUploadError(String msg) {
        showToastWithMessage(msg);
        if (picInfoArrayList.size() < MaxPicSize) {
            if (!picInfoArrayList.contains(defaultAddPic)) {
                picInfoArrayList.add(defaultAddPic);
            }
        }
    }

    /******************************************************** 数据初始化 ***********************************************************/

    /**
     * 初始化数据显示 在提交成功或失败以后
     */
    public void reloadDefaultData() {


        selectcolor = 0;
        urgent_business_button_type = false;
        initWorkPicGridView();

        licensePlateNumberEdit.setText("");
        cheJiaNumberEdit.setText("");
        faDongJiNumberEdit.setText("");
        xuanTianText.setVisibility(View.VISIBLE);
        colorChooseButton.setText("未选择");
        carTypeEdit.setText("");
        descInfoEdit.setText("");
        descInfoWarn.setText("还可以输入" + (200 - descInfoEdit.getText().length()) + "个字");
        urgentBusinessButton.setImageResource(R.mipmap.close);
    }

    /**
     * 重写父类返回按钮方法
     */
    @Override
    protected void backButtonAction() {
        GAConfirmDialog dialog = new GAConfirmDialog(this, GAConfirmDialog.DialogStyle.DEFAULT);
        dialog.setupTitle("确定退出?", "#ff6464")
                .tip("退出后将丢失已录入信息")
                .isShowSingleButton(false)
                .bgIcon(R.mipmap.ask)
                .sure(v -> {
                    finish();
                })
                .show();

    }

    /**
     * 删除所有的本地图片
     */
    public void DeleteAllPic() {

        for (WorkRegistAddPicInfo info : picInfoArrayList) {
            FileUtils.delFileByLocalPath(info.path);
        }
        picInfoArrayList.clear();
    }

    /**
     * 界面注销方法
     */
    @Override
    protected void onDestroy() {
        DeleteAllPic();
        Log.d(this.toString(), "onDestroy");
        super.onDestroy();
    }


}
























