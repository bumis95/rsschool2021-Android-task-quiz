package com.rsschool.quiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
                is QuizFragment -> {
                    showFragment(fragment, false)
                }
                else -> {
                    finish()
                }
            }
        }
    }

    override fun getPage(): Int = currentPage

    override fun setPage(page: Int) {
        currentPage = page
    }

    override fun incPage() {
        currentPage++
    }

    override fun decPage() {
        currentPage--
    }

    override fun setFragmentTheme(): Int {
        val id = when (currentPage) {
            1 -> R.color.deep_orange_100_dark
            2 -> R.color.yellow_100_dark
            3 -> R.color.yellow_100_dark
            4 -> R.color.yellow_100_dark
            else -> R.color.yellow_100_dark
        }
        window.statusBarColor = ContextCompat.getColor(applicationContext, id)
        return when (currentPage) {
            1 -> R.style.Theme_Quiz_First
            2 -> R.style.Theme_Quiz_Second
            3 -> R.style.Theme_Quiz_Third
            4 -> R.style.Theme_Quiz_Fourth
            else -> R.style.Theme_Quiz_Fifth
        }
    }


    override fun showFragment(fragment: Fragment, isBackStack: Boolean) {
        supportFragmentManager.commit {
            val tagName = if (!isBackStack) null else "f$currentPage"
            replace(R.id.fragmentContainerView, fragment, tagName)
            Log.d("QUIZ_FRAGMENT", "page=$currentPage")
            if (tagName != null) addToBackStack(tagName)
        }
    }
}