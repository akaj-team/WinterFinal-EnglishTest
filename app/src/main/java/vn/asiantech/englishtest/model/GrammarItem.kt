package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GrammarItem (
    var key1: String = "",
    var key2: String = ""
) : Parcelable
