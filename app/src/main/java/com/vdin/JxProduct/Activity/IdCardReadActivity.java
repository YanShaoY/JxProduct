package com.vdin.JxProduct.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.vdin.JxProduct.OSSService.BitmapUtil;
import com.vdin.JxProduct.OSSService.FileUtils;
import com.vdin.JxProduct.OSSService.PhotoUtils;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Service.IDCardReadService;
import com.vdin.JxProduct.Util.StringUtils;
import com.vdin.JxProduct.Util.ToolUtil;
import com.vdin.JxProduct.View.GAConfirmDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdCardReadActivity extends BaseActivity implements IDCardReadService.IdCardServiceBlock{

    // 控件声明
    @BindView(R.id.card_number_edit)
    EditText cardNumberEdit;
    @BindView(R.id.card_name_edit)
    EditText cardNameEdit;
    @BindView(R.id.card_phone_edit)
    EditText cardPhoneEdit;
    @BindView(R.id.card_image_button)
    Button cardImageButton;
    @BindView(R.id.card_scene_button)
    Button cardSceneButton;
    @BindView(R.id.next_button_id)
    Button nextButtonId;

    // 现场照片的本地路径
    private String scenePhotoUrl = "";
    // 存储本类实例
    public static Activity idCardActivity = null;
    // 身份证读取服务
    private IDCardReadService myService;

    /**
     * 视图创建方法
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card_read);
        ButterKnife.bind(this);

        idCardActivity = this;
        myService = new IDCardReadService(this, this);

        fullScreen(this);
        setFitsSystemWindows(this, true);
        setHeaderleftTurnBack("");
        setHeaderTitle("身份验证");

        testData();

    }

    public void testData(){

        cardNumberEdit.setText("511528199501151612");
        cardNameEdit.setText("严少颜");
        cardPhoneEdit.setText("17602887120");

        InputMethodManager imms = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imms.hideSoftInputFromWindow(cardNumberEdit.getWindowToken(), 0);
    }

    /**
     * 证件照片按钮点击响应事件
     */
    @OnClick(R.id.card_image_button)
    public void onCardImageButtonClicked() {

    }

    /**
     * 现场照片按钮点击响应事件
     */
    @OnClick(R.id.card_scene_button)
    public void onCardSceneButtonClicked() {
        // 拍摄照片
        PhotoUtils.takePicture(this);
    }

    /**
     * 拍摄照片以后的回传接口
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //拍照 成功
        if (requestCode == PhotoUtils.RESULT_TAKE_CAMERA && resultCode == RESULT_OK) {

            // 获取图片的Bitmap
            Bitmap bitmap = BitmapUtil.compressFile(mContext, PhotoUtils.currPicUrl, 250, 310);
            // 版本适配 设置按钮 将图片传入作为背景
            if (Build.VERSION.SDK_INT >= 16)
                cardSceneButton.setBackground(new BitmapDrawable(getResources(), bitmap));
            else  {
                cardSceneButton.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }

            // 用URL字符串存储拍照本地路径
            scenePhotoUrl = PhotoUtils.currPicUrl;
            // 删除本地路径的原图
            FileUtils.delFileByLocalPath(scenePhotoUrl);
            // 并将压缩后的图片存储到本地
            boolean result = BitmapUtil.saveBitmap(bitmap,scenePhotoUrl);
            if (!result){
                scenePhotoUrl = null;
                showToastWithMessage("图片存储本地失败，请重新拍照");
            }

            cardSceneButton.setCompoundDrawables(null, null, null, null);
            cardSceneButton.setText("");

        }else {
//            Drawable top = getResources().getDrawable(R.mipmap.ksdj_btn_camera_big);
//            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
//            cardSceneButton.setCompoundDrawables(null, top, null, null);
        }
    }

    /**
     * 下一步按钮点击响应事件
     */
    @OnClick(R.id.next_button_id)
    public void onNextButtonIdClicked() {
        // 身份证号码判断
        if (!ToolUtil.personIdValidation(cardNumberEdit.getText().toString().trim())){
            showToastWithMessage("身份证号码输入不正确");
            return;
        }

        // 姓名判断
        if (StringUtils.isEmpty(cardNameEdit.getText().toString().trim())){
            showToastWithMessage("请填写正确的姓名");
            return;
        }

        // 电话号码判断
        if (!ToolUtil.isMobileNO(cardPhoneEdit.getText().toString().trim())){
            showToastWithMessage("请输入正确的手机号码");
            return;
        }

        // 现场照片拍照判断
        if (StringUtils.isEmpty(scenePhotoUrl)){
            showToastWithMessage("请拍摄现场照片");
            return;
        }

        // 判断无误，跳转下一个界面
        Intent intent = new Intent(this, InfoRegistActivity.class);
        intent.putExtra("idCardNumber",cardNumberEdit.getText().toString().trim());
        intent.putExtra("name",cardNameEdit.getText().toString().trim());
        intent.putExtra("phoneNumber",cardPhoneEdit.getText().toString().trim());
        intent.putExtra("scenePhotoPath",scenePhotoUrl.toString());
        startActivity(intent);


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
     * 界面注销时 删除本地图片
     */
    @Override
    protected void onDestroy() {
        // 删除本地路径的原图
        if (StringUtils.isEmpty(scenePhotoUrl)){
            FileUtils.delFileByLocalPath(scenePhotoUrl);
        }

        scenePhotoUrl = null;

        super.onDestroy();
    }

    @Override
    public void onFailure(IDCardReadService service, int readMode, int errorCode) {

    }

    @Override
    public void completeBlock(boolean isSuccess, Object object) {

    }
}
























