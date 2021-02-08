package pl.karol202.smartwallet.interactors.usecases.account

import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class AddAccountUseCase(override val function: suspend (Account<New>) -> Unit) : SuspendUseCase1<Account<New>, Unit>

fun addAccountUseCaseFactory(accountRepository: AccountRepository) = AddAccountUseCase { category ->
	accountRepository.addAccount(category)
}
