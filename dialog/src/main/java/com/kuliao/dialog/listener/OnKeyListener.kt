package com.kuliao.dialog.listener

import android.content.DialogInterface
import android.os.Parcel
import android.os.Parcelable
import android.view.KeyEvent

/**
 * Author: WangHao
 * Created On: 2020/07/29  14:40
 * Description:
 */
abstract class OnKeyListener : DialogInterface.OnKeyListener, Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }

    constructor() {}

    protected constructor(source: Parcel) {}

    companion object CREATOR : Parcelable.Creator<OnKeyListener> {
        override fun createFromParcel(parcel: Parcel): OnKeyListener {
            return object : OnKeyListener(parcel) {
                override fun onKey(
                    dialog: DialogInterface?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    return false
                }

            }
        }

        override fun newArray(size: Int): Array<OnKeyListener?> {
            return arrayOfNulls(size)
        }
    }
}