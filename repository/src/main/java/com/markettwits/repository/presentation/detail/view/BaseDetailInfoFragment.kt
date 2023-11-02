package com.markettwits.repository.presentation.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.markettwits.core.ui.save_and_restore.parcelable
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
        val state = savedInstanceState?.parcelable<RepositoryUiState>(REPOSITORY_UI_STATE_KEY)
        val readmeState = savedInstanceState?.parcelable<RepositoryReadmeUiState>(REPOSITORY_README_STATE_KEY)
        viewModel.restore(state ?: RepositoryUiState.Loading)
        viewModel.restoreReadme(readmeState ?: RepositoryReadmeUiState.Loading)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(REPOSITORY_UI_STATE_KEY, viewModel.currentState())
        outState.putParcelable(REPOSITORY_README_STATE_KEY, viewModel.currentReadmeState())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private companion object{
        const val REPOSITORY_UI_STATE_KEY = "ui-state"
        const val REPOSITORY_README_STATE_KEY = "readme-state"
    }
}