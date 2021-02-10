package pl.karol202.smartwallet.framework

import androidx.preference.PreferenceManager
import org.koin.dsl.module
import pl.karol202.smartwallet.data.datasource.*
import pl.karol202.smartwallet.data.provider.DefaultNameProvider
import pl.karol202.smartwallet.framework.datasource.*
import pl.karol202.smartwallet.framework.provider.DefaultNameProviderImpl
import pl.karol202.smartwallet.framework.room.LocalDatabase

fun frameworkModule() = module {
	single { PreferenceManager.getDefaultSharedPreferences(get()) }

	single { LocalDatabase.create(get()) }
	single { get<LocalDatabase>().transactionDao() }
	single { get<LocalDatabase>().categoryDao() }
	single { get<LocalDatabase>().subcategoryDao() }
	single { get<LocalDatabase>().accountDao() }

	single<TransactionDataSource> { RoomTransactionDataSource(get()) }
	single<CategoryDataSource> { RoomCategoryDataSource(get()) }
	single<SubcategoryDataSource> { RoomSubcategoryDataSource(get()) }
	single<AccountDataSource> { RoomAccountDataSource(get()) }
	single<DefaultAccountDataSource> { SharedPrefsDefaultAccountDataSource(get()) }

	single<IdDataSource> { UuidIdDataSource() }

	single<DefaultNameProvider> { DefaultNameProviderImpl(get()) }
}
