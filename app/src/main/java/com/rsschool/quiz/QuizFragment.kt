package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.rsschool.quiz.databinding.FragmentQuizBinding

private const val MAX_QUESTIONS = 5

class QuizFragment : Fragment() {

    var param1: Int = -1

    private var listener: OnQuizFragmentListener? = null
    private lateinit var question: Question

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnQuizFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        setToolbarTitle()
        initQuestion()
        initButtons()

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.nextButton.isEnabled = true
            when (checkedId) {
                binding.optionOne.id -> {
                    param1 = 0
                }
                binding.optionTwo.id -> {
                    param1 = 1
                }
                binding.optionThree.id -> {
                    param1 = 2
                }
                binding.optionFour.id -> {
                    param1 = 3
                }
                binding.optionFive.id -> {
                    param1 = 4
                }
            }
        }

        binding.nextButton.setOnClickListener {
            goToNext()
        }

        binding.previousButton.setOnClickListener {
            goToPrevious()
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setToolbarTitle() {
        binding.toolbar.title =
            "Question ${listener?.getFragmentCount()}"
    }

    private fun initQuestion() {
        val page = listener?.getFragmentCount()?.dec()
        if (page != null) {
            question = questions[page]

            binding.question.text = question.text
            binding.optionOne.text = question.answers[0]
            binding.optionTwo.text = question.answers[1]
            binding.optionThree.text = question.answers[2]
            binding.optionFour.text = question.answers[3]
            binding.optionFive.text = question.answers[4]
        }
    }

    private fun initButtons() {
        val currentFragment = requireActivity()
            .supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as QuizFragment
        if (currentFragment.tag == "f$MAX_QUESTIONS")
            binding.nextButton.text = "Submit"
        if (currentFragment.tag == "f1")
            binding.previousButton.isEnabled = false
    }

    private fun goToNext() {
        val currentPage = listener?.getFragmentCount()
        val fragment =
            requireActivity().supportFragmentManager.findFragmentByTag("f${currentPage?.inc()}")
        if (fragment != null) {
            listener?.incFragmentCount()
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragmentContainerView, fragment)
            }
        } else {
            if (currentPage == MAX_QUESTIONS) {
                val list = ArrayList<Int>()
                for (entry in 1..requireActivity().supportFragmentManager.backStackEntryCount) {
                    val fragment1 =
                        requireActivity().supportFragmentManager.findFragmentByTag("f$entry") as QuizFragment
                    list.add(fragment1.param1)
                }

                requireActivity().supportFragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                requireActivity().supportFragmentManager.commit {
                    replace(
                        R.id.fragmentContainerView,
                        ResultFragment.newInstance(list)
                    )
                    addToBackStack("res")
                }
            } else {
                listener?.incFragmentCount()
                val page = listener?.getFragmentCount()
                requireActivity().supportFragmentManager.commit {
                    Log.d("QUIZ_FRAGMENT", "page=$page")
                    replace(R.id.fragmentContainerView, QuizFragment(), "f$page")
                    addToBackStack("f$page")
                }
            }
        }
    }

    private fun goToPrevious() {
        listener?.decFragmentCount()
        val page = listener?.getFragmentCount()
        val fragment =
            requireActivity().supportFragmentManager.findFragmentByTag("f$page") as QuizFragment
        requireActivity().supportFragmentManager.commit {
            Log.d("QUIZ_FRAGMENT", "page=$page")
            replace(R.id.fragmentContainerView, fragment)
        }
    }

    interface OnQuizFragmentListener {
        fun getFragmentCount(): Int
        fun incFragmentCount()
        fun decFragmentCount()
    }
}