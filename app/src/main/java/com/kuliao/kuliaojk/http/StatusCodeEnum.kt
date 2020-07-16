package com.kuliao.kuliaojk.http

/**
 * Author: WangHao
 * Created On: 2020/07/15  9:44
 * Description:
 */
enum class StatusCodeEnum(val code: Int) {
    SUCCESSFUL(1),
    NO_DATA(-1),
    INVALID_TOKEN(-101),
    SERVER_EXCEPTION(-102),
    INVALID_TIMESTAMP(-103);

    fun valueOf(code: Int): StatusCodeEnum {
        for (statusCode in values()) {
            if (statusCode.code == code) {
                return statusCode
            }
        }
        return SERVER_EXCEPTION
    }


    fun isTokenInvalid(): Boolean = this == INVALID_TOKEN

    fun isTimeInvalid(): Boolean = this == INVALID_TIMESTAMP
}