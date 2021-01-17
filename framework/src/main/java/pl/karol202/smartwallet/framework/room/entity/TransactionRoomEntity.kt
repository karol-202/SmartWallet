package pl.karol202.smartwallet.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import pl.karol202.smartwallet.data.model.NewTransactionModel
import pl.karol202.smartwallet.data.model.TransactionModel
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity.TransactionType
import java.time.LocalDate

@Entity(tableName = "transactions")
data class TransactionRoomEntity(@PrimaryKey(autoGenerate = true) val id: Long,
                                 val type: TransactionType,
                                 val date: LocalDate,
                                 val amount: Double)
{
	enum class TransactionType
	{
		EXPENSE, INCOME
	}
}

fun TransactionModel.toRoomEntity() = when(this)
{
	is TransactionModel.Expense -> TransactionRoomEntity(id, TransactionType.EXPENSE, date, amount)
	is TransactionModel.Income -> TransactionRoomEntity(id, TransactionType.INCOME, date, amount)
}

fun NewTransactionModel.toRoomEntity() = when(this)
{
	is NewTransactionModel.Expense -> TransactionRoomEntity(0, TransactionType.EXPENSE, date, amount)
	is NewTransactionModel.Income -> TransactionRoomEntity(0, TransactionType.INCOME, date, amount)
}

fun TransactionRoomEntity.toModel() = when(type)
{
	TransactionType.EXPENSE -> TransactionModel.Expense(id, date, amount)
	TransactionType.INCOME -> TransactionModel.Income(id, date, amount)
}
