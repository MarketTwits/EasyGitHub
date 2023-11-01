package com.markettwits.repository.presentation.detail.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.markettwits.repository.databinding.FragmentDetailInfoBinding
import com.markettwits.repository.presentation.detail.RepositoryInfoViewModel
import com.markettwits.repository.presentation.detail.RepositoryReadmeUiState
import com.markettwits.repository.presentation.detail.RepositoryUiState

abstract class BaseDetailInfoFragment(private val layoutResId : Int) : Fragment(layoutResId) {

    protected var _binding: FragmentDetailInfoBinding? = null
    protected val binding get() = _binding!!
    protected val viewModel by viewModels<RepositoryInfoViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val state = savedInstanceState?.getParcelable("ui-state", RepositoryUiState::class.java)
            val readmeState = savedInstanceState?.getParcelable("readme-state", RepositoryReadmeUiState::class.java)
            viewModel.restore(state ?: RepositoryUiState.Loading)
            viewModel.restoreReadme(readmeState ?: RepositoryReadmeUiState.Loading)
        }else{
            val state = savedInstanceState?.getParcelable<RepositoryUiState>("ui-state")
            val readmeState = savedInstanceState?.getParcelable<RepositoryReadmeUiState>("readme-state")
            viewModel.restore(state ?: RepositoryUiState.Loading)
            viewModel.restoreReadme(readmeState ?: RepositoryReadmeUiState.Loading)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("ui-state", viewModel.currentState())
        outState.putParcelable("readme-state", viewModel.currentReadmeState())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}