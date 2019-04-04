@file:Suppress("DEPRECATION")

package vn.asiantech.englishtest.listtest

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_list_reading_tests.*
import vn.asiantech.englishtest.R
import vn.asiantech.englishtest.grammardetail.GrammarDetailFragment
import vn.asiantech.englishtest.grammarlist.GrammarListFragment
import vn.asiantech.englishtest.wordlist.WordListFragment

class TestListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reading_tests)
        progressDialog = ProgressDialog(this)
        setSupportActionBar(toolBar as Toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar as Toolbar,
            R.string.navigationDrawerOpen,
            R.string.navigationDrawerClose
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        initReferenceFragment(GrammarDetailFragment.getInstance(R.id.itemToeicIntroduction))
        supportActionBar?.title = getString(R.string.toeicIntroduction)
        navigationView.apply {
            setNavigationItemSelectedListener(this@TestListActivity)
            setCheckedItem(R.id.itemToeicIntroduction)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.colorBlue)
        }
    }

    override fun onBackPressed() = when {
        drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
        else -> initAlertDialog()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.itemPart1 -> {
                initListTestFragment(R.id.itemPart1)
                supportActionBar?.title = getString(R.string.part1)
            }
            R.id.itemPart2 -> {
                initListTestFragment(R.id.itemPart2)
                supportActionBar?.title = getString(R.string.part2)
            }
            R.id.itemPart3 -> {
                initListTestFragment(R.id.itemPart3)
                supportActionBar?.title = getString(R.string.part3)
            }
            R.id.itemPart4 -> {
                initListTestFragment(R.id.itemPart4)
                supportActionBar?.title = getString(R.string.part4)
            }
            R.id.itemPart5Basic -> {
                initListTestFragment(R.id.itemPart5Basic)
                supportActionBar?.title = getString(R.string.part5Basic)
            }
            R.id.itemPart5Intermediate -> {
                initListTestFragment(R.id.itemPart5Intermediate)
                supportActionBar?.title = getString(R.string.part5Intermediate)
            }
            R.id.itemPart5Advanced -> {
                initListTestFragment(R.id.itemPart5Advanced)
                supportActionBar?.title = getString(R.string.part5Advanced)
            }
            R.id.itemPart6 -> {
                initListTestFragment(R.id.itemPart6)
                supportActionBar?.title = getString(R.string.part6)
            }
            R.id.itemPart7 -> {
                initListTestFragment(R.id.itemPart7)
                supportActionBar?.title = getString(R.string.part7)
            }
            R.id.itemGrammar -> {
                initReferenceFragment(GrammarListFragment.getInstance(R.id.itemGrammar))
                supportActionBar?.title = getString(R.string.grammar)
            }
            R.id.itemToeicIntroduction -> {
                initReferenceFragment(GrammarDetailFragment.getInstance(R.id.itemToeicIntroduction))
                supportActionBar?.title = getString(R.string.toeicIntroduction)
            }
            R.id.itemWordStudy -> {
                initReferenceFragment(WordListFragment.getInstance(R.id.itemWordStudy))
                supportActionBar?.title = getString(R.string.wordStudy)
            }
        }
        return true
    }

    private fun initListTestFragment(level: Int) = supportFragmentManager.beginTransaction().apply {
        setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
        replace(R.id.frListReadingTest, TestListFragment.getInstance(level))
        commit()
    }

    private fun initReferenceFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
        replace(R.id.frListReadingTest, fragment)
        commit()
    }


    private fun initAlertDialog() = AlertDialog.Builder(this).create().apply {
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

    fun initProgressDialog() = progressDialog?.apply {
        setProgressStyle(ProgressDialog.STYLE_SPINNER)
        setMessage(getString(R.string.loadingData))
        show()
    }

    fun dismissProgressDialog() = progressDialog?.dismiss()
}
