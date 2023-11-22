package com.github.qiu121.entity

import java.io.Serializable

/**
 * @author [qiu121](mailto:qiu0089@foxmail.com)
 * @version 1.0
 * @date 2023/11/21
 */
class User : Serializable {
    var id: Int? = null

    /**
     * 用户名
     */
    var username: String

    /**
     * 密码
     */
    var password: String

    /**
     * 性别
     */
    var gender: String? = null

    /**
     * 电子邮箱
     */
    var email: String? = null

    /**
     * 所在学院
     */
    var college: String? = null

    /**
     * 专业
     */
    var major: String? = null

    constructor(
        username: String,
        password: String,
        gender: String?,
        email: String?,
        college: String?,
        major: String?
    ) {
        this.username = username
        this.password = password
        this.gender = gender
        this.email = email
        this.college = college
        this.major = major
    }

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }

    companion object {
        private const val serialVersionUID = 2228839092249057512L
    }
}