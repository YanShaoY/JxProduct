package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vdin.JxProduct.Activity.HomeActivity;
import com.vdin.JxProduct.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页视图
 *
 * @author YanSY
 * @create 2018/9/11
 */
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    // 视图预览
    private ViewPager viewPager;
    //图片资源的数组
    private int[] imageIdArray;
    //图片资源的集合
    private List<View> viewList;
    //放置圆点
    private ViewGroup viewGroup;
    //实例化原点View
    private ImageView[] ivPointArray;

    //最后一页的按钮
    private Button startButton;
    private LinearLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);

        Intent intent = getIntent();
        imageIdArray = intent.getIntArrayExtra("imageIdArray");

        startButton = (Button) findViewById(R.id.guide_ib_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
        });

        //加载ViewPager
        initViewPager();

        //加载底部圆点
        initPoint();
    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        //实例化图片资源
//        imageIdArray = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        viewPager.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        viewPager.addOnPageChangeListener(this);
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {

        viewGroup = (ViewGroup) findViewById(R.id.guide_ll_point);
        ivPointArray = new ImageView[viewList.size()];

        for (int i = 0; i < viewList.size(); i++) {

            ImageView dot = new ImageView(this);

            layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.setMargins(0, 0, 16, 0);
            dot.setLayoutParams(layoutParams);
            dot.setClickable(true);
            dot.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ivPointArray[i] = dot;

            if (i == 0) {
                dot.setBackgroundResource(R.drawable.ic_rect_dot);
            } else {
                dot.setBackgroundResource(R.drawable.ic_circle_dot);
            }

            //将数组中的ImageView加入到ViewGroup
            viewGroup.addView(ivPointArray[i]);
        }
    }

    // 当前页面被滑动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 滑动后的监听
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0; i < length; i++) {
            ivPointArray[position].setBackgroundResource(R.drawable.ic_rect_dot);
            if (position != i) {
                ivPointArray[i].setBackgroundResource(R.drawable.ic_circle_dot);
            }
        }

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1) {
            startButton.setVisibility(View.VISIBLE);
        } else {
            startButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
