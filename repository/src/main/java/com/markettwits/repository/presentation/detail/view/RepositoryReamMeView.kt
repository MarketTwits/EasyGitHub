package com.markettwits.repository.presentation.detail.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.Target
import com.markettwits.repository.databinding.LayoutRepositoryReadMeBinding
import com.markettwits.repository.presentation.detail.MarkwonConfig
import com.markettwits.repository.presentation.detail.RepositoryInfoViewModel
import com.markettwits.repository.presentation.detail.RepositoryReadmeUiState
import com.markettwits.repository.presentation.detail.RepositoryReadmeUiStateHandle
import com.markettwits.repository.presentation.detail.RepositoryUiState
import io.noties.markwon.Markwon
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin


class RepositoryReamMeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), RepositoryReadmeUiStateHandle {
    private val markwon = MarkwonConfig.Base(context).build()
    private val binding =
        LayoutRepositoryReadMeBinding.inflate(LayoutInflater.from(context), this, false)
    private val root by lazy { findFragment<DetailInfoFragment>() }
    private val viewModel by lazy { root.viewModels<RepositoryInfoViewModel.Base>() }


    init {
        isSaveEnabled = true
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addView(binding.root)

        viewModel.value.observeReadme(findViewTreeLifecycleOwner()!!) {
            it.show(this)
        }
    }
    override fun loading() {
        binding.progressCircularReadme.visibility = View.VISIBLE
    }

    override fun success(message: String) {
        binding.progressCircularReadme.visibility = View.GONE
        markwon.setMarkdown(binding.tvReadme, message)
    }
    override fun empty(message: Int) {
        binding.progressCircularReadme.visibility = View.GONE
        binding.tvReadme.setText(message)
    }

}
