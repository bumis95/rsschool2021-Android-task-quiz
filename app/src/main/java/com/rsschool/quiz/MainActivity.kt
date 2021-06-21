package com.rsschool.quiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(), QuizFragment.OnQuizFragmentListener {

    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            showFragment(QuizFragment(), true)
        }
    }

    override fun onBackPressed() {
        if (currentPage == 1) {
            finish()
        } else {
            currentPage--
            val fragment =
                supportFragmentManager.findFragmentByTag("f$currentPage")
            when (fragment) {
                is QuizFragment -> showFragment(fragment, false)
                else -> finish()
            }
        }
    }

    override fun getPage(): Int = currentPage

    override fun setPage(page: Int) {
        currentPage = page
    }

    override fun setFragmentTheme() {
        val statusBarColor = when (currentPage) {
            1 -> R.color.deep_orange_100_dark
            2 -> R.color.yellow_100_dark
            3 -> R.color.cyan_100_dark
            4 -> R.color.light_green_100_dark
            else -> R.color.deep_purple_100_dark
        }
        window.statusBarColor = ContextCompat.getColor(applicationContext, statusBarColor)
        val fragmentTheme = when (currentPage) {
            1 -> R.style.Theme_Quiz_First
            2 -> R.style.Theme_Quiz_Second
            3 -> R.style.Theme_Quiz_Third
            4 -> R.style.Theme_Quiz_Fourth
            else -> R.style.Theme_Quiz_Fifth
        }
        theme.applyStyle(fragmentTheme, true)
    }

    override fun showFragment(fragment: Fragment, isBackStack: Boolean) {
        supportFragmentManager.commit {
            val tagName = if (!isBackStack) null else "f$currentPage"
            replace(R.id.fragmentContainerView, fragment, tagName)
            Log.d("QUIZ_FRAGMENT", "page=$currentPage")
            if (tagName != null) addToBackStack(tagName)
        }
    }

    override fun nextPage() {
        if (currentPage == ResultFragment.MAX_QUESTIONS) {
            val list = ArrayList<Int>()
            for (entry in 1..supportFragmentManager.backStackEntryCount) {
                val fragment = supportFragmentManager.findFragmentByTag("f$entry") as QuizFragment
                list.add(fragment.selectedAnswer)
            }
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            showFragment(ResultFragment.newInstance(list), false)
        } else {
            val fragment = supportFragmentManager.findFragmentByTag("f${currentPage.inc()}")
            if (fragment != null) {
                currentPage++
                showFragment(fragment, false)
            } else {
                currentPage++
                showFragment(QuizFragment(), true)
            }
        }
    }
}