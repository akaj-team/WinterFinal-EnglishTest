package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ListReadingTestsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reading_tests)

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentListReadingTests, ListReadingTestFragment())
            .commit()
    }
}
