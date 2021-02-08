package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.TransactionModel

interface TransactionDataSource
{
    val allTransactions: Flow<List<TransactionModel>>

    fun getTransaction(transactionId: String): Flow<TransactionModel?>

    suspend fun addTransaction(transaction: TransactionModel)

    suspend fun updateTransaction(transaction: TransactionModel)

    suspend fun removeTransaction(transactionId: String)

    suspend fun moveTransactionsBetweenSubcategories(sourceSubcategoryId: String, destinationSubcategoryId: String)
}
