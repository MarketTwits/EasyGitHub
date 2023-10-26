package com.markettwits.repository.presentation.list.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.markettwits.repository.presentation.list.RepositoriesUiState


class RepoDiffUtilCallback : DiffUtil.ItemCallback<RepositoriesUiState>() {
    override fun areItemsTheSame(oldItem: RepositoriesUiState, newItem: RepositoriesUiState): Boolean {
        //todo
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RepositoriesUiState, newItem: RepositoriesUiState): Boolean {
        return oldItem == newItem
    }

}