package pl.karol202.smartwallet.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.smartwallet.data.model.CategoryModel

@Entity(tableName = "categories")
data class CategoryRoomEntity(@PrimaryKey val id: String,
                              val name: String,
                              val type: Type)
{
	enum class Type
	{
		EXPENSE, INCOME
	}
}

fun CategoryModel.toRoomEntity() = CategoryRoomEntity(id, name, type.toRoomEntity())

fun CategoryModel.Type.toRoomEntity() = when(this)
{
	CategoryModel.Type.EXPENSE -> CategoryRoomEntity.Type.EXPENSE
	CategoryModel.Type.INCOME -> CategoryRoomEntity.Type.INCOME
}

fun CategoryRoomEntity.toModel() = CategoryModel(id, name, type.toModel())

fun CategoryRoomEntity.Type.toModel() = when(this)
{
	CategoryRoomEntity.Type.EXPENSE -> CategoryModel.Type.EXPENSE
	CategoryRoomEntity.Type.INCOME -> CategoryModel.Type.INCOME
}
