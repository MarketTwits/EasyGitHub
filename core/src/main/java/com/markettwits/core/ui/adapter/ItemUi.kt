package com.markettwits.core.ui.adapter

import com.markettwits.core.ui.adapter.MyView

/**
 * @author Asatryan on 01.06.2022
 */
interface ItemUi {

    fun type(): Int

    fun show(vararg views: MyView)

    fun id(): String

    fun content(): String
}