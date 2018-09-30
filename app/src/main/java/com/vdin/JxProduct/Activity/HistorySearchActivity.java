package com.vdin.JxProduct.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.vdin.JxProduct.API.WorkApiRequest;
import com.vdin.JxProduct.Gson.WorkColorListGson;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.AllCapTransformationMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistorySearchActivity extends BaseActivity {

    @BindView(R.id.plate_number_edit)
    EditText plateNumberEdit;
    @BindView(R.id.name_edit)
    EditText nameEdit;
    @BindView(R.id.car_type_edit)
    EditText carTypeEdit;
    @BindView(R.id.color_choose_button)
    Button colorChooseButton;
    @BindView(R.id.search_button)
    Button searchButton;

    // 本类实例
    public static HistorySearchActivity myActivity;

    // 选择颜色name列表
    ArrayList<String> colorNameArr;
    // 当前选中颜色
    int selectcolor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_search);
        ButterKnife.bind(this);

        // 导航栏初始化
        initNavBar();
        // 参数初始化
        initParameter();
        // 初始化视图监听
        initViewLisener();
        // 初始化请求颜色列表
        initColorsList();
    }

    /**
     * 导航栏初始化
     */
    private void initNavBar() {
        fullScreen(this);
        setFitsSystemWindows(this, true);
        setHeaderleftTurnBack("");
        setHeaderTitle("历史业务查询");
    }

    /**
     * 初始化参数
     */
    private void initParameter() {
        // 本类实例
        myActivity = this;
        // 颜色列表
        colorNameArr = new ArrayList<>();
        // 全部颜色 即为不传
        colorNameArr.add("全部");
    }

    /**
     * 初始化视图监听
     */
    private void initViewLisener() {
        // 自动替换车牌大小写
        plateNumberEdit.setTransformationMethod(new AllCapTransformationMethod(true));
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
            colorNameArr.add("全部");
            // 获取颜色列表
            List<WorkColorListGson.CollectionBean.VehicleColorBean> vehicleColor = gson.getCollection().get(0).getVehicle_color();
            for (WorkColorListGson.CollectionBean.VehicleColorBean vehicleColorBean : vehicleColor) {
                colorNameArr.add(vehicleColorBean.getName());
            }
        }
    }

    @OnClick(R.id.color_choose_button)
    public void onColorChooseButtonClicked() {

        // 判断颜色数组
        if (colorNameArr == null || colorNameArr.size() == 0) {
            showToastWithMessage("正在重新请求颜色列表，请稍后重试！");
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

    @OnClick(R.id.search_button)
    public void onSearchButtonClicked() {

        String plateNumber = plateNumberEdit.getText().toString();
        String name = nameEdit.getText().toString();
        String vehicleModel = carTypeEdit.getText().toString();
        String vehicleColor = selectcolor == 0 ? "" : colorNameArr.get(selectcolor);

        Intent intent = new Intent();
        intent.putExtra("plateNumber",plateNumber);
        intent.putExtra("name",name);
        intent.putExtra("vehicleModel",vehicleModel);
        intent.putExtra("vehicleColor",vehicleColor);

        setResult(1,intent);

        finish();

    }



}





