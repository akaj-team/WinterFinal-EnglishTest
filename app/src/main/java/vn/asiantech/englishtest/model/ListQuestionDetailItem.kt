package vn.asiantech.englishtest.model

import android.os.Parcel
import android.os.Parcelable

class ListQuestionDetailItem() : Parcelable {
    val question : String? = null
    val answerA : String? = null
    val answerB : String? = null
    val answerC : String? = null
    val answerD : String? = null

    constructor(@Suppress("UNUSED_PARAMETER") parcel: Parcel) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListQuestionDetailItem> {
        override fun createFromParcel(parcel: Parcel): ListQuestionDetailItem {
            return ListQuestionDetailItem(parcel)
        }

        override fun newArray(size: Int): Array<ListQuestionDetailItem?> {
            return arrayOfNulls(size)
        }
    }
}
