package pl.karol202.smartwallet.ui.viewmodel

import androidx.lifecycle.ViewModel
import java.io.Closeable

abstract class AndroidViewModelProxy(private val closeable: Closeable) : ViewModel()
{
	override fun onCleared() = closeable.close()
}
