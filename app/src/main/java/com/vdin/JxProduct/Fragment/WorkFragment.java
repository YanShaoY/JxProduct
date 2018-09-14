package com.vdin.JxProduct.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vdin.JxProduct.Activity.IdCardReadActivity;
import com.vdin.JxProduct.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkFragment extends BaseFragment {
    /**
     * 定义属性变量
     */
    @BindView(R.id.lv_history)
    LinearLayout lvHistory;
    @BindView(R.id.lv_register)
    LinearLayout lvRegister;
    Unbinder unbinder;

    /**
     * 默认初始化方法
     */
    public WorkFragment() {
        // Required empty public constructor
    }

    /**
     * 碎片创建时调用的方法
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 当Activity创建后调用
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 可见状态变化是调用
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * 注销时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 历史业务点击事件
     */
    @OnClick(R.id.lv_history)
    public void onLvHistoryClicked() {

    }

    /**
     * 业务登记点击事件
     */
    @OnClick(R.id.lv_register)
    public void onLvRegisterClicked() {

        Intent intent = new Intent(getActivity(), IdCardReadActivity.class);
        intent.putExtra("newxtid","下一步");

        getActivity().startActivity(intent);


    }
}














