package pl.karol202.smartwallet.framework.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.karol202.smartwallet.framework.room.converter.CategoryTypeTypeConverter
import pl.karol202.smartwallet.framework.room.converter.LocalDateTypeConverter
import pl.karol202.smartwallet.framework.room.converter.TransactionTypeTypeConverter
import pl.karol202.smartwallet.framework.room.dao.AccountDao
import pl.karol202.smartwallet.framework.room.dao.CategoryDao
import pl.karol202.smartwallet.framework.room.dao.SubcategoryDao
import pl.karol202.smartwallet.framework.room.dao.TransactionDao
import pl.karol202.smartwallet.framework.room.entity.AccountRoomEntity
import pl.karol202.smartwallet.framework.room.entity.CategoryRoomEntity
import pl.karol202.smartwallet.framework.room.entity.SubcategoryRoomEntity
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity

private const val DATABASE_NAME = "smartwallet.local"
private const val DATABASE_VERSION = 9

@Database(
	entities = [
		TransactionRoomEntity::class,
		CategoryRoomEntity::class,
		SubcategoryRoomEntity::class,
		AccountRoomEntity::class
	],
	version = DATABASE_VERSION,
	exportSchema = false
)
@TypeConverters(
	TransactionTypeTypeConverter::class,
	LocalDateTypeConverter::class,
	CategoryTypeTypeConverter::class
)
abstract class LocalDatabase : RoomDatabase()
{
	companion object
	{
		fun create(context: Context) =
			Room.databaseBuilder(context.applicationContext, LocalDatabase::class.java, DATABASE_NAME)
					.fallbackToDestructiveMigration() //TODO To be removed
					.build()
	}

	abstract fun transactionDao(): TransactionDao

	abstract fun categoryDao(): CategoryDao

	abstract fun subcategoryDao(): SubcategoryDao

	abstract fun accountDao(): AccountDao
}


