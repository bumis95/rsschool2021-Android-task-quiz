package com.rsschool.quiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(), QuizFragment.OnQuizFragmentListener {

    private var fragmentCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragmentContainerView, QuizFragment.newInstance(), "f$fragmentCount")
                Log.d("QUIZ_FRAGMENT", "page=$fragmentCount")
                addToBackStack("f$fragmentCount")
            }
        }
    }

    override fun onBackPressed() {
        if (fragmentCount == 1) {
            finish()
        } else {
            fragmentCount--
            val fragment =
                supportFragmentManager.findFragmentByTag("f$fragmentCount") as QuizFragment
            supportFragmentManager.commit {
                Log.d("QUIZ_FRAGMENT", "page=$fragmentCount")
                replace(R.id.fragmentContainerView, fragment)
            }
        }
    }

    override fun getFragmentCount(): Int = fragmentCount

    override fun incFragmentCount() {
        fragmentCount++
    }

    override fun decFragmentCount() {
        fragmentCount--
    }
}