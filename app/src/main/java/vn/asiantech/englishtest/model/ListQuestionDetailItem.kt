package vn.asiantech.englishtest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListQuestionDetailItem(
    val question: String ?= null,
    val answerA: String ?= null,
    val answerB: String ?= null,
    val answerC: String ?= null,
    val answerD: String ?= null,
    val correctanswer : String ?= null,
    var myAnswer : String ?= null
) : Parcelable
