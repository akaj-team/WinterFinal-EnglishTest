package vn.asiantech.englishtest

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_list_reading_tests.*

class ListReadingTestActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reading_tests)
        setSupportActionBar(toolBar as Toolbar)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolBar as Toolbar, R.string.navigationDrawerOpen, R.string.navigationDrawerClose)
        supportActionBar?.title = getString(R.string.part5Basic)
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        initFragment()
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frListReadingTests, ListReadingTestFragment())
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
            .replace(R.id.frListReadingTests, ListReadingTestFragment())
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
                supportActionBar?.title = getString(R.string.part5Basic)
            }
            R.id.itemReadingLevelIntermediate -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                initFragment()
                supportActionBar?.title = getString(R.string.part5Intermediate)
            }
            R.id.itemReadingLevelAdvanced -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                initFragment()
                supportActionBar?.title = getString(R.string.part5Advanced)
            }
        }
        return true
    }
}
