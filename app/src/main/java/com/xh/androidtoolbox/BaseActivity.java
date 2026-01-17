package com.xh.androidtoolbox;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (v, insets) -> {
            // 1. 取出系统栏 Insets
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // 2. 给根 View 设置 padding
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            // 3. 一定要返回原始 insets，否则内部分发会断
            return insets;
        });
    }
}