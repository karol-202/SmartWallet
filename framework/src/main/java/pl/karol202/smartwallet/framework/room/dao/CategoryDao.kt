package pl.karol202.smartwallet.framework.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.framework.room.entity.CategoryRoomEntity
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity

@Dao
interface CategoryDao
{
	@Insert
	suspend fun insert(category: CategoryRoomEntity)

	@Update
	suspend fun update(category: CategoryRoomEntity)

	@Query("DELETE FROM categories WHERE id = :id")
	suspend fun delete(id: String)

	@Query("SELECT * FROM categories")
	fun getAll(): Flow<List<CategoryRoomEntity>>

	@Query("SELECT * FROM categories WHERE id = :id")
	fun getById(id: String): Flow<CategoryRoomEntity>
}
