package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListQuestionDetailItem(
    val questionTitle: String = "",
    val answerA: String = "",
    val answerB: String = "",
    val answerC: String = "",
    val answerD: String = "",
    val correctAnswer: String = "",
    var myAnswer: String = "",
    val audio : String = "",
    var explanation: String = "",
    var translation: String = "",
    val questionContent: String = "",
    val questionDetail: String = ""
) : Parcelable
