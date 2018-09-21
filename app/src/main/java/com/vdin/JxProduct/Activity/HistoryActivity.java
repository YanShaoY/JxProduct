package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vdin.JxProduct.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity {

    // 本类实例
    public static HistoryActivity myActivity;

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
