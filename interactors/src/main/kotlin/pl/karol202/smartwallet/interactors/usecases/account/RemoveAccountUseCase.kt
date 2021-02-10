package pl.karol202.smartwallet.interactors.usecases.account

import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class RemoveAccountUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun removeAccountUseCaseFactory(accountRepository: AccountRepository,
                                transactionRepository: TransactionRepository) = RemoveAccountUseCase { id ->
	transactionRepository.removeTransactionsOfAccount(id)
	accountRepository.removeAccount(id)
}
