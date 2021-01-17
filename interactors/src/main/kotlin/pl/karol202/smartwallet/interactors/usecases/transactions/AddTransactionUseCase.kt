package pl.karol202.smartwallet.interactors.usecases.transactions

import pl.karol202.smartwallet.domain.entity.NewTransaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class AddTransactionUseCase(override val function: suspend (NewTransaction) -> Unit) : SuspendUseCase1<NewTransaction, Unit>

fun addTransactionUseCaseFactory(transactionRepository: TransactionRepository) = AddTransactionUseCase { transaction ->
	transactionRepository.addTransaction(transaction)
}
