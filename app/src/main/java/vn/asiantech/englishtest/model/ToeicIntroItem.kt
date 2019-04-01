package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToeicIntroItem(
    var introTitle: String = "",
    var introContent: String = ""
) : Parcelable
