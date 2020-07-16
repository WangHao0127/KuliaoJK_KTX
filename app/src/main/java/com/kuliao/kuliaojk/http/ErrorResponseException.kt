package com.kuliao.kuliaojk.http

import java.lang.RuntimeException

/**
 * Author: WangHao
 * Created On: 2020/07/15  10:38
 * Description:
 */
class ErrorResponseException(response: BaseResponse,errorInfo:String):RuntimeException()