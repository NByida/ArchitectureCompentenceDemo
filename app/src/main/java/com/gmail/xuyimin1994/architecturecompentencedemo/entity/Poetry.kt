package com.gmail.xuyimin1994.architecturecompentencedemo.entity

import android.os.Parcel
import android.os.Parcelable

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.entity
 *yida
 *2019/9/17 0017
 **/
class Poetry() :Parcelable{
    var content: String? = null
    var translate: String? = null
    var notes: String? = null
    var appreciation: String? = null
    var pinyin: String? = null
    var name: String? = null
    var dynasty: String? = null
    var poet: String? = null
    var poetId: Long = 0

    constructor(parcel: Parcel) : this() {
        content = parcel.readString()
        translate = parcel.readString()
        notes = parcel.readString()
        appreciation = parcel.readString()
        pinyin = parcel.readString()
        name = parcel.readString()
        dynasty = parcel.readString()
        poet = parcel.readString()
        poetId = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(content)
        parcel.writeString(translate)
        parcel.writeString(notes)
        parcel.writeString(appreciation)
        parcel.writeString(pinyin)
        parcel.writeString(name)
        parcel.writeString(dynasty)
        parcel.writeString(poet)
        parcel.writeLong(poetId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Poetry> {
        override fun createFromParcel(parcel: Parcel): Poetry {
            return Poetry(parcel)
        }

        override fun newArray(size: Int): Array<Poetry?> {
            return arrayOfNulls(size)
        }
    }
}