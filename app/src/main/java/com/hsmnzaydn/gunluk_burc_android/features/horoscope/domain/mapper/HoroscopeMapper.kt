import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities.HoroscopeResponse
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.HoroscopeListItem

fun HoroscopeResponse.toHoroscopeListItem(): HoroscopeListItem = HoroscopeListItem(
    imagePath,
    id,
    horoscopeName
)