package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.AccountDataSource
import pl.karol202.smartwallet.data.datasource.IdDataSource
import pl.karol202.smartwallet.data.model.AccountModel
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.repository.AccountRepository

class LocalAccountRepository(private val accountDataSource: AccountDataSource,
                             private val idDataSource: IdDataSource) : AccountRepository
{
	override val allAccounts =
			accountDataSource.allAccounts.map { it.map(AccountModel::toEntity) }

	override fun getAccount(accountId: String) =
			accountDataSource.getAccount(accountId).map { it?.toEntity() }

	override suspend fun addAccount(account: Account<New>) =
			accountDataSource.addAccount(account.toModel(newId = idDataSource.createRandomId()))

	override suspend fun updateAccount(account: Account<Existing>) =
			accountDataSource.updateAccount(account.toModel())

	override suspend fun removeAccount(accountId: String) =
			accountDataSource.removeAccount(accountId)
}
