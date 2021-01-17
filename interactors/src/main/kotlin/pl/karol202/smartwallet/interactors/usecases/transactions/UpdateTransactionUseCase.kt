package pl.karol202.smartwallet.interactors.usecases.transactions

import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class UpdateTransactionUseCase(override val function: suspend (Transaction) -> Unit) : SuspendUseCase1<Transaction, Unit>

fun updateTransactionUseCaseFactory(transactionRepository: TransactionRepository) = UpdateTransactionUseCase { transaction ->
	transactionRepository.updateTransaction(transaction)
}
