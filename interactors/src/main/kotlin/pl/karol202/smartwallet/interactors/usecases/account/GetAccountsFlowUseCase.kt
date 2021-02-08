package pl.karol202.smartwallet.interactors.usecases.account

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.interactors.usecases.UseCase0

class GetAccountsFlowUseCase(override val function: () -> Flow<List<Account<Existing>>>) :
		UseCase0<Flow<List<Account<Existing>>>>

fun getAccountsFlowUseCaseFactory(accountRepository: AccountRepository) = GetAccountsFlowUseCase {
	accountRepository.allAccounts
}
