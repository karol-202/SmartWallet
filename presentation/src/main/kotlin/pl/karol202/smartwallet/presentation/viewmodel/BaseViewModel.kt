package pl.karol202.smartwallet.presentation.viewmodel

import kotlinx.coroutines.*
import java.io.Closeable

abstract class BaseViewModel : Closeable
{
	protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

	fun launch(block: suspend CoroutineScope.() -> Unit)
	{
		viewModelScope.launch { block() }
	}

	override fun close() = viewModelScope.cancel()
}
