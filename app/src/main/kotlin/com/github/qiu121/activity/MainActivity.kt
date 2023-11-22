package com.github.qiu121.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.qiu121.R
import com.github.qiu121.entity.User

/**
 * @author [qiu121](mailto:qiu0089@foxmail.com)
 * @version 1.0
 * @date 2023/11/21
 * 主界面
 */
class MainActivity : AppCompatActivity() {
    private var usersList: List<User>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        // 接收来自RegisterActivity的注册用户列表
        val intent = intent
        usersList = intent.getSerializableExtra("usersList") as? List<User>

        loginButton.setOnClickListener {
            // 获取用户输入
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 检查用户名和密码是否为空
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                // 弹出提示
                Toast.makeText(this@MainActivity, "用户名和密码不能为空", Toast.LENGTH_SHORT).show()
                // 记录日志
                Log.d("MainActivity", "用户名或密码为空")
            } else {
                // 验证用户名和密码
                if (hasUser(username, password)) {
                    // 登录成功，跳转到欢迎页面
                    val welcomeIntent = Intent(this@MainActivity, WelcomeActivity::class.java)
                    for (user in usersList!!) {
                        if (user.username == username) {
                            welcomeIntent.putExtra("user", user)
                            break
                        }
                    }
                    startActivity(welcomeIntent)
                } else {
                    // 登录失败提示
                    Toast.makeText(
                        this@MainActivity,
                        "登录失败，请检查用户名和密码",
                        Toast.LENGTH_SHORT
                    ).show()
                    // 记录日志
                    Log.d("MainActivity", "登录失败，用户名或密码不匹配")
                }
            }
        }

        // 获取注册按钮并设置点击监听器
        val registerButton = findViewById<Button>(R.id.buttonRegister)
        registerButton.setOnClickListener {
            // 点击注册按钮时跳转到注册页面，并传递注册用户列表
            val registerIntent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }

    // 验证用户名和密码的方法
    private fun hasUser(username: String, password: String): Boolean {
        if (usersList != null) {
            for (user in usersList!!) {
                // 只验证用户名和密码，其他字段不校验
                if (user.username == username && user.password == password) {
                    return true
                }
            }
        }
        return false
    }
}