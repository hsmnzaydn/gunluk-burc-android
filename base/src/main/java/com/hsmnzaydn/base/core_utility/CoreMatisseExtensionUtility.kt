package com.hsmnzaydn.base.core_utility

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.ImageEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy

@Suppress("UNREACHABLE_CODE")
class Picasso4Engine : ImageEngine {

    /**Matisse imageEngine thumbnailleri çeken fonksiyon*/
    override fun loadThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable,
        imageView: ImageView,
        uri: Uri
    ) {
        /**Matisse'nin galerideki resimleri göstermesini sağlayan fonksiyon**/
        Glide.with(context).load(uri).placeholder(placeholder)
            .override(resize, resize)
            .centerCrop()
            .into(imageView)

    }

    /**Matisse imageEngine gif thumbnailleri çeken fonksiyon*/
    override fun loadGifThumbnail(
        context: Context, resize: Int, placeholder: Drawable, imageView: ImageView,
        uri: Uri
    ) {
        loadThumbnail(context, resize, placeholder, imageView, uri)
    }

    /**Matisse imageEngine resimleri çeken fonksiyon*/
    override fun loadImage(
        context: Context,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView,
        uri: Uri
    ) {
        /**Matisse'nin galerideki resimleri göstermesini sağlayan fonksiyon**/
        Glide.with(context).load(uri).override(resizeX, resizeY).priority(Priority.HIGH)
            .centerInside().into(imageView)
    }

    /**Matisse imageEngine resim giflerini çeken fonksiyon*/
    override fun loadGifImage(
        context: Context,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView,
        uri: Uri
    ) {
        loadImage(context, resizeX, resizeY, imageView, uri)
    }
    /**Matisse Gif desteğini saglayan fonksiyon*/
    override fun supportAnimatedGif(): Boolean {
        return true
    }

    object CommonMatisse {
        /**
         * Matisse ile galerimize erişmeye sağlayan statik fonksyion
         *
         * @param activity: Matisse'ye hangi activity'den erişildiğini belirten parametre
         * @param CODE: Yapılan işlemi onActivityResult'da karşılamak için gönderilen requestCode parametresi
         * @param photoRestcount: Aynı anda en fazla kaç adet resim seçilebilindiğini belirleten parametre
         *     bir deger verilmesse default olarak en fazla 1 resim seçilebilir
         * @param camera: Bu değer matisse kamera desteğini göstermeye yada gizlemeye yarar hiç birşey verilmezse eğer
         *  default olarak değer true gelir.
         *
         * */
        fun openMatisse(
            activity: Activity,
            CODE: Int,
            photoRestcount: Int = 1,
            camera: Boolean = true
        ) {
            try {
                Matisse.from(activity)
                    .choose(MimeType.of(MimeType.MP4, MimeType.JPEG, MimeType.PNG))
                    .capture(camera)
                    .captureStrategy(
                        CaptureStrategy(
                            false,
                            TODO("Buraya ResValue değeri yazılmalıdır Örn:com.basefy.projectName.fileprovider")
                        )
                    )
                    .countable(false)
                    .maxSelectable(photoRestcount)
                    .gridExpectedSize(12)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(Picasso4Engine())
                    .showSingleMediaType(true)
                    .forResult(CODE)
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
    }

}
