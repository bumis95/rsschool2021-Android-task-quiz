package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding

private const val ARG_RESULT = "ARG_RESULT"

class ResultFragment : Fragment() {

    private var list: ArrayList<Int> = arrayListOf()
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
            list = it.getIntegerArrayList(ARG_RESULT) as ArrayList<Int>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        binding.tvResult.text = ResultMaker.getPercent(list)

        binding.ibShare.setOnClickListener {
            sendResult()
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

    private fun sendResult() {
        val message = ResultMaker.getResult(list)
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
            type = "text/plain"
        }
        val intentChooser = Intent.createChooser(intent, null)
        startActivity(intentChooser)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: ArrayList<Int>) =
            ResultFragment().apply {
                arguments = bundleOf(ARG_RESULT to param1)
            }
    }
}