package vn.asiantech.englishtest

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_list_reading_tests.*

class ReadingTestsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reading_tests)
        setSupportActionBar(toolbar as Toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar as Toolbar,
            R.string.navigationDrawerOpen,
            R.string.navigationDrawerClose
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        initFragment()
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
            .replace(R.id.fragmentListReadingTests, ListReadingTestFragment())
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemReadingLevelBasic -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                initFragment()
            }
            R.id.itemReadingLevelIntermediate -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                initFragment()
            }
            R.id.itemReadingLevelAdvanced -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                initFragment()
            }
        }
        return true
    }
}
