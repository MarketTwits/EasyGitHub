package com.markettwits.core.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Asatryan on 01.06.2022
 */
abstract class GenericViewHolder<T : ItemUi>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)
}