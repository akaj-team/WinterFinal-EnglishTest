package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
    }

    private fun initFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.listListeningTestsFragment, ListListeningTestsFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
