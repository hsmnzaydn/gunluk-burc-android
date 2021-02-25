import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities.HoroscopeResponse
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.Horoscope
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.HoroscopeListItem
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.mapper.toDescription

fun Horoscope.toHoroscopeListItem(): HoroscopeListItem = HoroscopeListItem(
   this
)

fun HoroscopeResponse.toHoroscope(): Horoscope = Horoscope(
    "https://"+imagePath,
    id,
    horoscopeName,
    descriptions = this.descriptionResponse?.map {
        it.toDescription()
    }?: emptyList()
)