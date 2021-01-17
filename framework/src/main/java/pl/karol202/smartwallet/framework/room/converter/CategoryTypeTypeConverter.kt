package pl.karol202.smartwallet.framework.room.converter

import androidx.room.TypeConverter
import pl.karol202.smartwallet.framework.room.entity.CategoryRoomEntity

object CategoryTypeTypeConverter
{
	@JvmStatic
	@TypeConverter
	fun fromCategoryType(categoryType: CategoryRoomEntity.Type) = categoryType.name

	@JvmStatic
	@TypeConverter
	fun toCategoryType(value: String) = enumValueOf<CategoryRoomEntity.Type>(value)
}
