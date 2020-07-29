package com.kuliao.dialog.listener

import android.os.Parcel
import android.os.Parcelable
import com.kuliao.dialog.CommonDialog

/**
 * Author: WangHao
 * Created On: 2020/07/29  14:46
 * Description:
 */
abstract class DataConvertListener : Parcelable {

    abstract fun convertView(dialogBinding: Any, dialog: CommonDialog)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }

    constructor() {}

    protected constructor(source: Parcel) {}

    companion object CREATOR : Parcelable.Creator<DataConvertListener> {
        override fun createFromParcel(parcel: Parcel): DataConvertListener {
            return object : DataConvertListener(parcel) {
                override fun convertView(dialogBinding: Any, dialog: CommonDialog) {

                }

            }
        }

        override fun newArray(size: Int): Array<DataConvertListener?> {
            return arrayOfNulls(size)
        }
    }
}