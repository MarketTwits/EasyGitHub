package com.markettwits.repository.presentation.list.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.markettwits.repository.databinding.LayoutErrorBinding
import com.markettwits.repository.databinding.LayoutLoadingBinding
import com.markettwits.repository.databinding.RepositoryElementBinding
import com.markettwits.repository.presentation.list.RepositoriesUiState


class RepositoriesAdapter(
    private val onItemClick: ItemAction
) : ListAdapter<RepositoriesUiState, BaseRepositoriesViewHolder>(RepoDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRepositoriesViewHolder {
        val base = RepositoryElementBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        val failed =
            LayoutErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val loading =
            LayoutLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = when (viewType) {
            0 -> BaseRepositoriesViewHolder.Base(base, onItemClick)
            1 -> BaseRepositoriesViewHolder.Error(failed, onItemClick)
            2 -> BaseRepositoriesViewHolder.Loading(loading)
            else -> BaseRepositoriesViewHolder.Loading(loading)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseRepositoriesViewHolder, position: Int) {
        currentList[position].show(holder)
    }
    override fun getItemViewType(position: Int): Int = when (currentList[position]) {
        is RepositoriesUiState.Success -> 0
        is RepositoriesUiState.Error -> 1
        is RepositoriesUiState.Loading -> 2
    }
}
interface ItemAction{
    fun onClick(item : RepositoriesUiState)
    fun retry()
}