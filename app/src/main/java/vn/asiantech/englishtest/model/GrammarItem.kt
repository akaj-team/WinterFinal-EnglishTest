package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GrammarItem (
    var grammarTitle: String = "",
    var grammarExample: String = ""
) : Parcelable
