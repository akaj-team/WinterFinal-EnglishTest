package vn.asiantech.englishtest.model

import android.os.Parcel
import android.os.Parcelable

data class ListQuestionDetailItem(
    val question: String, val answerA: String,
    val answerB: String, val answerC: String, val answerD: String
) : Parcelable {
    constructor() : this("", "", "", "", "")
    constructor(@Suppress("UNUSED_PARAMETER") parcel: Parcel) : this("", "", "", "", "")

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
