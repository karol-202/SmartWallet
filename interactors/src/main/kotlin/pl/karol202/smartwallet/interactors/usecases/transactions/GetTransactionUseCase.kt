package pl.karol202.smartwallet.interactors.usecases.transactions

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class GetTransactionUseCase(override val function: suspend (String) -> Transaction<Existing>) :
		SuspendUseCase1<String, Transaction<Existing>>

fun getTransactionUseCaseFactory(transactionRepository: TransactionRepository) = GetTransactionUseCase { id ->
	transactionRepository.getTransaction(id).first()
}
