package com.xh.androidtoolbox.constant;

/**
 * 全局常量池
 * 职责：
 * 1. 集中存放所有跨模块使用的字符串、数字常量，彻底消灭“魔法值”
 * 2. 统一入口，方便后续批量修改或做多语言/多 flavor 适配
 * 3. 私有构造器禁止实例化，纯工具类
 */
public final class AppConstants {
    private AppConstants() {}

    public static final String SP_NAME = "settings"; // SharedPreferences 文件名
    public static final String KEY_FLOAT_WINDOW = "float_window";
    public static final String KEY_SCREENSHOT_TILE = "screenshot_tile";
}