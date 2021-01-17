package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.entity.asId

data class SubcategoryModel(val id: String,
                            val categoryId: String,
                            val name: String)

fun Subcategory<New>.toModel(newId: String) = SubcategoryModel(newId, categoryId, name)

fun Subcategory<Existing>.toModel() = SubcategoryModel(id.value, categoryId, name)

fun SubcategoryModel.toEntity() = Subcategory(id.asId(), categoryId, name)
