package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.asId

data class CategoryModel(val id: String,
                         val name: String,
                         val type: Type)
{
	enum class Type
	{
		EXPENSE, INCOME
	}
}

fun Category<New>.toModel(newId: String) = CategoryModel(newId, name, type.toModel())

fun Category<Existing>.toModel() = CategoryModel(id.value, name, type.toModel())

fun Category.Type.toModel() = when(this)
{
	Category.Type.EXPENSE -> CategoryModel.Type.EXPENSE
	Category.Type.INCOME -> CategoryModel.Type.INCOME
}

fun CategoryModel.toEntity(isOthers: Boolean) = Category(id.asId(), name, type.toEntity(), isOthers)

fun CategoryModel.Type.toEntity() = when(this)
{
	CategoryModel.Type.EXPENSE -> Category.Type.EXPENSE
	CategoryModel.Type.INCOME -> Category.Type.INCOME
}
