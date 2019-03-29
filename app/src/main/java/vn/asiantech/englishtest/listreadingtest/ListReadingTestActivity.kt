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
        initListReadingTestFragment(R.id.itemPart5Basic)
        supportActionBar?.title = getString(R.string.part5Basic)
        navigationView.apply {
            setNavigationItemSelectedListener(this@ListReadingTestActivity)
            setCheckedItem(R.id.itemPart5Basic)
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
            R.id.itemPart5Basic -> {
                initListReadingTestFragment(R.id.itemPart5Basic)
                supportActionBar?.title = getString(R.string.part5Basic)
            }
            R.id.itemPart5Intermediate -> {
                initListReadingTestFragment(R.id.itemPart5Intermediate)
                supportActionBar?.title = getString(R.string.part5Intermediate)
            }
            R.id.itemPart5Advanced -> {
                initListReadingTestFragment(R.id.itemPart5Advanced)
                supportActionBar?.title = getString(R.string.part5Advanced)
            }
            R.id.itemPart6 -> {
                initListReadingTestFragment(R.id.itemPart6)
                supportActionBar?.title = getString(R.string.part6)
            }
            R.id.itemPart7 -> {
                initListReadingTestFragment(R.id.itemPart7)
                supportActionBar?.title = getString(R.string.part7)
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
