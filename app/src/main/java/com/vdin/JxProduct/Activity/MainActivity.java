package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.hjm.bottomtabbar.BottomTabBar;
import com.vdin.JxProduct.Fragment.ContactsFragment;
import com.vdin.JxProduct.Fragment.MessagesFragment;
import com.vdin.JxProduct.Fragment.MyFragment;
import com.vdin.JxProduct.Fragment.WorkFragment;
import com.vdin.JxProduct.Fragment.WorkFragment_ViewBinding;
import com.vdin.JxProduct.LaunchActivity;
import com.vdin.JxProduct.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    // 底部导航栏
    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        bottomTabBar
                .init(getSupportFragmentManager())                        // 初始化方法，必须第一个调用；传入参数为V4包下的FragmentManager
                .setImgSize(dp2px(22),dp2px(22))                         // 设置ICON图片的尺寸
                .setFontSize(11)                                          // 设置文字的尺寸
                .setTabPadding(dp2px(3),5,dp2px(5))               // 设置ICON图片与上部分割线的间隔、图片与文字的间隔、文字与底部的间隔
                .setChangeColor(Color.parseColor("#1271F0"), Color.parseColor("#B2DAFC")) //设置选中的颜色、未选中的颜色
                .addTabItem("工作", R.mipmap.tabbar_work_selected, R.mipmap.tabbar_work_normal, WorkFragment.class)  // 设置文字、两张图片、fragment
                .addTabItem("消息", R.mipmap.tabbar_message_selected, R.mipmap.tabbar_message_normal, MessagesFragment.class)  // 设置文字、两张图片、fragment
                .addTabItem("联系人", R.mipmap.tabbar_contact_selected, R.mipmap.tabbar_contact_normal, ContactsFragment.class)  // 设置文字、两张图片、fragment
                .addTabItem("我", R.mipmap.tabbar_me_selected, R.mipmap.tabbar_me_normal, MyFragment.class)  // 设置文字、两张图片、fragment
                .isShowDivider(true)                                                            // 设置是否显示分割线
                .setDividerHeight(1)
                .setDividerColor(Color.parseColor("#373737")) //设置分割线颜色
                .setTabBarBackgroundColor(Color.parseColor("#ffffff")) //设置底部导航栏颜色
//                .setTabBarBackgroundResource(R.mipmap.ic_launcher)                               // 设置底部导航栏的背景图片【与设置底部导航栏颜色方法不能同时使用，否则会覆盖掉前边设置的颜色】
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    // 添加选项卡切换监听
                    @Override
                    public void onTabChange(int position, String name, View view) {
                        //这里不用说，你们也都看的懂了
                        //暂时就返回了这俩参数，如果还有什么用的比较多的参数，欢迎留言告诉我，我继续添加上
                        Log.i("TGA", "位置：" + position + "      选项卡的文字内容：" + name);
                    }
                })
                .setCurrentTab(0);                                                              // 设置当前选中的Tab，从0开始

    }


    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showToastWithMessage("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}


