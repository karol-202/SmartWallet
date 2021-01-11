package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.NewTransactionModel
import pl.karol202.smartwallet.data.model.TransactionModel

interface TransactionDataSource
{
    val allTransactions: Flow<List<TransactionModel>>

    suspend fun addTransaction(transaction: NewTransactionModel)

    suspend fun updateTransaction(transaction: TransactionModel)

    suspend fun removeTransaction(transactionId: Long)
}
