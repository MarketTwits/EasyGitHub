package com.markettwits.repository.presentation.list.recyclerView

import android.graphics.Color
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
        override fun success(
            name: String,
            owner : String,
            description: String,
            language: String,
            languageColor: String
        ) {
            with(binding) {
                binding.root.setOnClickListener {
                    action.onClick(name, owner)
                }
                rvElementRepoName.text = name
                rvElementRepoDescription.text = description
                rvElementRepoLanguage.let {
                    it.text = language
                    it.setTextColor(Color.parseColor(languageColor))
                }
                if (description.isEmpty())
                    rvElementRepoDescription.visibility = View.GONE
                else
                    rvElementRepoDescription.visibility = View.VISIBLE
                    this.rvElementRepoDescription.text = description
            }

        }
    }


    class Error(
        private val binding: LayoutErrorBinding,
        private val action: ItemAction
    ) : BaseRepositoriesViewHolder(binding.root), RepositoriesUiStateHandle {
        override fun error(icon: Int, title: Int, message: Int) {
            with(binding) {
                ivError.setImageResource(icon)
                tvLabelError.setText(title)
                tvInfoError.setText(message)
                retryButton.setOnClickListener {
                    action.retry()
                }
            }
        }

    }

    class Loading(
        private val binding: LayoutLoadingBinding,
    ) : BaseRepositoriesViewHolder(binding.root), RepositoriesUiStateHandle {
        override fun loading() {}
    }
}