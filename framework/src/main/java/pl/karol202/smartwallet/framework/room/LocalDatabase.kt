package pl.karol202.smartwallet.framework.room

import android.content.Context
import androidx.room.*
import pl.karol202.smartwallet.framework.room.dao.ExpenseDao
import pl.karol202.smartwallet.framework.room.dao.IncomeDao
import pl.karol202.smartwallet.framework.room.entity.ExpenseRoomEntity
import pl.karol202.smartwallet.framework.room.entity.IncomeRoomEntity

private const val DATABASE_NAME = "smartwallet.local"
private const val DATABASE_VERSION = 1

@Database(entities = [ExpenseRoomEntity::class, IncomeRoomEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class LocalDatabase : RoomDatabase()
{
	companion object
	{
		fun create(context: Context) =
			Room.databaseBuilder(context.applicationContext, LocalDatabase::class.java, DATABASE_NAME)
				.fallbackToDestructiveMigration() //TODO To be removed
				.build()
	}

	abstract fun expenseDao(): ExpenseDao

	abstract fun incomeDao(): IncomeDao
}


