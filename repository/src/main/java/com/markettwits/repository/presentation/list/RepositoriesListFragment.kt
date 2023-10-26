package com.markettwits.repository.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.markettwits.repository.databinding.FragmentRepositoriesListBinding
import com.markettwits.repository.presentation.list.recyclerView.ItemAction
import com.markettwits.repository.presentation.list.recyclerView.RepositoriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesListFragment : Fragment(com.markettwits.repository.R.layout.fragment_repositories_list) {
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
            override fun onClick(item: RepositoriesUiState) {

            }
            override fun retry() {
                viewModel.repositories()
            }
        })
        binding.rvRepositoriesList.adapter = adapter
        viewModel.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.toolbar.setUpToolbar("Repositories"){
            viewModel.singOut()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvRepositoriesList.adapter = null
        _binding = null
    }

}