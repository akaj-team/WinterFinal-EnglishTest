package vn.asiantech.englishtest.model

import android.os.Parcel
import java.io.Serializable

class ListQuestionDetailItem(val questionDetail : String,
                                  val answerA : String,
                                  val answerB : String,
                                  val answerC : String,
                                  val answerD : String) : Serializable


