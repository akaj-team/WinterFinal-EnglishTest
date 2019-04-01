package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GrammarDetailItem(
    var grammarDetailTitle: String = "",
    var grammarDetailDescription: String = ""
) : Parcelable
