package com.kuliao.dialog.listener

import android.os.Parcel
import android.os.Parcelable

/**
 * Author: WangHao
 * Created On: 2020/07/29  11:18
 * Description:
 */
class DialogShowOrDismissListener : Parcelable {

    //是否在dialog显示的时候执行onDialogShow()方法
    var enableExecuteShowListener = true

    //是否在dialog关闭的时候执行onDialogDismiss()方法
    var enableExecuteDismissListener = true

    private var dialogShowFun: (() -> Unit)? = null

    private var dialogDismissFun: (() -> Unit)? = null

    fun onDialogShow(listener: () -> Unit) {
        dialogShowFun = listener
    }

    fun onDialogDismiss(listener: () -> Unit) {
        dialogDismissFun = listener
    }

    fun onAddDialogShow(listener: () -> Unit) {
        val oldListener = dialogShowFun
        dialogShowFun = {
            oldListener?.invoke()
            listener.invoke()
        }
    }

    fun onAddDialogDismiss(listener: () -> Unit) {
        val oldListener = dialogDismissFun
        dialogDismissFun = {
            oldListener?.invoke()
            listener.invoke()
        }
    }

    fun onDialogShow() {
        dialogShowFun?.invoke()
    }

    fun onDialogDismiss() {
        dialogDismissFun?.invoke()
    }

    constructor()

    constructor(source: Parcel) : this(
    ) {
        enableExecuteShowListener = source.readByte() != 0.toByte()
        enableExecuteDismissListener = source.readByte() != 0.toByte()
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object CREATOR : Parcelable.Creator<DialogShowOrDismissListener> {
        override fun createFromParcel(parcel: Parcel): DialogShowOrDismissListener {
            return DialogShowOrDismissListener(parcel)
        }

        override fun newArray(size: Int): Array<DialogShowOrDismissListener?> {
            return arrayOfNulls(size)
        }
    }


}