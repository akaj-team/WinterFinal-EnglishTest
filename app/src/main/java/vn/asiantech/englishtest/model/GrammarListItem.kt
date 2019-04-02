package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GrammarListItem (
    var grammarTitle: String = "",
    var grammarExample: String = ""
) : Parcelable
