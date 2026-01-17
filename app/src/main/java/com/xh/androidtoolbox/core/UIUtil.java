/**
 *  ┌----------------------┐
 *  │   AdbStatusHelper    │◀─── 纯工具，不碰任何 View
 *  │  isAdbConnected() : Boolean
 *  └----------▲-----------┘
 *             │ 提供数据
 *             │
 *  ┌----------┴-----------┐
 *  │      UIUtil          │◀─── 只负责把数据转成可见字符串
 *  │  updateAdbStatusText(TextView)
 *  └----------------------┘
 */
package com.xh.androidtoolbox.core;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowInsetsController;

import android.widget.TextView;

public final class UIUtil {

    public static void updateAdbStatusText(TextView tv) {
        boolean connected = AdbStatusHelper.isAdbConnected();
        tv.setText(connected ? "ADB 连接状态：已连接" : "ADB 连接状态：未连接");
    }

    private UIUtil() { }
}