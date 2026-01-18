package com.xh.androidtoolbox.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.BatteryManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

/**
 *  ┌----------------------┐
 *  │   AdbStatusHelper    │◀─── 纯工具，获取adb状态，不碰任何 View
 *  │  isAdbConnected() : Boolean
 *  └----------▲-----------┘
 *             │ 提供数据
 *             │
 *  ┌----------┴-----------┐
 *  │      UIUtil          │◀─── 只负责把数据转成可见字符串
 *  │  updateAdbStatusText(TextView)
 *  └----------------------┘
 * AdbStatusHelper 类用于帮助检查 Android 设备的 ADB（Android Debug Bridge）连接状态。
 * 它提供了一系列静态方法来检查开发者选项、USB 调试是否开启，以及设备是否通过 USB 连接到电脑。
 * 同时，还可以创建一个广播接收器来监听 USB 设备的连接和断开事件。
 */
public final class AdbStatusHelper {
    private static final String TAG = "AdbStatusHelper";

    public static boolean isAdbConnected(Context context) {
        // 检查开发者选项是否开启
        if (!isDeveloperOptionsEnabled(context)) {
            return false;
        }

        // 检查 USB 调试是否开启
        if (!isUsbDebuggingEnabled(context)) {
            return false;
        }

        // 检查设备是否通过 USB 连接到电脑
        return isUsbConnected(context);
    }

    /**
     * 检查开发者选项是否开启
     */
    private static boolean isDeveloperOptionsEnabled(Context context) {
        return Settings.Secure.getInt(
                context.getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) != 0;
    }

    /**
     * 检查 USB 调试是否开启
     */
    private static boolean isUsbDebuggingEnabled(Context context) {
        return Settings.Global.getInt(
                context.getContentResolver(),
                Settings.Global.ADB_ENABLED, 0
        ) != 0;
    }

    /**
     * 检查设备是否通过 USB 连接到电脑
     * @return true 表示当前处于 USB 充电状态；false 表示未连接或获取失败
     * 返回值仅表示“USB 充电”，并不能区分“是否开启 USB 调试”或“是否建立 ADB 连接”
     */
    private static boolean isUsbConnected(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        if (batteryStatus != null) {
            int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            return chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        }
        return false;
    }

    /**
     * 创建 USB 连接状态广播接收器，好像暂时没调用。。
     */
    public static BroadcastReceiver createUsbReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                    boolean connected = Objects.requireNonNull(intent.getExtras()).getBoolean(UsbManager.ACTION_USB_DEVICE_ATTACHED);
                    if (connected) {
                        Toast.makeText(context, "USB 已连接", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "USB 已连接，检查是否开启 USB 调试...");
                        if (isAdbConnected(context)) {
                            Log.d(TAG, "USB 调试已开启");
                        } else {
                            Log.d(TAG, "USB 调试未开启");
                        }
                    } else {
                        Toast.makeText(context, "USB 已断开", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "USB 已断开");
                    }
                }
            }
        };
    }
}