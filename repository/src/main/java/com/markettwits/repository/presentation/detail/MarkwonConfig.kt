package com.markettwits.repository.presentation.detail

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.Target
import io.noties.markwon.Markwon
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin

interface MarkwonConfig {
    fun build() : Markwon
    abstract class Abstract(private val context: Context) : MarkwonConfig{
        protected fun image() : GlideImagesPlugin{
            val glide = object : GlideImagesPlugin.GlideStore {
                override fun load(drawable: AsyncDrawable): RequestBuilder<Drawable> {
                    return Glide.with(context)
                        .load(drawable.destination)
                }
                override fun cancel(target: Target<*>) {
                    Glide.with(context).clear(target);
                }
            }
            return GlideImagesPlugin.create(glide)
        }
        protected fun link() = LinkifyPlugin.create()
    }
    class Base(private val context: Context) : Abstract(context){
        override fun build() = Markwon.builder(context)
                .usePlugin(image())
                .usePlugin(link())
                .build()
        }
}