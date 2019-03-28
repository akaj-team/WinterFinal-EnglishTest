package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordStudyItem(
    val imageWordStudy: String = "",
    val word: String = "",
    val spelling: String = "",
    val definition: String = "",
    val meaning: String = "",
    val example: String = "",
    val translation: String = ""
) : Parcelable
