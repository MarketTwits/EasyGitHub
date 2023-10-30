package com.markettwits.repository.presentation.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.findFragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import com.markettwits.repository.R
import com.markettwits.repository.databinding.FragmentDetailInfoBinding
import com.markettwits.repository.presentation.detail.RepositoryInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInfoFragment : Fragment(R.layout.fragment_repositories_list) {
    private var _binding: FragmentDetailInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RepositoryInfoViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //todo handle process death option
        val owner = arguments?.getString("owner") ?: ""
        val name = arguments?.getString("name") ?: ""
        if (savedInstanceState == null){
            viewModel.fetchRepositoryInfo(name, owner)
        }
        binding.toolbar.setUpWithBack(name)
        viewModel.observeRetry(viewLifecycleOwner){
            viewModel.fetchRepositoryInfo(name, owner)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}