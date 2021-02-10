package pl.karol202.smartwallet.interactors.usecases.account

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase0

class GetDefaultAccountUseCase(override val function: suspend () -> Account<Existing>?) : SuspendUseCase0<Account<Existing>?>

fun getDefaultAccountUseCaseFactory(accountRepository: AccountRepository) = GetDefaultAccountUseCase {
	accountRepository.defaultAccount.first()
}
