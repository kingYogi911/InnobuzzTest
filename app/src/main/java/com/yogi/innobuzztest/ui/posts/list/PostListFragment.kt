package com.yogi.innobuzztest.ui.posts.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yogi.innobuzztest.R
import com.yogi.innobuzztest.databinding.FragmentPostsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostListFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostListViewModel by viewModels()
    private val adapter = PostListAdapter { item ->
        val data = Bundle()
        data.putSerializable("item", item)
        findNavController().navigate(R.id.action_nav_posts_to_postDetailsFragment, data)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            rv.adapter = adapter
            root.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.root.isRefreshing = false
                    viewModel.refresh()
                }, 500)
            }
        }
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        viewModel.showProgress.observe(viewLifecycleOwner) {
            binding.apply {
                llProgress.isVisible = it != null
                tvMsg.text = it
                rv.isVisible = it == null
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.showError.collectLatest {
                    showError(it)
                }
            }
        }
    }

    private fun showError(it: Throwable) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(it.message)
            .setPositiveButton("Ok") { d, _ ->
                d.dismiss()
                Toast.makeText(requireContext(), "Swipe Down to Refresh", Toast.LENGTH_LONG).show()
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}