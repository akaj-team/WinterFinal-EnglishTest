package vn.asiantech.englishtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_question_items.view.*
import vn.asiantech.englishtest.model.ListQuestionItems

class ListQuestionAdapter(private val listQuestions: List<ListQuestionItems>) :
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

    inner class ListQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(listItems: ListQuestionItems) {
            with(itemView) { with(listItems) { tvQuestionNumber.text = testNumber.toString() } }
        }
    }
}
