package com.xh.androidtoolbox.core;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xh.androidtoolbox.R;

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
 */
public final class UIUtil {

    public static void updateAdbStatusText(TextView tv, boolean connected) {

        Context ctx = tv.getContext();
        String prefix  = ctx.getString(R.string.main_adb_prefix);
        String suffix  = ctx.getString(connected ? R.string.main_adb_connected
                : R.string.main_adb_disconnected);
        SpannableString full = new SpannableString(prefix + suffix);

        // 颜色作用范围：从 prefix 长度到结尾
        int color = ContextCompat.getColor(ctx, connected ? R.color.adb_connected
                : R.color.adb_disconnected);
        full.setSpan(new ForegroundColorSpan(color),
                prefix.length(), full.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setText(full);
    }

}