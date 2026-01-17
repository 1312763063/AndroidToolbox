package com.xh.androidtoolbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import com.xh.androidtoolbox.core.UIUtil;
import com.xh.androidtoolbox.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. 搜索栏 → 展开
        binding.searchBar.setOnClickListener(v -> binding.searchView.show());

        // 2. 返回键自动收起
        binding.searchView.setupWithSearchBar(binding.searchBar);

        // 3. 搜索回调（先空着）
        binding.searchView
                .getEditText()
                .setOnEditorActionListener((v, actionId, event) -> {
                    binding.searchView.hide();          // 收起
                    // TODO: 实际过滤逻辑
                    return true;
                });

        UIUtil.updateAdbStatusText(binding.tvSubtitle);

        binding.btnAppsList.setOnClickListener(v ->
                startActivity(new Intent(this, AppListActivity.class)));

        binding.btnApkManager.setOnClickListener(v ->
                startActivity(new Intent(this, PkgManagerActivity.class)));

        binding.btnMore.setOnClickListener(v ->
                startActivity(new Intent(this, MoreActivity.class)));

        binding.switchScreen.setOnCheckedChangeListener((b, checked) -> {
            // TODO 截屏录屏
        });
        binding.switchFloat.setOnCheckedChangeListener((b, checked) -> {
            // TODO 全局悬浮
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_check_update) {
            // TODO 检查更新
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}