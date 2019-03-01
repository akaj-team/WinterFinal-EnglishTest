package vn.asiantech.englishtest.listreadingtest

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_list_reading_tests.*
import vn.asiantech.englishtest.R

class ListReadingTestActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var navItemSelectedPosition = 0
    private var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reading_tests)

        level = intent.getIntExtra(getString(R.string.level), 0)

        setSupportActionBar(toolBar as Toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar as Toolbar,
            R.string.navigationDrawerOpen,
            R.string.navigationDrawerClose
        )
        setNavigationItem()
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        initListReadingTestFragment(level)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initListReadingTestFragment(level: Int) {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            replace(
                R.id.frListReadingTest,
                ListReadingTestFragment.getInstance(level)
            )
            commit()
        }
    }

    private fun setNavigationItem() {
        when (level) {
            0 -> {
                supportActionBar?.title = getString(R.string.part5Basic)
                navigationView.setCheckedItem(R.id.itemReadingLevelBasic)
            }
            1 -> {
                supportActionBar?.title = getString(R.string.part5Intermediate)
                navigationView.setCheckedItem(R.id.itemReadingLevelIntermediate)
            }
            2 -> {
                supportActionBar?.title = getString(R.string.part5Advanced)
                navigationView.setCheckedItem(R.id.itemReadingLevelAdvanced)
            }
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).create().apply {
            setTitle(getString(R.string.confirmExit))
            setMessage(getString(R.string.doYouWantToExit))
            setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no))
            { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes))
            { _, _ ->
                finish()
            }
        }.show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            showAlertDialog()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemReadingLevelBasic -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                initListReadingTestFragment(navItemSelectedPosition)
                supportActionBar?.title = getString(R.string.part5Basic)
            }
            R.id.itemReadingLevelIntermediate -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                navItemSelectedPosition = 1
                initListReadingTestFragment(navItemSelectedPosition)
                supportActionBar?.title = getString(R.string.part5Intermediate)
            }
            R.id.itemReadingLevelAdvanced -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                navItemSelectedPosition = 2
                initListReadingTestFragment(navItemSelectedPosition)
                supportActionBar?.title = getString(R.string.part5Advanced)
            }
        }
        return true
    }
}
