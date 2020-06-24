package com.kuliao.baselib.valid

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

/**
 * Author: WangHao
 * Created On: 2020/06/24  11:30
 * Description:
 */
@Retention(RetentionPolicy.RUNTIME)
annotation class Interceptor(vararg val value: KClass<out Valid> = [])