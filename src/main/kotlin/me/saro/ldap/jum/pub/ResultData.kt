package me.saro.ldap.jum.pub

class ResultData<T> {
    val code: String
    val msg: String?
    val data: T?

    constructor(code: String, msg: String? = null, data: T? = null) {
        this.code = code
        this.msg = msg
        this.data = data
    }
}