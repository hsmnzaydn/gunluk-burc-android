package com.hsmnzaydn.base.core_utility

import com.hsmnzaydn.base.core_utility.GenericExtensions.LOCALE_TR
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val DateStringPattern = "yyyy-MM-dd'T'HH:mm:ss"
const val UIDateStringPattern = "dd MMMM HH:mm"


/**String olarak gelen tarihi istenilen gün, ay formatına çevirir*/
fun String?.toFormattedDate(): String {
    if (this == null)
        return ""
    //this format createdDate we want
    val mSDF = SimpleDateFormat(UIDateStringPattern, LOCALE_TR)
    //this format createdDate actully present
    val formatter = SimpleDateFormat(DateStringPattern, LOCALE_TR)

    var result = ""
    try {
        //  result = mSDF.format(formatter.parse(this)!!)
        val date = formatter.parse(this)
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.HOUR_OF_DAY, 1)
        result = mSDF.format(cal.time)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return result
}

/**String olarak gelen tarihi timestamp formatına çevirir.*/
fun String?.toTimeStampLong(): Long {
    var timeStamp: Long = -1
    try {
        timeStamp = SimpleDateFormat(DateStringPattern, LOCALE_TR).parse(this)!!.time / 1000
    } catch (e: ParseException) {
        e.printStackTrace()
    } finally {
        return timeStamp

    }
}

/**Anlık olarak sistem timestampini alan fonksiyon*/
fun currentTime() = System.currentTimeMillis()