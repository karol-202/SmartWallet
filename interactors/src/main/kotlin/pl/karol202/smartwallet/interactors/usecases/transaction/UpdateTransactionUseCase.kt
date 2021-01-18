package pl.karol202.smartwallet.interactors.usecases.transaction

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class UpdateTransactionUseCase(override val function: suspend (Transaction<Existing>) -> Unit) :
		SuspendUseCase1<Transaction<Existing>, Unit>

fun updateTransactionUseCaseFactory(transactionRepository: TransactionRepository) = UpdateTransactionUseCase { transaction ->
	transactionRepository.updateTransaction(transaction)
}
