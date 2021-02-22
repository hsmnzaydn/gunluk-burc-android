@file:JvmName("Utility")

package com.basefy.base_mvp.core_utility


import java.util.*

object GenericExtensions {
    /**Local bölgeyi static değişkende tutan kod*/
    val LOCALE_TR = Locale("tr", "TR")

    /**Kotlin'de listeye ekleme yapılabilmesini saglayan extension
     * @param item eklenmek istenen item
     * */
    fun <T> List<T>.add(item: T) = this.toMutableList().add(item)


    /**Kotlin'de listeye liste eklemesi saglayan extension
     * @param item eklenmek istenen item
     * */
    fun <T> List<T>.addAll(item: List<T>) = this.toMutableList().addAll(item)


    /**Kotlin'de listeden index'e item silen extension
     * @param index silinmek istenen itemin indexi
     * */
    fun <T> List<T>.removeAt(index: Int) = this.toMutableList().removeAt(index)


    /**Kotlin'de listeden item silen extension
     * @param item silinmek istenen item
     * */
    fun <T> List<T>.remove(item: T) = this.toMutableList().remove(item)

    /**Kotlin'de listeden liste silen extension
     * @param item silinmek istenen item
     * */
    fun <T> List<T>.removeAll(item: List<T>) = this.toMutableList().removeAll(item)


    /**Güvenli bir şekilde cast işlemi yapan fonksiyon*/
    inline fun <reified T> Any.safeCast(): T? = this as? T

}