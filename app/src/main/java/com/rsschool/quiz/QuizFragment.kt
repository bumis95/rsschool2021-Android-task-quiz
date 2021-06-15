package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rsschool.quiz.databinding.FragmentQuizBinding

private const val MAX_QUESTIONS = 5

class QuizFragment : Fragment() {

    private var selectedAnswer: Int = -1
    private lateinit var question: Question
    private lateinit var fragment: Fragment

    private lateinit var listener: OnQuizFragmentListener

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
        val contextThemeWrapper = ContextThemeWrapper(activity, listener.setTheme())
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        requireContext().theme.applyStyle(listener.setTheme(), true)
        _binding = FragmentQuizBinding.inflate(localInflater, container, false)

        setToolbarTitle()
        initQuestion()
        initButtons()

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.nextButton.isEnabled = true
            when (checkedId) {
                binding.optionOne.id -> {
                    selectedAnswer = 0
                }
                binding.optionTwo.id -> {
                    selectedAnswer = 1
                }
                binding.optionThree.id -> {
                    selectedAnswer = 2
                }
                binding.optionFour.id -> {
                    selectedAnswer = 3
                }
                binding.optionFive.id -> {
                    selectedAnswer = 4
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setToolbarTitle() {
        binding.toolbar.title =
            "Question ${listener.getPage()}"
    }

    private fun initQuestion() {
        val page = listener.getPage().dec()
        question = questions[page]
        binding.question.text = question.text
        binding.optionOne.text = question.answers[0]
        binding.optionTwo.text = question.answers[1]
        binding.optionThree.text = question.answers[2]
        binding.optionFour.text = question.answers[3]
        binding.optionFive.text = question.answers[4]
    }

    private fun initButtons() {
        fragment = requireActivity()
            .supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as QuizFragment
        when (fragment.tag) {
            "f1" -> binding.previousButton.isEnabled = false
            "f$MAX_QUESTIONS" -> binding.nextButton.text = "Submit"
        }
    }

    private fun goToNext() {
        val currentPage = listener.getPage()
        val fragment =
            requireActivity().supportFragmentManager.findFragmentByTag("f${currentPage.inc()}")
        if (fragment != null) {
            listener.incPage()
            listener.showFragment(fragment, false)
        } else {
            if (currentPage == MAX_QUESTIONS) {
                val list = ArrayList<Int>()
                for (entry in 1..requireActivity().supportFragmentManager.backStackEntryCount) {
                    val fragment1 =
                        requireActivity().supportFragmentManager.findFragmentByTag("f$entry") as QuizFragment
                    list.add(fragment1.selectedAnswer)
                }

                requireActivity().supportFragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                listener.showFragment(ResultFragment.newInstance(list), false)
            } else {
                listener.incPage()
                listener.showFragment(QuizFragment(), true)
            }
        }
    }

    private fun goToPrevious() {
        listener.decPage()
        val page = listener.getPage()
        val fragment =
            requireActivity().supportFragmentManager.findFragmentByTag("f$page") as QuizFragment
        listener.showFragment(fragment, false)
    }

    interface OnQuizFragmentListener {
        fun setPage(page: Int)
        fun getPage(): Int
        fun incPage()
        fun decPage()
        fun setTheme(): Int
        fun showFragment(fragment: Fragment, isBackStack: Boolean)
    }
}