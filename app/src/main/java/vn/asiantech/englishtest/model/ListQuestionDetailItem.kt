package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListQuestionDetailItem(
    val question: String = "",
    val answerA: String = "",
    val answerB: String = "",
    val answerC: String = "",
    val answerD: String = "",
    val correctanswer: String = "",
    var myAnswer: String = ""
) : Parcelable
