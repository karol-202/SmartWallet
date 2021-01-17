package pl.karol202.smartwallet.interactors.usecases.transactions

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.UseCase0

class GetTransactionsFlowUseCase(override val function: () -> Flow<List<Transaction<Existing>>>) :
		UseCase0<Flow<List<Transaction<Existing>>>>

fun getTransactionsFlowUseCaseFactory(transactionRepository: TransactionRepository) = GetTransactionsFlowUseCase {
	transactionRepository.allTransactions
}
