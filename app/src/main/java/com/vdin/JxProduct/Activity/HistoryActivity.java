package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vdin.JxProduct.Adapter.WorkHistoryListAdapter;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.db.HistoryListDB;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity {

    // 本类实例
    public static HistoryActivity myActivity;
    // 存储历史记录数据
    private ArrayList<HistoryListDB> historyListArr = new ArrayList<>();
    // 存储当前页数
    private int page = 1;
    // 存储请求时间
    private Date startTime,endTime;
    // 历史列表显示适配器
    WorkHistoryListAdapter historyListAdapter;

    @BindView(R.id.history_search_bar)
    LinearLayout historySearchBar;
    @BindView(R.id.history_list_view)
    ListView historyListView;
    @BindView(R.id.history_refresh)
    SwipeRefreshLayout historyRefresh;
    @BindView(R.id.history_null_img)
    ImageView historyNullImg;
    @BindView(R.id.history_null_text)
    TextView historyNullText;
    @BindView(R.id.history_null_button)
    Button historyNullButton;
    @BindView(R.id.history_null_view)
    LinearLayout historyNullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        // 导航栏初始化
        initNavBar();
        // 参数初始化
        initParameter();
        // 初始化列表视图
        initHistoryListView();
        // 请求历史列表
        reloadHistoryList(1);

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
        // 当前请求页数
        page = 1;
        // 开始时间
        startTime = null;
        // 结束时间
        endTime = null;


    }

    /**
     * 初始化历史列表视图
     */
    private void initHistoryListView(){

        // 设置监听
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO 设置点击后跳转详情界面
//                Intent intent = new Intent(LjlsActivity.this, lsxqActivity.class);
//                intent.putExtra("name", list.get(position).name);
//                intent.putExtra("sex", list.get(position).sex);
//                intent.putExtra("chepai", list.get(position).chepai);
//                intent.putExtra("type", list.get(position).type);
//                intent.putExtra("color", list.get(position).color);
//                intent.putExtra("dsc", list.get(position).description);
//                intent.putExtra("time", list.get(position).time);
//
//                startActivity(intent);
            }
        });
    }

    /**
     * 请求历史列表
     * @param reloadPage 请求的页数
     */
    private void reloadHistoryList(final int reloadPage){
        // TODO 请求历史列表

    }

    /**
     * 搜索按钮点击响应事件
     */
    @OnClick(R.id.history_search_bar)
    public void onHistorySearchBarClicked() {
    }

    /**
     * 新增业务登记按钮点击响应事件
     */
    @OnClick(R.id.history_null_button)

    public void onHistoryNullButtonClicked() {
        Intent intent = new Intent(this, IdCardReadActivity.class);
        intent.putExtra("newxtid","下一步");
        this.startActivity(intent);
        this.finish();
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
