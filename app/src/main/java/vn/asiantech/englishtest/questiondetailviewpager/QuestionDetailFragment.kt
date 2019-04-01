package vn.asiantech.englishtest.questiondetailviewpager

import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_taking_reading_test.*
import kotlinx.android.synthetic.main.fragment_question_detail.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.ListQuestionDetailItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity
import java.util.*


@Suppress("DEPRECATION")
class QuestionDetailFragment : Fragment() {

    private var data: ListQuestionDetailItem? = null
    private var position = 0
    private var mediaPlay: MediaPlayer? = null
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
        mediaPlay = MediaPlayer()
        mediaPlay?.setAudioStreamType(AudioManager.STREAM_MUSIC)

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
                ViewGroup.LayoutParams.WRAP_CONTENT.let {
                    rbAnswerA.layoutParams.height = it
                    rbAnswerB.layoutParams.height = it
                    rbAnswerC.layoutParams.height = it
                    rbAnswerD.layoutParams.height = it
                }
            }
            R.id.itemPart1 -> {
                View.VISIBLE.let {
                    tvQuestionContent.visibility = it
                    imgQuestionTitle.visibility = it
                    cardViewAudio.visibility = it
                }
                tvQuestionTitle.visibility = View.GONE
                ViewGroup.LayoutParams.WRAP_CONTENT.let {
                    rbAnswerA.layoutParams.height = it
                    rbAnswerB.layoutParams.height = it
                    rbAnswerC.layoutParams.height = it
                    rbAnswerD.layoutParams.height = it
                }
            }
            R.id.itemPart2 -> {
                View.VISIBLE.let {
                    tvQuestionContent.visibility = it
                    cardViewAudio.visibility = it
                }
                View.GONE.let {
                    tvQuestionTitle.visibility = it
                    rbAnswerD.visibility = it
                    divider4.visibility = it
                }
            }
            R.id.itemPart3, R.id.itemPart4 -> {
                View.VISIBLE.let {
                    tvQuestionContent.visibility = it
                    cardViewAudio.visibility = it
                }
                View.GONE.let {
                    tvQuestionTitle.visibility = it
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
                    tvQuestionContent.text = questionContent
                    tvExplanation.text = explanation
                    tvTranslation.text = translation
                }
            }
            if ((activity as TakingReadingTestActivity).review) {
                cardViewExplanation.visibility = View.VISIBLE
                if (level == R.id.itemPart1 || level == R.id.itemPart2) {
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

    private fun onClickPlayAudio() {
        imgState.setOnClickListener {
            try {
                mediaPlay?.setDataSource(data?.audio)
                mediaPlay?.setOnPreparedListener { mp -> mp.start() }
                mediaPlay?.prepare()
            } catch (e: Exception) {
            }
            seekBarPlay.max = mediaPlay?.duration ?: 0
            val timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    if (isDestroy) {
                        return
                    }
                    seekBarPlay.progress = mediaPlay?.currentPosition ?: 0
                }
            }, 0, 1000)

            seekBarChangeListener()
            if (mediaPlay?.isPlaying == true) {
                mediaPlay?.pause()
                imgState.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            } else {
                mediaPlay?.start()
                imgState.setImageResource(R.drawable.ic_pause_black_24dp)
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
            mediaPlay?.pause()
            imgState.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlay?.stop()
        mediaPlay?.release()
        mediaPlay = null
        isDestroy = true
    }

    private fun seekBarChangeListener() {
        seekBarPlay.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (mediaPlay != null && fromUser) {
                    mediaPlay?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}
