package com.markettwits.repository.presentation.detail.view

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.markettwits.repository.presentation.detail.RepositoryReadmeUiState
import com.markettwits.repository.presentation.detail.RepositoryUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInfoFragment : BaseDetailInfoFragment(R.layout.fragment_repositories_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val owner = arguments?.getString("owner") ?: ""
        val name = arguments?.getString("name") ?: ""

        viewModel.init(savedInstanceState == null, name, owner)
        binding.toolbar.setUpWithBack(name)
        viewModel.observeRetry(viewLifecycleOwner){
            viewModel.fetchRepositoryInfo(name, owner)
        }
    }

}