package com.kuliao.kuliaojk.valid

import android.content.Context
import com.kuliao.baselib.ext.go
import com.kuliao.baselib.valid.Valid
import com.kuliao.kuliaojk.config.Settings
import com.kuliao.kuliaojk.ui.login.LoginActivity

/**
 * Author: WangHao
 * Created On: 2020/06/24  14:21
 * Description:
 */
class LoginValid(val context: Context) : Valid {

    override fun check(): Boolean {
        return !(Settings.Account.nickname.isNullOrBlank())
    }

    override fun doValid() {
        go<LoginActivity>(context)
    }

}