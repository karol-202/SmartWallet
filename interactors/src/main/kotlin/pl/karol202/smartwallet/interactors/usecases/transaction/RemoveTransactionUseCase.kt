package pl.karol202.smartwallet.interactors.usecases.transaction

import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class RemoveTransactionUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun removeTransactionUseCaseFactory(transactionRepository: TransactionRepository) = RemoveTransactionUseCase { id ->
	transactionRepository.removeTransaction(id)
}
