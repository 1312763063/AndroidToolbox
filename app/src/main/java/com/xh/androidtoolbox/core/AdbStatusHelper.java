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

public class AdbStatusHelper {
    // TODO 后续通过监听 adb tcp 5551 / usb 等方式返回真实状态
    public static boolean isAdbConnected() {
        return false;
    }
}