package pl.karol202.smartwallet.interactors.usecases.init

import kotlinx.coroutines.runBlocking
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.UseCase0

class InitializeRepositoriesUseCase(override val function: () -> Unit) : UseCase0<Unit>

fun initializeRepositoriesUseCaseFactory(categoryRepository: CategoryRepository,
                                         subcategoryRepository: SubcategoryRepository) = InitializeRepositoriesUseCase {
	runBlocking {
		categoryRepository.ensureIntegrity()
		subcategoryRepository.ensureIntegrity()
	}
}
