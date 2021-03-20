package com.hsmnzaydn.base.core_utility

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ContentLoadingProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.File


class CoreImageloaderUtility {


    companion object {

        /**
         * Verilen linki cache ile indirmek için kullanılır
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param url: Image linki
         * @param imageView: Image linkinin set edildiği imageview
         */
        fun imageLoaderWithCache(
            activity: Context,
            url: String? = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            imageView: ImageView
        ) {
            Glide.with(activity)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }


        /**
         * Verilen linki cache ile indirmek için kullanılır ve indirilen resmi callback olarak geri verir
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param url: Image linki
         * @param imageView: Image linkinin set edildiği imageview
         * @param listener: İndirilen imagenin drawable olarak verilmesi için kullanılır
         */
        fun imageLoaderWithCacheWithListener(
            activity: Activity,
            url: String? = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            imageView: ImageView,
            listener: RequestListener<Drawable>
        ) {
            Glide.with(activity)
                .load(url)
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }


        /**
         * Verilen linki cache ile indirmek için kullanılır ve indirilen resmi verilen imageviewın boyutuna göre resize eder
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param url: Image linki
         * @param imageView: Image linkinin set edildiği imageview
         */
        fun imageLoaderWithCacheFit(
            activity: Context,
            url: String? = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            imageView: ImageView
        ) {


            Glide.with(activity)
                .load(url)
                .fitCenter()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)

        }

        /**
         * Verilen linki cache ile indirmek için kullanılır ve indirilen resmin indirilme süresine kadar loading gösterir
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param url: Image linki
         * @param imageView: Image linkinin set edildiği imageview
         * @param progressBar: Image indirilene kadar gösterilmek istenen progress bar
         */
        fun imageLoaderWithCacheFitWithLoading(
            activity: Activity,
            url: String? = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            imageView: ImageView,
            progressBar: ContentLoadingProgressBar
        ) {


            progressBar.visibility = View.VISIBLE

            Glide.with(activity)
                .load(url)
                .fitCenter()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                })
                .into(imageView)


        }

        /**
         * Verilen linki cache ile indirmek için kullanılır ve indirilen resmin köşelerini ovarlaştırmak için kullanılır
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param url: Image linki
         * @param imageView: Image linkinin set edildiği imageview
         * @param radius: İndirilen imagenin köşelerini ovarlaklaştırmak için kullanılır
         */
        fun imageLoaderWithCacheFitCornerRadius(
            activity: Activity,
            url: String? = "",
            imageView: ImageView,
            radius: Int? = 15
        ) {
            var requestOptions = RequestOptions()
            requestOptions =
                requestOptions.transform(CenterCrop(), RoundedCorners(
                    dpToPx(
                        activity,
                        radius!!
                    )
                ))


            Glide.with(activity)
                .load(url)
                .fitCenter()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions)
                .into(imageView)


        }

        /**
         * File'den çekilen resmin imageviewa verilmesi için kullanılır
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param file: Image dosyası
         * @param imageView: Image linkinin set edildiği imageview
         */
        fun imageLoaderWithCacheFitFile(
            activity: Activity,
            file: File?,
            imageView: ImageView
        ) {

            Glide.with(activity)
                .load(file)
                .into(imageView)
        }

        /**
         * Verilen linki cache ile indirmek için kullanılır ve indirilen resmin indirilme süresine kadar loading gösterir ve indirilen imageviewı circle yapar
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param url: Image linki
         * @param imageView: Image linkinin set edildiği imageview
         * @param progressBar: Image indirilene kadar gösterilmek istenen progress bar
         */
        fun imageLoaderWithCacheFitWithLoadingWithCircle(
            activity: Activity,
            url: String? = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            imageView: ImageView,
            progressBar: ContentLoadingProgressBar
        ) {


            progressBar.visibility = View.VISIBLE

            Glide.with(activity)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .fitCenter()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                })
                .into(imageView)


        }

        /**
         * Verilen linki cache ile indirmek için kullanılır ve indirilen resmin indirilme süresine kadar loading gösterir ve indirilen imageviewı resize eder
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param url: Image linki
         * @param imageView: Image linkinin set edildiği imageview
         * @param width: Gösterilecek imagenin genişliği
         * @param height: Gösterilecek imagenin yüksekliği
         * @param progressBar: Image indirilene kadar gösterilmek istenen progress bar
         */
        fun imageLoadWithCacheFitResize(
            activity: Activity,
            url: String? = "",
            imageView: ImageView,
            width: Int? = 600,
            height: Int? = 350,
            progressBar: ContentLoadingProgressBar

        ) {

            progressBar.visibility = View.VISIBLE

            Glide.with(activity)
                .load(url)
                .fitCenter()
                .override(width!!, height!!)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                })
                .into(imageView)
        }

        /**
         * Verilen linki cache ile indirmek için kullanılır ve indirilen resmin indirilme süresine kadar loading gösterir
         * @param activity: Image için çekilen verinin set edildiği activity
         * @param url: Image linki
         * @param imageView: Image linkinin set edildiği imageview
         */
        fun imageLoaderWithCacheFitWithCircle(
            activity: Activity,
            url: String? = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            imageView: ImageView
        ) {


            Glide.with(activity)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .into(imageView)

        }


    }
}