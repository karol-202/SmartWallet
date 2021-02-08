package pl.karol202.smartwallet.interactors.usecases.account

import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class UpdateAccountUseCase(override val function: suspend (Account<Existing>) -> Unit) :
		SuspendUseCase1<Account<Existing>, Unit>

fun updateAccountUseCaseFactory(accountRepository: AccountRepository) = UpdateAccountUseCase { account ->
	accountRepository.updateAccount(account)
}
