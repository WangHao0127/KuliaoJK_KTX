package com.kuliao.kuliaojk.net

import com.blankj.utilcode.util.EncryptUtils
import com.google.common.base.Charsets
import com.google.common.hash.Hashing
import com.kuliao.kuliaojk.config.Settings

/**
 * Author: WangHao
 * Created On: 2020/06/24  16:37
 * Description:
 */
fun getParamsWithToken(apiPath: String): MutableMap<String, Any> {
    val time = System.currentTimeMillis()
    val params: MutableMap<String, Any> = LinkedHashMap()
    params["rts"] = time

    val userId = Settings.Account.userId
    val token = Settings.Account.token

    userId?.let {
        params["userId"] = userId

        token?.let {
            params["zgha"] = generateEncryptedRequestToken(userId, token, "/$apiPath", time)
        }
    }

    return params
}


fun generateEncryptedRequestToken(
    userId: String,
    tokenUUID: String,
    urlPath: String,
    timestamp: Long
): String {

    val encode = with(StringBuilder()) {
        append(userId)
        append("m7UC5a6w")
        append(tokenUUID)
        append("v6B8Z5j2")
        append(urlPath)
        append(timestamp)
    }
    return Hashing.md5().newHasher().putString(encode, Charsets.UTF_8).hash().toString()
}

object APIPath {

    object Basic {
        const val LOGIN = "app/user/login"
        const val LOGOUT = "app/user/loginOut"
    }


    object Account {
        const val GET_BACK_LOGIN_PASSWORD = "Account/GetbackLoginPwd"
    }
}