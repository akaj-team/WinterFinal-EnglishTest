package vn.asiantech.englishtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_question_detail.view.*
import vn.asiantech.englishtest.RecyclerAdapter.Part34ViewHolder
import vn.asiantech.englishtest.model.ListQuestionDetailItem

class RecyclerAdapter(private val listQuestionDetailItem: List<ListQuestionDetailItem>) : RecyclerView.Adapter<Part34ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position : Int): Part34ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_question_detail, parent, false)
        return Part34ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listQuestionDetailItem.size
    }

    override fun onBindViewHolder(holder : Part34ViewHolder, position: Int) {
        holder.bindView(listQuestionDetailItem[position])
    }

    class Part34ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(questionDetailItem: ListQuestionDetailItem) {
            with(itemView){
                with(questionDetailItem) {
                    tvQuestionContent.text = questionContent
                    rbAnswerA.text = answerA
                    rbAnswerB.text = answerB
                    rbAnswerC.text = answerC
                    rbAnswerD.text = answerD
                }
            }
        }
    }
}