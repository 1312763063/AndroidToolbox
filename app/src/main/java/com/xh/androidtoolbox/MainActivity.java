package com.xh.androidtoolbox;

import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import com.xh.androidtoolbox.core.AdbStatusHelper;
import com.xh.androidtoolbox.core.UIUtil;
import com.xh.androidtoolbox.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    private android.content.BroadcastReceiver usbReceiver;

    private ActivityMainBinding binding;

    /* 主线程 Handler：保证 UI 更新发生在主线程 */
    private final Handler adbHandler = new Handler(Looper.getMainLooper());
    /**
     * 轮询任务（Runnable）
     * 特点：
     * ① 自我递归：每次执行完再 postDelayed 0.5 秒；
     * ② 只持有 Handler 引用，不直接持有 Activity；
     * ③ 随 onDestroy() 移除，生命周期安全。
     */
    private final Runnable adbPoller = new Runnable() {
        @Override
        public void run() {
            // 1. 子线程做网络检测
            new Thread(() -> {
                final boolean connected = AdbStatusHelper.isAdbConnected(getApplicationContext());
                // 2. 检测完回到主线程刷新 UI
                adbHandler.post(() -> {
                    UIUtil.updateAdbStatusText(binding.tvSubtitle, connected);
                    // 3. 继续下一次循环
                    adbHandler.postDelayed(adbPoller, 500);
                });
            }).start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 创建 USB 连接状态广播接收器
        usbReceiver = AdbStatusHelper.createUsbReceiver();
        // 注册广播接收器
        IntentFilter filter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        registerReceiver(usbReceiver, filter);

        // 启动轮询，立即执行一次，避免空等 1 秒
        adbPoller.run();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 页面销毁时移除回调，防止 Handler 持有匿名类导致内存泄漏
        adbHandler.removeCallbacks(adbPoller);
        // 注销广播接收器
        unregisterReceiver(usbReceiver);
    }

}