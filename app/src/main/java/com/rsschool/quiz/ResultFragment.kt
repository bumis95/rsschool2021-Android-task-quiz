package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding

private const val ARG_PARAM1 = "param1"

class ResultFragment : Fragment() {

    private var param1: ArrayList<Int>? = null
    private lateinit var listener: QuizFragment.OnQuizFragmentListener

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as QuizFragment.OnQuizFragmentListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getIntegerArrayList(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        binding.textView2.text = ResultMaker.getResult(param1!!)

        binding.ibShare.setOnClickListener {

        }

        binding.ibBack.setOnClickListener {
            listener.setPage(1)
            listener.showFragment(QuizFragment(), true)
        }

        binding.ibClose.setOnClickListener {
            requireActivity().finish()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: ArrayList<Int>) =
            ResultFragment().apply {
                arguments = bundleOf(ARG_PARAM1 to param1)
            }
    }
}