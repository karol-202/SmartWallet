package pl.karol202.smartwallet.framework.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.framework.room.entity.SubcategoryRoomEntity

@Dao
interface SubcategoryDao
{
	@Insert
	suspend fun insert(subcategory: SubcategoryRoomEntity)

	@Update
	suspend fun update(subcategory: SubcategoryRoomEntity)

	@Query("DELETE FROM subcategories WHERE id = :id")
	suspend fun delete(id: String)

	@Query("UPDATE subcategories SET categoryId = :destinationCategoryId WHERE categoryId = :sourceCategoryId")
	suspend fun moveBetweenCategories(sourceCategoryId: String, destinationCategoryId: String)

	@Query("SELECT * FROM subcategories")
	fun getAll(): Flow<List<SubcategoryRoomEntity>>

	@Query("SELECT * FROM subcategories WHERE id = :id")
	fun getById(id: String): Flow<SubcategoryRoomEntity?>
}
