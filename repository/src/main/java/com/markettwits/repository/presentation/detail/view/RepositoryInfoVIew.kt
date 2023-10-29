package com.markettwits.repository.presentation.detail.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.markettwits.repository.databinding.LayoutErrorBinding
import com.markettwits.repository.databinding.LayoutLoadingBinding
import com.markettwits.repository.databinding.LayoutRepositoryDetailInfoBinding
import com.markettwits.repository.presentation.detail.RepositoryInfoViewModel
import com.markettwits.repository.presentation.detail.RepositoryUiStateHandle

class RepositoryInfoVIew @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), RepositoryUiStateHandle {
    private val baseBinding =
        LayoutRepositoryDetailInfoBinding.inflate(LayoutInflater.from(context), this, false)
    private val loadingBinding = LayoutLoadingBinding.inflate(LayoutInflater.from(context), this, false)
    private val errorBinding = LayoutErrorBinding.inflate(LayoutInflater.from(context), this, false)
    private val root by lazy { findFragment<DetailInfoFragment>() }
    private val viewModel by lazy { root.viewModels<RepositoryInfoViewModel.Base>() }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel.value.observe(findViewTreeLifecycleOwner()!!){
            it.show(this)
        }
    }

    override fun success(
        name: String,
        htmlUrl: String,
        license: String,
        forks: Int,
        stars: Int,
        watchers: Int
    ) {
        changeState(baseBinding)
        with(baseBinding){
            tvLink.text = htmlUrl
            tvLicense.text = license
            tvForks.text = forks.toString()
            tvStars.text = stars.toString()
            tvWatchers.text = watchers.toString()
            tvLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl))
                context.startActivity(intent)
            }
        }
    }

    override fun loading() {
        changeState(loadingBinding)
    }

    override fun error(icon: Int, title: Int, message: Int) {
        changeState(errorBinding)
        with(errorBinding){
            tvLabelError.setText(title)
            tvInfoError.setText(message)
            ivError.setImageResource(icon)
            retryButton.setOnClickListener {
                viewModel.value.retry()
            }
        }
    }
    private fun changeState(binding : androidx.viewbinding.ViewBinding){
        removeAllViews()
        addView(binding.root)
    }
}