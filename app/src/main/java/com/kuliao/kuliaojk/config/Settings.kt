package com.kuliao.kuliaojk.config

import com.kuliao.kuliaojk.storage.Preference

object Settings {

    object Account {
        var token:String? by Preference(Constant.AUTH_TOKEN, "")
        var userId:String? by Preference(Constant.USER_ID, "")
        var nickname:String? by Preference(Constant.NICK_NAME, "")
        var password:String? by Preference(Constant.USER_PASSWORD, "")
        var zgha:String? by Preference(Constant.USER_ZGHA, "")
        var head_photo:String? by Preference(Constant.HEAD_PHOTO, "")
    }
}