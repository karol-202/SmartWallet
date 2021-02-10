package pl.karol202.smartwallet.interactors.usecases.account

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.interactors.usecases.UseCase0

data class AccountWithDefault(val account: Account<Existing>,
                              val isDefault: Boolean)

class GetAccountsWithDefaultFlowUseCase(override val function: () -> Flow<List<AccountWithDefault>>) :
		UseCase0<Flow<List<AccountWithDefault>>>

fun getAccountsWithDefaultFlowUseCaseFactory(accountRepository: AccountRepository) = GetAccountsWithDefaultFlowUseCase {
	combine(accountRepository.allAccounts, accountRepository.defaultAccount) { accounts, defaultAccount ->
		accounts.map {
			AccountWithDefault(
				account = it,
				isDefault = it.id.value == defaultAccount?.id?.value
			)
		}
	}
}
