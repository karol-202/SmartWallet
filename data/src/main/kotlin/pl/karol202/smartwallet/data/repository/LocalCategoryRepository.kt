package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.CategoryDataSource
import pl.karol202.smartwallet.data.datasource.IdDataSource
import pl.karol202.smartwallet.data.model.CategoryModel
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.data.provider.DefaultNameProvider
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.repository.CategoryRepository

private const val ID_SUBJECT_EXPENSES = "expenses"
private const val ID_SUBJECT_INCOMES = "incomes"

class LocalCategoryRepository(private val categoryDataSource: CategoryDataSource,
                              private val idDataSource: IdDataSource,
                              private val defaultNameProvider: DefaultNameProvider) : CategoryRepository
{
	private val othersIds = mapOf(
		Category.Type.EXPENSE to idDataSource.getStaticId(ID_SUBJECT_EXPENSES),
		Category.Type.INCOME to idDataSource.getStaticId(ID_SUBJECT_INCOMES)
	)

	override val allCategories =
			categoryDataSource.allCategories.map { it.map(this::toEntity) }

	override fun getOthersCategory(type: Category.Type) =
			getCategory(getOthersIdByType(type)).map { it ?: error("Others category does not exist") }

	override fun getCategory(categoryId: String) =
			categoryDataSource.getCategory(categoryId).map { it?.let(this::toEntity) }

	override suspend fun addCategory(category: Category<New>) =
			categoryDataSource.addCategory(category.toModel(newId = idDataSource.createRandomId()))

	override suspend fun updateCategory(category: Category<Existing>)
	{
		val fixedCategory = when(val othersType = getOthersTypeById(category.id.value))
		{
			null -> category
			else -> fixOthersCategory(category, othersType)
		}
		categoryDataSource.updateCategory(fixedCategory.toModel())
	}

	override suspend fun removeCategory(categoryId: String)
	{
		if(isRemovable(categoryId)) categoryDataSource.removeCategory(categoryId)
	}

	override suspend fun ensureIntegrity() =
			Category.Type.values().forEach { ensureOthersCategoryIntegrity(it) }

	private suspend fun ensureOthersCategoryIntegrity(type: Category.Type)
	{
		val id = getOthersIdByType(type)
		val category = getCategory(id).first()
		when
		{
			category == null -> categoryDataSource.addCategory(createDefaultOthersCategoryModel(id, type))
			category.type != type -> categoryDataSource.updateCategory(fixOthersCategory(category, type).toModel())
		}
	}

	private fun createDefaultOthersCategoryModel(id: String, type: Category.Type) = CategoryModel(
		id = id,
		name = defaultNameProvider.getDefaultOthersCategoryName(),
		type = type.toModel()
	)

	// Do not allow to change type
	private fun fixOthersCategory(category: Category<Existing>, type: Category.Type) = category.copy(type = type)

	private fun getOthersIdByType(type: Category.Type) = othersIds.getValue(type)

	private fun getOthersTypeById(id: String) = othersIds.entries.find { it.value == id }?.key

	private fun toEntity(model: CategoryModel) = model.toEntity(removable = isRemovable(model.id))

	private fun isRemovable(id: String) = id !in othersIds.values
}
