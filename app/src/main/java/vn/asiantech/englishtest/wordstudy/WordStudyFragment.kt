package vn.asiantech.englishtest.wordstudy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_grammar_detail.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.listreadingtest.ListReadingTestFragment
import vn.asiantech.englishtest.model.WordStudyItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity

class WordStudyFragment : Fragment() {

    private var wordStudyItem = arrayListOf<WordStudyItem>()
    private var wordStudyAdapter: WordStudyAdapter? = null
    private lateinit var reference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grammar_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        recycleViewGrammarDetail.apply {
            layoutManager = LinearLayoutManager(activity)
            wordStudyAdapter = WordStudyAdapter(wordStudyItem)
            adapter = wordStudyAdapter
        }
    }

    private fun initData() {
        val position = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        reference = FirebaseDatabase.getInstance().getReference("wordStudy0${position?.plus(1)}")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(wordStudyData: DataSnapshot) {
                (activity as TakingReadingTestActivity).dismissProgressDialog()
                for (i in wordStudyData.children) {
                    val wordDetail = i.getValue(WordStudyItem::class.java)
                    wordDetail?.let {
                        wordStudyItem.add(it)
                    }
                }
                wordStudyAdapter?.notifyDataSetChanged()
            }
        })
    }
}