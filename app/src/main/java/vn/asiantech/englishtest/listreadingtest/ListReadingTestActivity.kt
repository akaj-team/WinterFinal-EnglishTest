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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reading_tests)
        setSupportActionBar(toolBar as Toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar as Toolbar,
            R.string.navigationDrawerOpen,
            R.string.navigationDrawerClose
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        initListReadingTestFragment(R.id.itemReadingLevelBasic)
        supportActionBar?.title = getString(R.string.part5Basic)
        navigationView.apply {
            setNavigationItemSelectedListener(this@ListReadingTestActivity)
            setCheckedItem(R.id.itemReadingLevelBasic)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            initAlertDialog()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.itemReadingLevelBasic -> {
                initListReadingTestFragment(R.id.itemReadingLevelBasic)
                supportActionBar?.title = getString(R.string.part5Basic)
            }
            R.id.itemReadingLevelIntermediate -> {
                initListReadingTestFragment(R.id.itemReadingLevelIntermediate)
                supportActionBar?.title = getString(R.string.part5Intermediate)
            }
            R.id.itemReadingLevelAdvanced -> {
                initListReadingTestFragment(R.id.itemReadingLevelAdvanced)
                supportActionBar?.title = getString(R.string.part5Advanced)
            }
            R.id.itemPart1 -> {
                initListReadingTestFragment(R.id.itemPart1)
                supportActionBar?.title = getString(R.string.part1)
            }
            R.id.itemPart2 -> {
                initListReadingTestFragment(R.id.itemPart2)
                supportActionBar?.title = getString(R.string.part2)
            }
            R.id.itemPart3 -> {
                initListReadingTestFragment(R.id.itemPart3)
                supportActionBar?.title = getString(R.string.part3)
            }
            R.id.itemPart4 -> {
                initListReadingTestFragment(R.id.itemPart4)
                supportActionBar?.title = getString(R.string.part4)
            }
        }
        return true
    }

    private fun initListReadingTestFragment(level: Int) {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left
            )
            replace(
                R.id.frListReadingTest,
                ListReadingTestFragment.getInstance(level)
            )
            commit()
        }
    }

    private fun initAlertDialog() {
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
}
