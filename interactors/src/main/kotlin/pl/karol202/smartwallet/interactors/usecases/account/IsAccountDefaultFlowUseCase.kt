package pl.karol202.smartwallet.interactors.usecases.account

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class IsAccountDefaultFlowUseCase(override val function: suspend (String?) -> Flow<Boolean>) :
		SuspendUseCase1<String?, Flow<Boolean>>

fun isAccountDefaultFlowUseCaseFactory(accountRepository: AccountRepository) = IsAccountDefaultFlowUseCase { id ->
	if(id == null) return@IsAccountDefaultFlowUseCase emptyFlow()
	accountRepository.defaultAccount.map { it?.id?.value == id }
}
