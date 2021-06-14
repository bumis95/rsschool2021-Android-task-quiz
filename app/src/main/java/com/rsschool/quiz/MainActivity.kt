package com.rsschool.quiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    override fun showFragment(fragment: Fragment, isBackStack: Boolean) {
        supportFragmentManager.commit {
            val tagName = if (!isBackStack) null else "f$currentPage"
            replace(R.id.fragmentContainerView, fragment, tagName)
            Log.d("QUIZ_FRAGMENT", "page=$currentPage")
            if (tagName != null) addToBackStack(tagName)
        }
    }
}