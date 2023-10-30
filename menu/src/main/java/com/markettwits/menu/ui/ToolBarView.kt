package com.markettwits.menu.ui

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.markettwits.core.R
import com.markettwits.menu.databinding.WidgetToolbarBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ToolBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : android.widget.Toolbar(context, attrs) {
    private lateinit var root : Fragment
    private val binding =
        WidgetToolbarBinding.inflate(LayoutInflater.from(context), this, false)
    private lateinit var viewModel: ToolBarViewModel.Base

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addView(binding.root)
        root = findFragment()
        viewModel  = root.viewModels<ToolBarViewModel.Base>().value
    }
     fun setUpSingle(title: String) {
        binding.run {
            root.title = title
            root.setTitleTextAppearance(
                context,
                R.style.TextAppearance_Widget_Toolbar_Title
            )
            root.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_sign_out){
                    viewModel.signOut()
                }
              true
            }
        }
    }
    fun setUpWithBack(title : String) {
        binding.run {
            root.title = title
            root.setTitleTextAppearance(
                context,
                R.style.TextAppearance_Widget_Toolbar_Title
            )
            root.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            root.setNavigationOnClickListener {
                viewModel.goBack()
            }
            root.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.action_sign_out) {
                    viewModel.signOut()
                }
                true
            }
        }
    }
}