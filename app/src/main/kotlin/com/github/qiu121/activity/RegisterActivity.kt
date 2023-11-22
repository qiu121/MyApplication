package com.github.qiu121.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.qiu121.R
import com.github.qiu121.entity.User

/**
 * @author [qiu121](mailto:qiu0089@foxmail.com)
 * @version 1.0
 * @date 2023/11/21
 */
class RegisterActivity : AppCompatActivity() {
    private val usersList: MutableList<User> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 获取控件
        val usernameEditText = findViewById<EditText>(R.id.editTextRegisterUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextRegisterPassword)
        val spinnerCollege = findViewById<Spinner>(R.id.spinnerCollege)
        val majorEditText = findViewById<EditText>(R.id.editTextMajor)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val registerButton = findViewById<Button>(R.id.buttonDoRegister)

        // 设置学院下拉框
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.college_array, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCollege.adapter = adapter

        // 点击注册按钮时处理逻辑
        registerButton.setOnClickListener {
            // 获取用户输入
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val selectedCollege = spinnerCollege.selectedItem.toString()
            val major = majorEditText.text.toString()
            val email = emailEditText.text.toString()

            // 获取性别
            var gender = ""
            val selectedGenderId = radioGroupGender.checkedRadioButtonId
            if (selectedGenderId != -1) {
                val selectedGender = findViewById<RadioButton>(selectedGenderId)
                gender = selectedGender.text.toString()
            }

            // 检查输入是否为空
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(selectedCollege) || TextUtils.isEmpty(major) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(gender)
            ) {
                // 弹出提示
                Toast.makeText(this@RegisterActivity, "所有字段都必须填写", Toast.LENGTH_SHORT)
                    .show()
            } else if (selectedCollege == getString(R.string.choose_college)) {
                // 学院下拉框未选择
                Toast.makeText(this@RegisterActivity, "请选择学院", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // 邮箱格式不正确
                Toast.makeText(this@RegisterActivity, "邮箱格式有误", Toast.LENGTH_SHORT).show()
            } else {
                // 输入合法，进行注册
                val newUser = User(username, password, gender, email, selectedCollege, major)
                usersList.add(newUser)
                Log.d("RegisterActivity", "注册完成")

                // 提示注册成功，倒计时3秒后跳转到MainActivity
                Toast.makeText(this@RegisterActivity, "注册成功，即将前往登录", Toast.LENGTH_SHORT)
                    .show()

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.putExtra("usersList", usersList as ArrayList<User>)
                    startActivity(intent)
                    finish()
                }, 3000)
            }
        }
    }
}