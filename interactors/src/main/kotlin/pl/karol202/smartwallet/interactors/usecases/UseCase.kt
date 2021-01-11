package pl.karol202.smartwallet.interactors.usecases

interface UseCase0<R>
{
	val function: () -> R

	operator fun invoke() = function()
}

interface SuspendUseCase0<R>
{
	val function: suspend () -> R

	suspend operator fun invoke() = function()
}

interface UseCase1<P1, R>
{
	val function: (P1) -> R

	operator fun invoke(p1: P1) = function(p1)
}

interface SuspendUseCase1<P1, R>
{
	val function: suspend (P1) -> R

	suspend operator fun invoke(p1: P1) = function(p1)
}
