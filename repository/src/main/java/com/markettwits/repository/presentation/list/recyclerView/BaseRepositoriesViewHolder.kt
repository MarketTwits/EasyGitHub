package com.markettwits.repository.presentation.list.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.markettwits.repository.databinding.LayoutErrorBinding
import com.markettwits.repository.databinding.LayoutLoadingBinding
import com.markettwits.repository.databinding.RepositoryElementBinding
import com.markettwits.repository.presentation.list.RepositoriesUiStateHandle

abstract class BaseRepositoriesViewHolder(view: View) :
    RecyclerView.ViewHolder(view), RepositoriesUiStateHandle {
     class Base(
        private val binding: RepositoryElementBinding,
         private val action: ItemAction
    ) : BaseRepositoriesViewHolder(binding.root), RepositoriesUiStateHandle {
        override fun success(name: String, description: String, language: String) {
            binding.rvElementRepoName.text = name
            binding.rvElementRepoLanguage.text = language
            binding.rvElementRepoDescription.text = description
        }
    }


      class Error(
        private val binding: LayoutErrorBinding,
        private val action: ItemAction
    ) : BaseRepositoriesViewHolder(binding.root), RepositoriesUiStateHandle {
        override fun error(icon: Int, title: Int, message: Int) {
            binding.ivError.setImageResource(icon)
            binding.tvLabelError.setText(title)
            binding.tvInfoError.setText(message)
            binding.retryButton.setOnClickListener {
                action.retry()
            }
        }

    }

     class Loading(
        private val binding: LayoutLoadingBinding,
    ) : BaseRepositoriesViewHolder(binding.root), RepositoriesUiStateHandle {
        override fun loading() {}
    }
}