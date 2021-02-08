package pl.karol202.smartwallet.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New

interface AccountRepository
{
	val allAccounts: Flow<List<Account<Existing>>>

	fun getAccount(accountId: String): Flow<Account<Existing>?>

	suspend fun addAccount(account: Account<New>)

	suspend fun updateAccount(account: Account<Existing>)

	suspend fun removeAccount(accountId: String)
}
