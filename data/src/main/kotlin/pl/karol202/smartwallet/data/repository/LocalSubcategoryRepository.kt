package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.IdDataSource
import pl.karol202.smartwallet.data.datasource.SubcategoryDataSource
import pl.karol202.smartwallet.data.model.SubcategoryModel
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.data.provider.DefaultNameProvider
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository

private const val ID_SUBJECT_EXPENSES = "subcategory_expenses"
private const val ID_SUBJECT_INCOMES = "subcategory_incomes"

class LocalSubcategoryRepository(private val subcategoryDataSource: SubcategoryDataSource,
                                 private val categoryRepository: CategoryRepository,
                                 private val idDataSource: IdDataSource,
                                 private val defaultNameProvider: DefaultNameProvider) : SubcategoryRepository
{
	private val othersIds = mapOf(
		Category.Type.EXPENSE to idDataSource.getStaticId(ID_SUBJECT_EXPENSES),
		Category.Type.INCOME to idDataSource.getStaticId(ID_SUBJECT_INCOMES)
	)

	override val allSubcategories = subcategoryDataSource.allSubcategories.map { it.map(this::toEntity) }

	override fun getOthersSubcategory(type: Category.Type) =
			getSubcategory(getOthersIdByType(type)).map { it ?: error("Others subcategory does not exist") }

	override fun getSubcategory(subcategoryId: String) =
			subcategoryDataSource.getSubcategory(subcategoryId).map { it?.let(this::toEntity) }

	override suspend fun addSubcategory(subcategory: Subcategory<New>) =
			subcategoryDataSource.addSubcategory(subcategory.toModel(idDataSource.createRandomId()))

	override suspend fun updateSubcategory(subcategory: Subcategory<Existing>)
	{
		val fixedSubcategory = when(val othersType = getOthersTypeById(subcategory.id.value))
		{
			null -> subcategory
			else -> fixOthersSubcategory(subcategory, othersType)
		}
		subcategoryDataSource.updateSubcategory(fixedSubcategory.toModel())
	}

	override suspend fun removeSubcategory(subcategoryId: String)
	{
		if(!isOthersCategory(subcategoryId)) subcategoryDataSource.removeSubcategory(subcategoryId)
	}

	override suspend fun ensureIntegrity() =
			Category.Type.values().forEach { ensureOthersSubcategoryIntegrity(it) }

	private suspend fun ensureOthersSubcategoryIntegrity(type: Category.Type)
	{
		val id = getOthersIdByType(type)
		val subcategory = getSubcategory(id).first()
		
		if(subcategory == null) subcategoryDataSource.addSubcategory(createDefaultOthersSubcategoryModel(id, type))
		else subcategoryDataSource.updateSubcategory(fixOthersSubcategory(subcategory, type).toModel())
	}

	private fun createDefaultOthersSubcategoryModel(id: String, type: Category.Type) = SubcategoryModel(
		id = id,
		categoryId = categoryRepository.getOthersCategoryId(type),
		name = defaultNameProvider.getDefaultOthersSubcategoryName(),
	)

	// Do not allow to change type
	private fun fixOthersSubcategory(subcategory: Subcategory<Existing>, type: Category.Type) = subcategory.copy(
		categoryId = categoryRepository.getOthersCategoryId(type)
	)

	private fun getOthersIdByType(type: Category.Type) = othersIds.getValue(type)

	private fun getOthersTypeById(id: String) = othersIds.entries.find { it.value == id }?.key

	private fun toEntity(model: SubcategoryModel) = model.toEntity(isOthers = isOthersCategory(model.id))

	private fun isOthersCategory(id: String) = id in othersIds.values
}
