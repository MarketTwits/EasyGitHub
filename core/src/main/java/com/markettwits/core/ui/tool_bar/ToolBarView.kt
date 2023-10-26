package com.markettwits.core.ui.tool_bar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import com.markettwits.core.R
import com.markettwits.core.databinding.WidgetToolbarBinding

class ToolBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : android.widget.Toolbar(context, attrs) {
    private val binding =
        WidgetToolbarBinding.inflate(LayoutInflater.from(context), this, false)
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addView(binding.root)
    }
     fun setUpToolbar(title: String, signOut: () -> Unit) {
        binding.run {
            root.title = title
            root.setTitleTextAppearance(
                context,
                R.style.TextAppearance_Widget_Toolbar_Title
            )
            root.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_sign_out){
                    signOut()
                }
              true
            }
        }
    }
    fun setUpToolbar(title: String, signOut: () -> Unit, back : () -> Unit) {
        binding.run {
            root.title = title
            root.setTitleTextAppearance(
                context,
                R.style.TextAppearance_Widget_Toolbar_Title
            )
            root.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            root.setNavigationOnClickListener {
                back()
            }
            root.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.action_sign_out) {
                    signOut()
                }
                true
            }
        }
    }
}