package com.vdin.JxProduct.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vdin.JxProduct.API.MetaDataApiRequest;
import com.vdin.JxProduct.Activity.BaseActivity;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.ActivityConllector;
import com.vdin.JxProduct.Util.HttpUtil;
import com.vdin.JxProduct.Util.LaunchUtil;
import com.vdin.JxProduct.View.GAConfirmDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    /**
     * 定义属性变量
     */
    @BindView(R.id.btn_logout)
    Button btnLogout;
    Unbinder unbinder;

    /**
     * 默认初始化方法
     */
    public MyFragment() {
        // Required empty public constructor
    }

    /**
     * 碎片创建时调用的方法
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
     * 退出登录按钮点击响应
     */
    @OnClick(R.id.btn_logout)
    public void onViewClicked() {

        // 提示弹窗
        GAConfirmDialog dialog = new GAConfirmDialog(getActivity(), GAConfirmDialog.DialogStyle.DEFAULT);
        dialog.setupTitle("确定退出?", "#ff6464")
                .tip("退出后切换状态为离线")
                .isShowSingleButton(false)
                .bgIcon(R.mipmap.ask)
                .sure(v -> {
                    loginOut();
                })
                .show();
    }

    /**
     * 退出登录
     */
    private void loginOut() {

        BaseActivity activity = (BaseActivity) getActivity();
        activity.showProgressDialog("正在退出登录");

        // 判断网络状态
        if (!HttpUtil.isNetworkConnected(activity)) {
            activity.closeProgressDialog();
            activity.showToastWithMessage("退出成功");
            ActivityConllector.GoToLoginActivity();
            return;
        }

        MetaDataApiRequest.logout((isSuccess, object) -> {

            // 存储用户登录数据
            LaunchUtil.removeLoginFlag(activity);
            LaunchUtil.removeLoginusername(activity);
            LaunchUtil.removePassword(activity);

            activity.closeProgressDialog();
            activity.showToastWithMessage("退出成功");
            ActivityConllector.GoToLoginActivity();
        });


    }

}
















