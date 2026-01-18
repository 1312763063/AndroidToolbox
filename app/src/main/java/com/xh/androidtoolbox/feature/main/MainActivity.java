package com.xh.androidtoolbox.feature.main;

import android.os.Bundle;

import com.xh.androidtoolbox.core.BaseActivity;
import com.xh.androidtoolbox.core.Router;
import com.xh.androidtoolbox.core.UIUtil;
import com.xh.androidtoolbox.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements MainContract.View {

    private ActivityMainBinding binding;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. 初始化 Presenter
        presenter = new MainPresenter(this, getApplicationContext());

        // 2. 按钮绑定
        binding.btnAppsList.setOnClickListener(v -> gotoAppList());
        binding.btnApkManager.setOnClickListener(v -> gotoPkgManager());
        binding.btnMore.setOnClickListener(v -> gotoMore());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();   // 开始轮询
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stop();    // 停止轮询，防泄漏
    }

    /* ---------- View 接口实现 ---------- */
    @Override
    public void showAdbStatus(boolean connected) {
        UIUtil.updateAdbStatusText(binding.tvSubtitle, connected);
    }

    @Override
    public void gotoAppList() {
        Router.navigate(this, Router.PATH_APP_LIST);
    }

    @Override
    public void gotoPkgManager() {
        Router.navigate(this, Router.PATH_PKG_MANAGER);
    }

    @Override
    public void gotoMore() {
        Router.navigate(this, Router.PATH_MORE);
    }
}