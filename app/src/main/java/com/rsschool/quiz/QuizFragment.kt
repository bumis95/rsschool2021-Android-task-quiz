package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.rsschool.quiz.databinding.FragmentQuizBinding

private const val ARG_PARAM1 = "param1"
private const val MAX_QUESTIONS = 3

class QuizFragment : Fragment() {

    private var param1: String? = null

    private var listener: OnQuizFragmentListener? = null

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnQuizFragmentListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("QUIZ_FRAGMENT", "onCreate")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        Log.d("QUIZ_FRAGMENT", "onCreateView")
        initButtons()
        setToolbarTitle()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.nextButton.isEnabled = true
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
            listener?.incFragmentCount()
            val page = listener?.getFragmentCount()
            requireActivity().supportFragmentManager.commit {
                Log.d("QUIZ_FRAGMENT", "page=$page")
                replace(R.id.fragmentContainerView, newInstance(), "f$page")
                addToBackStack("f$page")
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

    companion object {

        @JvmStatic
        fun newInstance() =
            QuizFragment().apply {
                arguments = bundleOf(
                    ARG_PARAM1 to param1,
                )
            }
    }
}