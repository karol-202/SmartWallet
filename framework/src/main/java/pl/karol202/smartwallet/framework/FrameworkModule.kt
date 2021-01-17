package pl.karol202.smartwallet.framework

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.karol202.smartwallet.data.datasource.CategoryDataSource
import pl.karol202.smartwallet.data.datasource.IdDataSource
import pl.karol202.smartwallet.data.datasource.SubcategoryDataSource
import pl.karol202.smartwallet.data.datasource.TransactionDataSource
import pl.karol202.smartwallet.framework.datasource.RoomCategoryDataSource
import pl.karol202.smartwallet.framework.datasource.RoomSubcategoryDataSource
import pl.karol202.smartwallet.framework.datasource.RoomTransactionDataSource
import pl.karol202.smartwallet.framework.datasource.UuidIdDataSource
import pl.karol202.smartwallet.framework.room.LocalDatabase

fun frameworkModule() = module {
	single { LocalDatabase.create(androidContext()) }

	single { get<LocalDatabase>().transactionDao() }
	single { get<LocalDatabase>().categoryDao() }
	single { get<LocalDatabase>().subcategoryDao() }

	single<TransactionDataSource> { RoomTransactionDataSource(get()) }
	single<CategoryDataSource> { RoomCategoryDataSource(get()) }
	single<SubcategoryDataSource> { RoomSubcategoryDataSource(get()) }

	single<IdDataSource> { UuidIdDataSource() }
}
