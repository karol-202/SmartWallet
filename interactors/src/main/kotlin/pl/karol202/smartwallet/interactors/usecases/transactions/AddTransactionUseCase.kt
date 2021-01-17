package pl.karol202.smartwallet.interactors.usecases.transactions

import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class AddTransactionUseCase(override val function: suspend (Transaction<New>) -> Unit) :
		SuspendUseCase1<Transaction<New>, Unit>

fun addTransactionUseCaseFactory(transactionRepository: TransactionRepository) = AddTransactionUseCase { transaction ->
	transactionRepository.addTransaction(transaction)
}
