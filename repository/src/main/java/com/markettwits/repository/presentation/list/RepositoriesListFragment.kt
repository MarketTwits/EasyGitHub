package com.markettwits.repository.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.markettwits.core.ui.save_and_restore.mapToSaveState
import com.markettwits.core.ui.save_and_restore.parcelableArrayList
import com.markettwits.repository.R
import com.markettwits.repository.databinding.FragmentRepositoriesListBinding
import com.markettwits.repository.presentation.list.recyclerView.ItemAction
import com.markettwits.repository.presentation.list.recyclerView.RepositoriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list) {
    private var _binding: FragmentRepositoriesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<RepositoriesListViewModel.Base>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRepositoriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RepositoriesAdapter(object : ItemAction {
            override fun onClick(name: String, owner: String) {
                viewModel.toDetail(owner, name)
            }
            override fun retry() {
                viewModel.repositories()
            }
        })
        val state =
            savedInstanceState?.parcelableArrayList<RepositoriesUiState>(REPOSITORY_LIST_STATE_KEY)
        viewModel.restore(state ?: viewModel.save())
        binding.rvRepositoriesList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvRepositoriesList.adapter = adapter
        viewModel.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.toolbar.setUpSingle(getString(com.markettwits.core.R.string.repositories))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvRepositoriesList.adapter = null
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            REPOSITORY_LIST_STATE_KEY,
            mapToSaveState(viewModel.save())
        )
    }
    private companion object {
        const val REPOSITORY_LIST_STATE_KEY = "repository_list_state"
    }

}