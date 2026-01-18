package com.xh.androidtoolbox.feature.main;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.xh.androidtoolbox.core.AdbStatusHelper;

/**
 * Presenter 只关心数据，不关心 View 的具体实现
 * 方便以后单元测试：new MainPresenter(mockView, mockRepo)
 */
public class MainPresenter implements MainContract.Presenter {

    // 主线程 Handler，用于定时轮询 adb 状态
    private final Handler handler = new Handler(Looper.getMainLooper());
    // 轮询任务
    private final Runnable task;
    // 全局 ApplicationContext，避免持有 Activity 导致内存泄漏
    private final Context appCtx;

    public MainPresenter(MainContract.View view, Context context) {
        // 把 Activity 的 Context 换成 ApplicationContext，就能让 Runnable 只持有“整个应用”而不再持有“某个 Activity”，
        // 屏幕旋转时旧的 Activity 可以被正常回收，就不会泄漏。https://ai.dangbei.com/share/UIKhTg6VEd
        appCtx = context.getApplicationContext();
        // 创建轮询任务：每 500 ms 检查一次 adb 连接状态并刷新 UI
        this.task = new Runnable() {
            @Override
            public void run() {
                boolean connected = AdbStatusHelper.isAdbConnected(appCtx);
                view.showAdbStatus(connected);  // 回调 View 层刷新界面
                handler.postDelayed(this, 500);
            }
        };
    }

    @Override
    public void start() {
        handler.post(task);
    }

    @Override
    public void stop() {
        handler.removeCallbacks(task);
    }
}