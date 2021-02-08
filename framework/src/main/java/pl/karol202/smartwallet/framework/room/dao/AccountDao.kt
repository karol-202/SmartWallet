package pl.karol202.smartwallet.framework.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.framework.room.entity.AccountRoomEntity

@Dao
interface AccountDao
{
	@Insert
	suspend fun insert(account: AccountRoomEntity)

	@Update
	suspend fun update(account: AccountRoomEntity)

	@Query("DELETE FROM accounts WHERE id = :id")
	suspend fun delete(id: String)

	@Query("SELECT * FROM accounts")
	fun getAll(): Flow<List<AccountRoomEntity>>

	@Query("SELECT * FROM accounts WHERE id = :id")
	fun getById(id: String): Flow<AccountRoomEntity?>
}
