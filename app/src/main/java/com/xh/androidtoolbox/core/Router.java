package com.xh.androidtoolbox.core;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.xh.androidtoolbox.feature.applist.AppListActivity;
import com.xh.androidtoolbox.feature.pkgmanager.PkgManagerActivity;
import com.xh.androidtoolbox.feature.more.MoreActivity;

/**
 * 全局路由表
 * 职责：
 * 1. 集中管理所有页面路径，杜绝 Intent 字符串硬编码散落在业务层
 * 2. 统一入口，方便后续扩展：参数自动注入、共享元素动画、拦截器、登录校验等
 * 使用方式：
 * Router.navigate(this, Router.PATH_APP_LIST);
 */
public final class Router {

    /* ====== 路径常量 ====== */
    public static final String PATH_APP_LIST    = "applist";
    public static final String PATH_PKG_MANAGER = "pkgmanager";
    public static final String PATH_MORE        = "more";

    /* ====== 私有构造器，禁止实例化 https://ai.dangbei.com/share/nTMFHB273T ====== */
    private Router() {}

    /**
     * 根据路径跳转到对应页面
     *
     * @param from 当前 Activity
     * @param path 目标页面对应的 Router 常量
     */
    public static void navigate(@NonNull Activity from, @NonNull String path) {
        Intent intent = null;
        switch (path) {
            case PATH_APP_LIST:
                intent = new Intent(from, AppListActivity.class);
                break;
            case PATH_PKG_MANAGER:
                intent = new Intent(from, PkgManagerActivity.class);
                break;
            case PATH_MORE:
                intent = new Intent(from, MoreActivity.class);
                break;
        }
        if (intent != null) from.startActivity(intent);
    }
}