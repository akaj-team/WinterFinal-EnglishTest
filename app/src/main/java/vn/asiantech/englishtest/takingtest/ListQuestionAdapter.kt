package vn.asiantech.englishtest.takingtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list_question.view.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.QuestionNumberItem

class ListQuestionAdapter(
    private val listQuestions: MutableList<QuestionNumberItem>,
    private val listener: OnItemClickQuestionNumber
) :
    RecyclerView.Adapter<ListQuestionAdapter.ListQuestionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ListQuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_question, parent, false)
        return ListQuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listQuestions.size
    }

    override fun onBindViewHolder(holder: ListQuestionViewHolder, position: Int) {
        holder.bindView(listQuestions[position])
    }

    interface OnItemClickQuestionNumber {
        fun onClickQuestionNumber(position: Int)
    }

    inner class ListQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            listener.onClickQuestionNumber(layoutPosition)
        }

        fun bindView(listItem: QuestionNumberItem) {
            with(itemView) { with(listItem) { tvQuestionNumber.text = testNumber.toString() } }
            itemView.tvQuestionNumber.setOnClickListener(this)
        }
    }
}
