package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.ResultFragment.Companion.MAX_QUESTIONS
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    var selectedAnswer: Int = -1
    private lateinit var question: Question
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

        initFragmentTheme()
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        setToolbarTitle()
        initQuestion()
        initButtons()

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.nextButton.isEnabled = true
            when (checkedId) {
                binding.optionOne.id -> selectedAnswer = 0
                binding.optionTwo.id -> selectedAnswer = 1
                binding.optionThree.id -> selectedAnswer = 2
                binding.optionFour.id -> selectedAnswer = 3
                binding.optionFive.id -> selectedAnswer = 4
            }
        }

        binding.nextButton.setOnClickListener {
            listener.nextPage()
        }

        binding.previousButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFragmentTheme() {
        listener.setFragmentTheme()
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
        val fragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as QuizFragment
        when (fragment.tag) {
            "f1" -> {
                binding.previousButton.isEnabled = false
                binding.toolbar.navigationIcon = null
            }
            "f$MAX_QUESTIONS" -> binding.nextButton.text = getString(R.string.submit)
        }
    }

    interface OnQuizFragmentListener {
        fun setPage(page: Int)
        fun getPage(): Int
        fun setFragmentTheme()
        fun showFragment(fragment: Fragment, isBackStack: Boolean)
        fun nextPage()
    }
}