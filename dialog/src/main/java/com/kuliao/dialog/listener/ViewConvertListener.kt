package com.kuliao.dialog.listener

import android.os.Parcel
import android.os.Parcelable
import com.kuliao.dialog.CommonDialog
import com.kuliao.dialog.other.ViewHolder

/**
 * Author: WangHao
 * Created On: 2020/07/29  14:43
 * Description:
 */
abstract class ViewConvertListener : Parcelable {


    abstract fun convertView(holder: ViewHolder, dialog: CommonDialog)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    constructor() {}

    protected constructor(source: Parcel) {}

    companion object CREATOR : Parcelable.Creator<ViewConvertListener> {
        override fun createFromParcel(parcel: Parcel): ViewConvertListener {
            return object :ViewConvertListener(parcel){
                override fun convertView(holder: ViewHolder, dialog: CommonDialog) {

                }

            }
        }

        override fun newArray(size: Int): Array<ViewConvertListener?> {
            return arrayOfNulls(size)
        }
    }


}