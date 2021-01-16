package pl.karol202.smartwallet.interactors.usecases.transactions

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class GetTransactionUseCase(override val function: suspend (Long) -> Transaction) : SuspendUseCase1<Long, Transaction>

fun getTransactionUseCaseFactory(transactionRepository: TransactionRepository) = GetTransactionUseCase { id ->
	transactionRepository.getTransaction(id).first()
}