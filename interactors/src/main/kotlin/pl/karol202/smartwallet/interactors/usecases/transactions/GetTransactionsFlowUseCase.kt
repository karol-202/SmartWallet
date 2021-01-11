package pl.karol202.smartwallet.interactors.usecases.transactions

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.UseCase0

class GetTransactionsFlowUseCase(override val function: () -> Flow<List<Transaction>>) : UseCase0<Flow<List<Transaction>>>

fun getTransactionsFlowUseCaseFactory(transactionRepository: TransactionRepository) = GetTransactionsFlowUseCase {
	transactionRepository.allTransactions
}
