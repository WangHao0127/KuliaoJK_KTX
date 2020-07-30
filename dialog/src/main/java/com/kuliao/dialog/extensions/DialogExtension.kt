package com.kuliao.dialog.extensions

import android.content.DialogInterface
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import com.kuliao.dialog.CommonDialog
import com.kuliao.dialog.listener.DataConvertListener
import com.kuliao.dialog.listener.DialogShowOrDismissListener
import com.kuliao.dialog.listener.OnKeyListener
import com.kuliao.dialog.listener.ViewConvertListener
import com.kuliao.dialog.other.DialogOptions
import com.kuliao.dialog.other.ViewHolder

/**
 * 创建一个dialog
 * 你也可以继承CommonDialog，实现更多功能，并通过扩展函数来简化创建过程
 */
inline fun createDialog(options: DialogOptions.(dialog: CommonDialog) -> Unit): CommonDialog {
    val dialog = CommonDialog()
    dialog.getDialogOptions().options(dialog)
    return dialog
}

/**
 * 当需要通过继承CommonDialog来分离Activity与dialog的代码时，
 * 可通过该扩展方法在子类中创建DialogOptions
 */
inline fun CommonDialog.dialogOptionsFun(dialogOp: DialogOptions.() -> Unit): DialogOptions {
    val options = DialogOptions()
    options.dialogOp()
    setDialogOptions(options)
    return options
}

/**
 * 设置convertListener的扩展方法
 */
inline fun DialogOptions.convertListenerFun(crossinline listener: (holder: ViewHolder, dialog: CommonDialog) -> Unit) {
    val viewConvertListener = object : ViewConvertListener() {
        override fun convertView(holder: ViewHolder, dialog: CommonDialog) {
            listener.invoke(holder, dialog)
        }
    }
    convertListener = viewConvertListener
}

/**
 * 设置dataListener的扩展方法
 */
inline fun DialogOptions.dataConvertListenerFun(crossinline listener: (dialogBinding: Any, dialog: CommonDialog) -> Unit) {
    val dataBindingConvertListener = object : DataConvertListener() {
        override fun convertView(dialogBinding: Any, dialog: CommonDialog) {
            listener.invoke(dialogBinding, dialog)
        }
    }
    dataConvertListener = dataBindingConvertListener
}

/**
 * 设置dataBindingListener的扩展方法
 */
inline fun DialogOptions.bindingListenerFun(crossinline listener: (container: ViewGroup?, dialog: CommonDialog) -> View) {
    val newBindingListener = { container: ViewGroup?, dialog: CommonDialog ->
        listener.invoke(container, dialog)
    }
    bindingListener = newBindingListener
}


/**
 * 添加DialogShowOrDismissListener的扩展方法
 */
inline fun DialogOptions.addShowDismissListener(key: String, dialogInterface: DialogShowOrDismissListener.() -> Unit): DialogOptions {
    val dialogShowOrDismissListener = DialogShowOrDismissListener()
    dialogShowOrDismissListener.dialogInterface()
    showDismissMap[key] = dialogShowOrDismissListener
    return this
}

/**
 * 设置OnKeyListener的扩展方法
 */
inline fun DialogOptions.onKeyListenerForOptions(crossinline listener: (keyCode: Int, event: KeyEvent) -> Boolean) {
    val onKey = object : OnKeyListener() {
        override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
            return listener.invoke(keyCode, event)
        }
    }
    onKeyListener = onKey
}

/**
 * 针对特殊动画需要调用genjidialog.dismiss()的方法
 */
inline fun CommonDialog.onKeyListenerForDialog(crossinline listener: (genjidialog: CommonDialog, dialogInterFace: DialogInterface, keyCode: Int, event: KeyEvent) -> Boolean): CommonDialog {
    val onKey = object : OnKeyListener() {
        override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
            return listener.invoke(this@onKeyListenerForDialog, dialog, keyCode, event)
        }
    }
    getDialogOptions().onKeyListener = onKey
    return this
}

