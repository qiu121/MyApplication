package com.github.qiu121.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.qiu121.R;
import com.github.qiu121.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="mailto:qiu0089@foxmail.com">qiu121</a>
 * @version 1.0
 * @date 2023/11/21
 */
public class WelcomeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 在界面上显示欢迎信息和当前时间
        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        updateCurrentTime(textViewWelcome);
    }

    // 更新当前时间的方法
    private void updateCurrentTime(final TextView textViewWelcome) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postAtTime(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                // 获取当前时间并格式化为 yyyy-MM-dd HH:mm:ss
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentTime = sdf.format(new Date());

                // 获取传递过来的用户名
                User user = (User) getIntent().getSerializableExtra("user");
                assert user != null;
                Log.d("WelcomeActivity", "Received User - Username" + user.getUsername());

                // 在界面上显示欢迎信息和当前时间
                textViewWelcome.setText("Hello，" + user.getUsername() + "！\n当前时间：" + currentTime);

                // 下一次更新的时间
                long nextUpdateTime = SystemClock.uptimeMillis() + 1000;

                // 指定下一次更新的时间
                handler.postAtTime(this, nextUpdateTime);
            }
        }, SystemClock.uptimeMillis() + 1000); // 1000 毫秒（即 1 秒）后执行第一次
    }
}
