package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.StringUtils;
import com.vdin.JxProduct.db.HistoryListDB;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryDetailActivity extends BaseActivity {

    @BindView(R.id.history_detail_name)
    TextView historyDetailName;
    @BindView(R.id.history_detail_sex)
    TextView historyDetailSex;
    @BindView(R.id.history_detail_chepai)
    TextView historyDetailChepai;
    @BindView(R.id.history_detail_car_type)
    TextView historyDetailCarType;
    @BindView(R.id.history_detail_car_color)
    TextView historyDetailCarColor;
    @BindView(R.id.history_detail_info_desc)
    TextView historyDetailInfoDesc;
    @BindView(R.id.history_detail_time)
    TextView historyDetailTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
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
        setHeaderTitle("业务详情");
    }

    /**
     * 初始化参数
     */
    private void initParameter() {

        Intent intent = getIntent();

        if (intent.getStringExtra("id") == null) return;
        String orderId = intent.getStringExtra("id");

        HistoryListDB listDB = HistoryListDB.selectItem(orderId);

        if (listDB != null){
            String name = StringUtils.cutStrToName(listDB.getName());
            historyDetailName.setText(name);
            historyDetailSex.setText(listDB.getSex());
            historyDetailChepai.setText(listDB.getChepai());
            historyDetailCarType.setText(listDB.getType());
            historyDetailCarColor.setText(listDB.getColor());
            historyDetailInfoDesc.setText(listDB.getDescription());
            historyDetailTime.setText(listDB.getTime());
        }


    }

}







