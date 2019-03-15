package vn.asiantech.englishtest.questiondetailviewpager

import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_question_detail.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity

@Suppress("DEPRECATION")
class QuestionDetailFragment : Fragment() {

    private var data: ListQuestionDetailItem? = null
    private var position = 0
    private var mediaPlay: MediaPlayer? = null
    private var level: Int? = null

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_DATA = "arg_data"

        fun getInstance(position: Int, question: ListQuestionDetailItem): QuestionDetailFragment =
            QuestionDetailFragment().apply {
                val bundle = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putParcelable(ARG_DATA, question)
                }
                arguments = bundle
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            data = it.getParcelable(ARG_DATA) as ListQuestionDetailItem
        }
        (activity as TakingReadingTestActivity).apply {
            progressDialog?.dismiss()
            chronometer.start()
        }
        level = (activity as TakingReadingTestActivity).intent.getIntExtra(ListReadingTestFragment.ARG_LEVEL, 0)
        return inflater.inflate(R.layout.fragment_question_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (level) {
            R.id.itemPart6 -> {
                tvQuestionContent.visibility = View.VISIBLE
                tvQuestionTitle.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            R.id.itemPart7 -> {
                tvQuestionContent.visibility = View.VISIBLE
                tvQuestionTitle.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                ViewGroup.LayoutParams.WRAP_CONTENT.let {
                    rbAnswerA.layoutParams.height = it
                    rbAnswerB.layoutParams.height = it
                    rbAnswerC.layoutParams.height = it
                    rbAnswerD.layoutParams.height = it
                }
            }
            R.id.itemPart1 -> {
                tvQuestionContent.visibility = View.VISIBLE
                imgQuestionTitle.visibility = View.VISIBLE
                tvQuestionTitle.visibility = View.GONE
                cardViewAudio.visibility = View.VISIBLE
                ViewGroup.LayoutParams.WRAP_CONTENT.let {
                    rbAnswerA.layoutParams.height = it
                    rbAnswerB.layoutParams.height = it
                    rbAnswerC.layoutParams.height = it
                    rbAnswerD.layoutParams.height = it
                }
            }
        }
        selectedAnswer()
        data?.let {
            with(it) {
                when ((activity as TakingReadingTestActivity).intent.getIntExtra(
                    ListReadingTestFragment.ARG_LEVEL,
                    0
                )) {
                    R.id.itemPart1 -> Glide.with(activity as TakingReadingTestActivity).load(questionTitle).into(
                        imgQuestionTitle
                    )
                }
                btnPlay.setOnClickListener {
                    try {
                        mediaPlay?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                        mediaPlay?.setDataSource(audio)
                        mediaPlay?.setOnPreparedListener { mp -> mp.start() }
                        mediaPlay?.prepare()
                    } catch (e: Exception) {

                    }
                }
                tvQuestionContent.text = questionContent
                if (level != R.id.itemPart1) {
                    tvQuestionTitle.text = questionTitle
                    rbAnswerA.text = answerA
                    rbAnswerB.text = answerB
                    rbAnswerC.text = answerC
                    rbAnswerD.text = answerD
                    tvQuestionContent.text = questionContent
                }
            }

            if ((activity as TakingReadingTestActivity).review) {
                if (level == R.id.itemPart1) {
                    data?.let { it1 ->
                        with(it1) {
                            rbAnswerA.text = answerA
                            rbAnswerB.text = answerB
                            rbAnswerC.text = answerC
                            rbAnswerD.text = answerD
                        }
                    }
                }
                with(it) {
                    if (myAnswer != correctAnswer) {
                        when (correctAnswer) {

                            answerA -> rbAnswerA.setBackgroundColor(if (myAnswer.isBlank()) Color.YELLOW else Color.GREEN)
                            answerB -> rbAnswerB.setBackgroundColor(if (myAnswer.isBlank()) Color.YELLOW else Color.GREEN)
                            answerC -> rbAnswerC.setBackgroundColor(if (myAnswer.isBlank()) Color.YELLOW else Color.GREEN)
                            answerD -> rbAnswerD.setBackgroundColor(if (myAnswer.isBlank()) Color.YELLOW else Color.GREEN)
                        }
                        when (myAnswer) {
                            answerA -> rbAnswerA.setBackgroundColor(Color.RED)
                            answerB -> rbAnswerB.setBackgroundColor(Color.RED)
                            answerC -> rbAnswerC.setBackgroundColor(Color.RED)
                            answerD -> rbAnswerD.setBackgroundColor(Color.RED)
                        }
                    }
                }
                rbAnswerA.isClickable = false
                rbAnswerB.isClickable = false
                rbAnswerC.isClickable = false
                rbAnswerD.isClickable = false
            }
        }
    }

    private fun selectedAnswer() {
        rgAnswer.setOnCheckedChangeListener { _, _ ->
            when {
                rbAnswerA.isChecked -> {
                    data?.apply {
                        myAnswer = answerA
                    }
                }
                rbAnswerB.isChecked -> {
                    data?.apply {
                        myAnswer = answerB
                    }
                }
                rbAnswerC.isChecked -> {
                    data?.apply {
                        myAnswer = answerC
                    }
                }
                rbAnswerD.isChecked -> {
                    data?.apply {
                        myAnswer = answerD
                    }
                }
            }
        }
    }
}
