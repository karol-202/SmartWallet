package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow

interface DefaultAccountDataSource
{
    val defaultAccountId: Flow<String?>

    fun setDefaultAccountId(accountId: String?)
}
