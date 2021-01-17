package pl.karol202.smartwallet.framework.room

import android.content.Context
import androidx.room.*
import pl.karol202.smartwallet.framework.room.converter.LocalDateTypeConverter
import pl.karol202.smartwallet.framework.room.converter.TransactionTypeTypeConverter
import pl.karol202.smartwallet.framework.room.dao.TransactionDao
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity

private const val DATABASE_NAME = "smartwallet.local"
private const val DATABASE_VERSION = 2

@Database(entities = [TransactionRoomEntity::class],
          version = DATABASE_VERSION,
          exportSchema = false)
@TypeConverters(TransactionTypeTypeConverter::class, LocalDateTypeConverter::class)
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
}

