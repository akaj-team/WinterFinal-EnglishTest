package vn.asiantech.englishtest.questiondetailviewpager

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_question_detail.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.R.string.explanation
import vn.asiantech.englishtest.R.string.translation
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity
import java.text.SimpleDateFormat

@Suppress("DEPRECATION")
class QuestionDetailFragment : Fragment() {

    private var data: ListQuestionDetailItem? = null
    private var position = 0
    private var level: Int? = null
    private var isDestroy = false

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

        (activity as TakingReadingTestActivity).apply {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }
        showView()
        selectedAnswer()
        onClickPlayAudio()
        setDataFirebase()
    }

    private fun showView() {
        when (level) {
            R.id.itemPart6 -> {
                tvQuestionContent.visibility = View.VISIBLE
                tvQuestionTitle.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            R.id.itemPart7 -> {
                tvQuestionContent.visibility = View.VISIBLE
                tvQuestionTitle.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                setLayoutHeight()
            }
            R.id.itemPart1 -> {
                with(View.VISIBLE) {
                    tvQuestionContent.visibility = this
                    imgQuestionTitle.visibility = this
                    cardViewAudio.visibility = this
                }
                tvQuestionTitle.visibility = View.GONE
                setLayoutHeight()
            }
            R.id.itemPart2 -> {
                with(View.VISIBLE) {
                    tvQuestionContent.visibility = this
                    cardViewAudio.visibility = this
                }
                with(View.GONE) {
                    tvQuestionTitle.visibility = this
                    rbAnswerD.visibility = this
                    divider4.visibility = this
                }
            }
        }
    }

    private fun setDataFirebase() {
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
                tvQuestionContent.text = questionContent

                if (level != R.id.itemPart1 && level != R.id.itemPart2) {
                    tvQuestionTitle.text = questionTitle
                    rbAnswerA.text = answerA
                    rbAnswerB.text = answerB
                    rbAnswerC.text = answerC
                    rbAnswerD.text = answerD
                    tvExplanation.text = explanation
                    tvTranslation.text = translation
                    tvQuestionContent.text = questionContent
                }
            }
            if ((activity as TakingReadingTestActivity).review) {
                if (level == R.id.itemPart1 || level == R.id.itemPart2) {
                    data?.let { it1 ->
                        with(it1) {
                            rbAnswerA.text = answerA
                            rbAnswerB.text = answerB
                            rbAnswerC.text = answerC
                            rbAnswerD.text = answerD
                        }
                    }

                } else {
                    cardViewExplanation.visibility = View.VISIBLE
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

    private fun onClickPlayAudio() {
        @SuppressLint("SimpleDateFormat")
        val timeFormat = SimpleDateFormat("mm:ss")
        imgState.setOnClickListener {

            (activity as TakingReadingTestActivity).mediaPlayer?.apply {
                try {
                    setDataSource(data?.audio)
                    setOnPreparedListener { mp -> mp.start() }
                    prepare()
                } catch (e: Exception) {
                }
                seekBarPlay.max = duration
                tvTotalTime.text = timeFormat.format(duration)

                val handler = Handler()
                (activity as TakingReadingTestActivity).runOnUiThread(object : Runnable {
                    override fun run() {
                        if (isDestroy) {
                            return
                        }
                        try {
                            seekBarPlay.progress = currentPosition
                            tvCurrentTime.text = timeFormat.format(currentPosition)
                            handler.postDelayed(this, 1000)
                        } catch (e: Exception) {
                        }
                    }
                })
                seekBarChangeListener()
                if (isPlaying) {
                    pause()
                    imgState.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                } else {
                    start()
                    imgState.setImageResource(R.drawable.ic_pause_black_24dp)
                }
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser && isResumed) {
            (activity as TakingReadingTestActivity).mediaPlayer?.pause()
            imgState.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as TakingReadingTestActivity).mediaPlayer?.stop()
        isDestroy = true
    }

    private fun seekBarChangeListener() {
        seekBarPlay.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if ((activity as TakingReadingTestActivity).mediaPlayer != null && fromUser) {
                    (activity as TakingReadingTestActivity).mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun setLayoutHeight() {
        with(ViewGroup.LayoutParams.WRAP_CONTENT) {
            rbAnswerA.layoutParams.height = this
            rbAnswerB.layoutParams.height = this
            rbAnswerC.layoutParams.height = this
            rbAnswerD.layoutParams.height = this
        }
    }
}
