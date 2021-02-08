package pl.karol202.smartwallet.interactors.usecases.account

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class GetAccountUseCase(override val function: suspend (String) -> Account<Existing>?) :
		SuspendUseCase1<String, Account<Existing>?>

fun getAccountUseCaseFactory(accountRepository: AccountRepository) = GetAccountUseCase { id ->
	accountRepository.getAccount(id).first()
}
