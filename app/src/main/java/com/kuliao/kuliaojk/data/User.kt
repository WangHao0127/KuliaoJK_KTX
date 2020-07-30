package com.kuliao.kuliaojk.data

import android.util.Base64
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuliao.kuliaojk.config.Constant.DES3_DECODE_IV
import com.kuliao.kuliaojk.config.Constant.DES3_DECODE_SECRET_KEY
import java.io.Serializable
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec
import javax.crypto.spec.IvParameterSpec

/**
 * Author: WangHao
 * Created On: 2020/06/24  16:08
 * Description:
 */
@Entity
data class User(
    @PrimaryKey
    val userId: String,

    val userName: String?,

    val zgha: String?,

    val headPhoto: String?,

    val nickName: String?

) : Serializable {
    val authToken: String?
        get() = zgha?.let { decrypt(DES3_DECODE_SECRET_KEY, DES3_DECODE_IV, it) }

}

fun decrypt(secretKey: String, iv: String, encryptText: String): String {
    val spec = DESedeKeySpec(secretKey.toByteArray())
    val keyFactory = SecretKeyFactory.getInstance("desede")
    val desKey = keyFactory.generateSecret(spec)
    val cipher =
        Cipher.getInstance("desede/CBC/PKCS5Padding")
    val ips = IvParameterSpec(iv.toByteArray())
    cipher.init(Cipher.DECRYPT_MODE, desKey, ips)

    val decryptData =
        cipher.doFinal(Base64.decode(encryptText, Base64.NO_WRAP))

    return String(decryptData, Charset.forName("utf-8"))
}