package com.xh.androidtoolbox.feature.main;

/**
 * Main 页面契约接口
 * 将 View 与 Presenter 的职责以接口形式固化：
 * 1. 方便 MVP 分层，View 与 Presenter 面向接口编程，互不依赖具体实现
 * 2. 后续新增功能只需在对应接口里添加方法即可，无需改动整个类
 */
public interface MainContract {

    /**
     * View 层职责：只负责 UI 展示与页面跳转
     * 由 Activity / Fragment 实现
     */
    interface View {
        void showAdbStatus(boolean connected);  // 刷新 ADB 连接状态
        void gotoAppList();
        void gotoPkgManager();
        void gotoMore();
    }

    /**
     * Presenter 层职责：只负责业务逻辑与数据调度
     * 由 MainPresenter 实现
     */
    interface Presenter {
        void start();   // 开始轮询
        void stop();    // 停止轮询
    }
}