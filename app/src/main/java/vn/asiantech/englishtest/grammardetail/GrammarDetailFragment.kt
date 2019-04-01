package vn.asiantech.englishtest.grammardetail

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
import vn.asiantech.englishtest.model.GrammarDetailItem
import vn.asiantech.englishtest.takingreadingtest.TakingReadingTestActivity

class GrammarDetailFragment : Fragment() {

    private var grammarDetailAdapter: GrammarDetailAdapter? = null
    private var grammarDetailItem = arrayListOf<GrammarDetailItem>()
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            grammarDetailAdapter = GrammarDetailAdapter(grammarDetailItem)
            adapter = grammarDetailAdapter
        }
    }

    private fun initData() {
        val position = activity?.intent?.getIntExtra(ListReadingTestFragment.ARG_POSITION, 0)
        databaseReference = FirebaseDatabase.getInstance().getReference("grammarDetail0${position?.plus(1)}")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(grammarDetailData: DataSnapshot) {
                (activity as TakingReadingTestActivity).dismissProgressDialog()
                for (i in grammarDetailData.children) {
                    val grammarDetail = i.getValue(GrammarDetailItem::class.java)
                    grammarDetail?.let {
                        grammarDetailItem.add(it)
                    }
                }
                grammarDetailAdapter?.notifyDataSetChanged()
            }
        })
    }
}
