package vn.asiantech.englishtest

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_list_reading_tests.*

class ListReadingTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reading_tests)
        setSupportActionBar(toolBar as Toolbar)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolBar as Toolbar, R.string.navigationDrawerOpen, R.string.navigationDrawerClose)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frListReadingTests, ListReadingTestFragment())
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
