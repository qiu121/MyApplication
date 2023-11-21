package com.github.qiu121.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.qiu121.R;
import com.github.qiu121.entity.User;

import java.util.ArrayList;
import java.util.List;
/**
 * @author <a href="mailto:qiu0089@foxmail.com">qiu121</a>
 * @version 1.0
 * @date 2023/11/21
 */
public class RegisterActivity extends AppCompatActivity {

    private List<User> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 获取控件
        EditText usernameEditText = findViewById(R.id.editTextRegisterUsername);
        EditText passwordEditText = findViewById(R.id.editTextRegisterPassword);
        Spinner spinnerCollege = findViewById(R.id.spinnerCollege);
        EditText majorEditText = findViewById(R.id.editTextMajor);
        EditText emailEditText = findViewById(R.id.editTextEmail);
        RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
        Button registerButton = findViewById(R.id.buttonDoRegister);

        // 设置学院下拉框
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.college_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCollege.setAdapter(adapter);

        // 点击注册按钮时处理逻辑
        registerButton.setOnClickListener(v -> {
            // 获取用户输入
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String selectedCollege = spinnerCollege.getSelectedItem().toString();
            String major = majorEditText.getText().toString();
            String email = emailEditText.getText().toString();

            // 获取性别
            String gender = "";
            int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
            if (selectedGenderId != -1) {
                RadioButton selectedGender = findViewById(selectedGenderId);
                gender = selectedGender.getText().toString();
            }

            // 检查输入是否为空
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(selectedCollege) || TextUtils.isEmpty(major) ||
                    TextUtils.isEmpty(email) || TextUtils.isEmpty(gender)) {
                // 弹出提示
                Toast.makeText(RegisterActivity.this, "所有字段都必须填写", Toast.LENGTH_SHORT).show();
            } else if (selectedCollege.equals(getString(R.string.choose_college))) {
                // 学院下拉框未选择
                Toast.makeText(RegisterActivity.this, "请选择学院", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // 邮箱格式不正确
                Toast.makeText(RegisterActivity.this, "邮箱格式有误", Toast.LENGTH_SHORT).show();
            } else {
                // 输入合法，进行注册
                User newUser = new User(username, password, selectedCollege, major, email, gender);
                usersList.add(newUser);
                Log.d("RegisterActivity", "注册完成");

                // 提示注册成功，倒计时3秒后跳转到MainActivity
                Toast.makeText(RegisterActivity.this, "注册成功，即将前往登录", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("usersList", (ArrayList<User>) usersList);
                    startActivity(intent);
                    finish();
                }, 3000);
            }
        });
    }
}
