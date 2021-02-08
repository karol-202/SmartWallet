package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.AccountModel

interface AccountDataSource
{
    val allAccounts: Flow<List<AccountModel>>

    fun getAccount(accountId: String): Flow<AccountModel?>

    suspend fun addAccount(account: AccountModel)

    suspend fun updateAccount(account: AccountModel)

    suspend fun removeAccount(accountId: String)
}
