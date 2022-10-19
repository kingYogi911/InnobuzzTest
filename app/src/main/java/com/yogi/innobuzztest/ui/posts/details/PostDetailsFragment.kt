package com.yogi.innobuzztest.ui.posts.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yogi.innobuzztest.databinding.FragmentPostDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private val viewModel: PostDetailsViewModel by viewModels()
    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner) {
            it?.also {
                binding.apply {
                    tvTitle.text = it.title
                    tvBody.text = it.body
                }
            }
        }
    }
}