package pl.karol202.smartwallet.framework.datasource

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.AccountDataSource
import pl.karol202.smartwallet.data.model.AccountModel
import pl.karol202.smartwallet.framework.room.dao.AccountDao
import pl.karol202.smartwallet.framework.room.entity.AccountRoomEntity
import pl.karol202.smartwallet.framework.room.entity.toModel
import pl.karol202.smartwallet.framework.room.entity.toRoomEntity

class RoomAccountDataSource(private val accountDao: AccountDao) : AccountDataSource
{
	override val allAccounts = accountDao.getAll().map { it.map(AccountRoomEntity::toModel) }

	override fun getAccount(accountId: String) =
			accountDao.getById(accountId).map { it?.toModel() }

	override suspend fun addAccount(account: AccountModel) =
			accountDao.insert(account.toRoomEntity())

	override suspend fun updateAccount(account: AccountModel) =
			accountDao.update(account.toRoomEntity())

	override suspend fun removeAccount(accountId: String) =
			accountDao.delete(accountId)
}
