package com.github.qiu121.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.qiu121.R
import com.github.qiu121.entity.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author [qiu121](mailto:qiu0089@foxmail.com)
 * @version 1.0
 * @date 2023/11/21
 */
class WelcomeActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // 在界面上显示欢迎信息和当前时间
        val textViewWelcome = findViewById<TextView>(R.id.textViewWelcome)
        updateCurrentTime(textViewWelcome)
    }

    // 更新当前时间的方法
    private fun updateCurrentTime(textViewWelcome: TextView) {
        val handler = Handler(Looper.getMainLooper())
        handler.postAtTime(object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                // 获取当前时间并格式化为 yyyy-MM-dd HH:mm:ss
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentTime = sdf.format(Date())

                // 获取传递过来的用户名
                val user = (intent.getSerializableExtra("user") as User?)!!
                Log.d("WelcomeActivity", "Received User - Username" + user.username)

                // 在界面上显示欢迎信息和当前时间
                textViewWelcome.text = """
                    Hello，${user.username}！
                    当前时间：$currentTime
                    """.trimIndent()

                // 下一次更新的时间
                val nextUpdateTime = SystemClock.uptimeMillis() + 1000

                // 指定下一次更新的时间
                handler.postAtTime(this, nextUpdateTime)
            }
        }, SystemClock.uptimeMillis() + 1000) // 1000 毫秒（即 1 秒）后执行第一次
    }
}