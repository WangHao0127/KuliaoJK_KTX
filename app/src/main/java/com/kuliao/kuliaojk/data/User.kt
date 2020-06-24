package com.kuliao.kuliaojk.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Author: WangHao
 * Created On: 2020/06/24  16:08
 * Description:
 */
@Entity
data class User(
    @PrimaryKey
    val userId: String,

    val userName: String,

    val zgha: String,

    val headPhoto: String,

    val nickName: String,

    val authToken: String
):Serializable