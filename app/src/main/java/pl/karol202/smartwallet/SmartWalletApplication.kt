package pl.karol202.smartwallet

import android.app.Application
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.karol202.smartwallet.data.dataModule
import pl.karol202.smartwallet.framework.frameworkModule
import pl.karol202.smartwallet.interactors.usecases.init.InitializeRepositoriesUseCase
import pl.karol202.smartwallet.interactors.usecases.interactorsModule
import pl.karol202.smartwallet.presentation.presentationModule
import pl.karol202.smartwallet.ui.uiModule

class SmartWalletApplication : Application()
{
	override fun onCreate()
	{
		super.onCreate()
		startKoin {
			androidContext(this@SmartWalletApplication)
			modules(dataModule(), frameworkModule(), interactorsModule(), presentationModule(), uiModule())
		}
		get<InitializeRepositoriesUseCase>()()
	}
}
