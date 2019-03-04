package vn.asiantech.englishtest.takingreadingtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_question_items.view.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.model.ListQuestionItem

class ListQuestionAdapter(
    private val listQuestions: MutableList<ListQuestionItem>,
    private val listener: OnItemClickQuestionNumber
) :
    RecyclerView.Adapter<ListQuestionAdapter.ListQuestionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ListQuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_question_items, parent, false)
        return ListQuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listQuestions.size
    }

    override fun onBindViewHolder(holder: ListQuestionViewHolder, position: Int) {
        holder.bindView(listQuestions[position])
    }

    inner class ListQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            listener.onClickQuestionNumber(layoutPosition)
        }

        fun bindView(listItem: ListQuestionItem) {
            with(itemView) { with(listItem) { tvQuestionNumber.text = testNumber.toString() } }
            itemView.tvQuestionNumber.setOnClickListener(this)
        }
    }

    interface OnItemClickQuestionNumber {
        fun onClickQuestionNumber(position: Int)
    }
}
