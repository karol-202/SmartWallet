package pl.karol202.smartwallet.interactors.usecases.account

import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class MarkAccountAsDefaultUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun markAccountAsDefaultUseCaseFactory(accountRepository: AccountRepository) = MarkAccountAsDefaultUseCase { id ->
	accountRepository.markAccountAsDefault(id)
}
