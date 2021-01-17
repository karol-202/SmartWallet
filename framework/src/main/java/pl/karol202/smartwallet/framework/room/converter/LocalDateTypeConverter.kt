package pl.karol202.smartwallet.framework.room.converter

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateTypeConverter
{
	@JvmStatic
	@TypeConverter
	fun fromLocalDate(localDate: LocalDate) = localDate.toString()

	@JvmStatic
	@TypeConverter
	fun toLocalDate(value: String): LocalDate = LocalDate.parse(value)
}
