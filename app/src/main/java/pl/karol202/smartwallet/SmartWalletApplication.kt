package pl.karol202.smartwallet

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import pl.karol202.smartwallet.data.dataModule
import pl.karol202.smartwallet.data.datasource.TransactionDataSource
import pl.karol202.smartwallet.data.repository.LocalTransactionRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.framework.datasource.RoomTransactionDataStore
import pl.karol202.smartwallet.framework.frameworkModule
import pl.karol202.smartwallet.framework.room.LocalDatabase
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
	}
}
