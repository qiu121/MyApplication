package com.github.qiu121.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.qiu121.R;
import com.github.qiu121.entity.User;

import java.util.List;

/**
 * @author <a href="mailto:qiu0089@foxmail.com">qiu121</a>
 * @version 1.0
 * @date 2023/11/21
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private List<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);

        // 接收来自RegisterActivity的注册用户列表
        Intent intent = getIntent();
        usersList = (List<User>) intent.getSerializableExtra("usersList");

        loginButton.setOnClickListener(v -> {
            // 获取用户输入
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // 检查用户名和密码是否为空
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                // 弹出提示
                Toast.makeText(MainActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                // 记录日志
                Log.d("MainActivity", "用户名或密码为空");
            } else {
                // 验证用户名和密码
                if (hasUser(username, password)) {
                    // 登录成功，跳转到欢迎页面
                    Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                    for (User user : usersList) {
                        if (user.getUsername().equals(username)) {
                            welcomeIntent.putExtra("user", user);
                            break;
                        }
                    }
                    startActivity(welcomeIntent);
                } else {
                    // 登录失败提示
                    Toast.makeText(MainActivity.this, "登录失败，请检查用户名和密码", Toast.LENGTH_SHORT).show();
                    // 记录日志
                    Log.d("MainActivity", "登录失败，用户名或密码不匹配");
                }
            }
        });

        // 获取注册按钮并设置点击监听器
        Button registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(v -> {
            // 点击注册按钮时跳转到注册页面，并传递注册用户列表
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }

    // 验证用户名和密码的方法
    private boolean hasUser(String username, String password) {
        if (usersList != null) {
            for (User user : usersList) {
                // 只验证用户名和密码，其他字段不校验
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}
