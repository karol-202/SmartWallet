package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.AccountDataSource
import pl.karol202.smartwallet.data.datasource.DefaultAccountDataSource
import pl.karol202.smartwallet.data.datasource.IdDataSource
import pl.karol202.smartwallet.data.model.AccountModel
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.repository.AccountRepository

class LocalAccountRepository(private val accountDataSource: AccountDataSource,
                             private val defaultAccountDataSource: DefaultAccountDataSource,
                             private val idDataSource: IdDataSource) : AccountRepository
{
	override val allAccounts =
			accountDataSource.allAccounts.map { it.map(AccountModel::toEntity) }
	override val defaultAccount = combine(allAccounts, defaultAccountDataSource.defaultAccountId) { accounts, defaultId ->
		accounts.find { it.id.value == defaultId }
	}

	override fun getAccount(accountId: String) =
			accountDataSource.getAccount(accountId).map { it?.toEntity() }

	override suspend fun markAccountAsDefault(accountId: String) =
			defaultAccountDataSource.setDefaultAccountId(accountId)

	override suspend fun addAccount(account: Account<New>) =
			accountDataSource.addAccount(account.toModel(newId = idDataSource.createRandomId()))

	override suspend fun updateAccount(account: Account<Existing>) =
			accountDataSource.updateAccount(account.toModel())

	override suspend fun removeAccount(accountId: String)
	{
		if(accountId == defaultAccountDataSource.defaultAccountId.first()) return
		accountDataSource.removeAccount(accountId)
	}
}
