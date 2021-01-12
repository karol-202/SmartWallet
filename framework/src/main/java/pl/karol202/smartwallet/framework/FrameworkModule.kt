package pl.karol202.smartwallet.framework

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.karol202.smartwallet.data.datasource.TransactionDataSource
import pl.karol202.smartwallet.framework.datasource.RoomTransactionDataStore
import pl.karol202.smartwallet.framework.room.LocalDatabase

fun frameworkModule() = module {
	single { LocalDatabase.create(androidContext()) }
	single { get<LocalDatabase>().transactionDao() }

	single<TransactionDataSource> { RoomTransactionDataStore(get()) }
}
