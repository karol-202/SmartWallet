package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.NewTransactionModel
import pl.karol202.smartwallet.data.model.TransactionModel
import pl.karol202.smartwallet.domain.entity.Transaction

interface TransactionDataSource
{
    val allTransactions: Flow<List<TransactionModel>>

    fun getTransaction(transactionId: Long): Flow<TransactionModel>

    suspend fun addTransaction(transaction: NewTransactionModel)

    suspend fun updateTransaction(transaction: TransactionModel)

    suspend fun removeTransaction(transactionId: Long)
}
