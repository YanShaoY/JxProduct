package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vdin.JxProduct.API.MetaDataApiRequest;
import com.vdin.JxProduct.API.NetWorkCallBack;
import com.vdin.JxProduct.API.WorkApiRequest;
import com.vdin.JxProduct.Adapter.WorkHistoryListAdapter;
import com.vdin.JxProduct.Gson.WorkHistoryListGson;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.DateUtil;
import com.vdin.JxProduct.Util.HttpUtil;
import com.vdin.JxProduct.db.HistoryListDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity {

    @BindView(R.id.history_search_bar)
    LinearLayout historySearchBar;
    @BindView(R.id.history_list_view)
    ListView historyListView;
    @BindView(R.id.history_refresh)
    RefreshLayout historyRefresh;
    @BindView(R.id.history_null_img)
    ImageView historyNullImg;
    @BindView(R.id.history_null_text)
    TextView historyNullText;
    @BindView(R.id.history_null_button)
    Button historyNullButton;
    @BindView(R.id.history_null_view)
    LinearLayout historyNullView;

    // 本类实例
    public static HistoryActivity myActivity;
    // 存储历史记录数据
    private ArrayList<HistoryListDB> historyListArr;
    // 存储当前页数
    private int page = 1;
    // 历史列表显示适配器
    WorkHistoryListAdapter historyListAdapter;
    // 搜索界面回传参数
    Intent searchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        // 导航栏初始化
        initNavBar();
        // 参数初始化
        initParameter();
        // 隐藏无数据视图
        showNullViewWithType(false);
        // 初始化列表视图
        initHistoryListView();
        // 初始化刷新控件
        initRefresh();
    }

    /**
     * 导航栏初始化
     */
    private void initNavBar() {
        fullScreen(this);
        setFitsSystemWindows(this, true);
        setHeaderleftTurnBack("");
        setHeaderTitle("历史业务列表");
    }

    /**
     * 初始化参数
     */
    private void initParameter() {

        // 本类实例
        myActivity = this;
        // 数据源
        historyListArr = new ArrayList<>();
        // 当前请求页数
        page = 1;
        // 回传参数
        searchIntent = null;
    }

    /**
     * 显示或隐藏无数据视图
     *
     * @param isShowNull
     */
    private void showNullViewWithType(boolean isShowNull) {

        // 禁用或开启下拉刷新和上拉加载更多
        historyRefresh.setEnableRefresh(!isShowNull);
        historyRefresh.setEnableLoadMore(!isShowNull);

        // 若为true
        if (isShowNull) {
            // 隐藏历史列表视图
            historyRefresh.getLayout().setVisibility(View.GONE);
            // 显示无数据视图
            historyNullView.setVisibility(View.VISIBLE);
        } else {
            // 显示历史列表视图
            historyRefresh.getLayout().setVisibility(View.VISIBLE);
            // 隐藏无数据视图
            historyNullView.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化历史列表视图
     */
    private void initHistoryListView() {

        // 设置视图适配器
        historyListAdapter = new WorkHistoryListAdapter(HistoryActivity.this, historyListArr);
        historyListView.setAdapter(historyListAdapter);

        // 设置监听
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击后跳转详情界面
                Intent intent = new Intent(HistoryActivity.this, HistoryDetailActivity.class);
                intent.putExtra("id", historyListArr.get(position).getId());
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化刷新控件
     */
    private void initRefresh() {

        // 未满一页 不许加载更多
        historyRefresh.setEnableLoadMoreWhenContentNotFull(false);
        // 刷新时禁止操作视图
        historyRefresh.setDisableContentWhenRefresh(true);
        // 加载时禁止操作视图
        historyRefresh.setDisableContentWhenLoading(true);

        // 设置刷新事件监听
        historyRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                // 清空数据
                historyListArr.clear();
                page = 1;
                searchIntent = null;
                // 请求历史列表
                reloadHistoryList(page);
            }
        });
        historyRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                page ++;
                // 请求历史列表
                reloadHistoryList(page);
            }
        });

        historyRefresh.autoRefresh(0);
    }

    /**
     * 请求历史列表
     *
     * @param reloadPage 请求的页数
     */
    private void reloadHistoryList(final int reloadPage) {

        // 判断网络状态
        if (!HttpUtil.isNetworkConnected(this)) {
            finishRefresh(false);
            showToastWithMessage("无可用网络，请检查网路设置");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("perPage", 10);

        // 获取时间
        Date today = DateUtil.getTimesmorning();
        Date threeDaysAgo = DateUtil.getDateBefore(today,-3);

        String format = "yyyy-MM-dd";
        params.put("createAtBegin", DateUtil.getStringByFormat(threeDaysAgo,format));
        params.put("createAtEnd", DateUtil.getStringByFormat(today,format));


        if (searchIntent != null){

            String plateNumber = searchIntent.getStringExtra("plateNumber");
            String name = searchIntent.getStringExtra("name");
            String vehicleModel = searchIntent.getStringExtra("vehicleModel");
            String vehicleColor = searchIntent.getStringExtra("vehicleColor");

            params.put("plateNumber", plateNumber);
            params.put("name", name);
            params.put("vehicleModel", vehicleModel);
            params.put("vehicleColor", vehicleColor);
        }

        // 请求历史列表
        WorkApiRequest.queryHistoryBusiness(params, new NetWorkCallBack() {
            @Override
            public void completeBlock(boolean isSuccess, Object object) {

                // 关闭刷新
                finishRefresh(isSuccess);

                // 请求成功
                if (isSuccess) {
                    // json字符串
                    String responseStr = (String) object;
                    WorkHistoryListGson responseModel = new Gson().fromJson(responseStr, WorkHistoryListGson.class);
                    if (responseModel.isSuccess()) {
                        // 入库入库
                        saveHistoryList(responseModel);

                    } else if (responseModel.getMessage().length() > 0) {
                        // 显示提示
                        showToastWithMessage(responseModel.getMessage());
                    }

                } else {
                    // 根据页数查询数据
                    ArrayList<HistoryListDB> result = new ArrayList<>(HistoryListDB.selectListForPage(page));
                    historyListArr.addAll(result);
                    if (historyListArr.isEmpty()) {
                        // 若请求失败，且数据库无数据
                        historyNullText.setText("没加载出来，请检查网络链接");
                        historyNullButton.setText("重新加载");
                        showNullViewWithType(true);

                    } else {
                        showNullViewWithType(false);
                    }

                }
                // 刷新数据显示
                reloadDataSource();

            }
        });

    }

    /**
     * 入库入库
     *
     * @param responseModel json数据模型
     */
    private void saveHistoryList(WorkHistoryListGson responseModel) {

        // 考虑没有更多数据情况
        if (responseModel.getCollection().size() == 0) {

            this.runOnUiThread(() -> {

                // 刷新为空 后台无数据
                if (page == 1) {

                    historyNullText.setText("暂无业务信息");
                    historyNullButton.setText("马上新增业务");
                    showNullViewWithType(true);

                } else {
                    // 最后一页传true
                    historyRefresh.setNoMoreData(true);
                }
            });

            return;
        }

        // 遍历Collection数据
        for (WorkHistoryListGson.CollectionBean collectionBean : responseModel.getCollection()) {
            // 数据库载入
            HistoryListDB listInfo = new HistoryListDB();
            listInfo.setId(collectionBean.getMotorVehicleMaintenance().getId());
            listInfo.setTime(collectionBean.getMotorVehicleMaintenance().getCreatedAt());
            listInfo.setColor(collectionBean.getMotorVehicleMaintenance().getVehicleColor().getName());
            listInfo.setName(collectionBean.getMotorVehicleMaintenance().getPractitioner().getName());
            listInfo.setDescription(collectionBean.getMotorVehicleMaintenance().getServiceDescription());
            listInfo.setChepai(collectionBean.getMotorVehicleMaintenance().getPlateNumber());
            listInfo.setSex(collectionBean.getMotorVehicleMaintenance().getPractitioner().getGender().getName());
            listInfo.setType(collectionBean.getMotorVehicleMaintenance().getVehicleModel());
            if (collectionBean.getMaintenancePhotos().size() > 0) {
                listInfo.setPic(collectionBean.getMaintenancePhotos().get(0).getPhotoUrl());
            }

            // DataSource载入
            historyListArr.add(listInfo);

            // 判断数据库时候已有数据
            if (HistoryListDB.selectItem(collectionBean.getMotorVehicleMaintenance().getId()) == null) {
                listInfo.save();
            }

        }
    }

    /**
     * 停止刷新
     *
     * @param success 是否刷新成功
     */
    private void finishRefresh(boolean success) {

        this.runOnUiThread(() -> {
            if (page == 1) {
                historyRefresh.finishRefresh(success);
            } else {
                historyRefresh.finishLoadMore(success);
            }
        });


    }

    /**
     * 刷新数据
     */
    private void reloadDataSource() {

        this.runOnUiThread(() -> {
            if (historyListArr.size() > 0) {
                historyListAdapter.notifyDataSetChanged();
            } else {
                showNullViewWithType(true);
            }
        });

    }

    /**
     * 搜索按钮点击响应事件
     */
    @OnClick(R.id.history_search_bar)
    public void onHistorySearchBarClicked() {
        // 跳转查询界面
        Intent intent = new Intent(myActivity,HistorySearchActivity.class);
        startActivityForResult(intent,1);
    }

    /**
     * 搜索界面参数回调
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data 返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 1){
            // 清空数据
            historyListArr.clear();
            page = 1;
            searchIntent = data;
            // 请求历史列表
            reloadHistoryList(page);
        }
    }

    /**
     * 新增业务登记按钮点击响应事件
     */
    @OnClick(R.id.history_null_button)
    public void onHistoryNullButtonClicked() {

        if (historyNullButton.getText().toString() == "重新加载") {

            showNullViewWithType(false);
            historyRefresh.autoRefresh();

        } else {
            Intent intent = new Intent(this, IdCardReadActivity.class);
            intent.putExtra("newxtid", "下一步");
            this.startActivity(intent);
            this.finish();
        }

    }

    /**
     * 界面注销方法
     */
    @Override
    protected void onDestroy() {
        Log.d(this.toString(), "onDestroy");
        super.onDestroy();
    }


}









