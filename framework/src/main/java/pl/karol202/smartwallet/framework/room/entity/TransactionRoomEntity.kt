package pl.karol202.smartwallet.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.smartwallet.data.model.TransactionModel
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity.Type
import java.time.LocalDate

@Entity(tableName = "transactions")
data class TransactionRoomEntity(@PrimaryKey val id: String,
                                 val type: Type,
                                 val date: LocalDate,
                                 val amount: Double)
{
	enum class Type
	{
		EXPENSE, INCOME
	}
}

fun TransactionModel.toRoomEntity() = when(this)
{
	is TransactionModel.Expense -> TransactionRoomEntity(id, Type.EXPENSE, date, amount)
	is TransactionModel.Income -> TransactionRoomEntity(id, Type.INCOME, date, amount)
}

fun TransactionRoomEntity.toModel() = when(type)
{
	Type.EXPENSE -> TransactionModel.Expense(id, date, amount)
	Type.INCOME -> TransactionModel.Income(id, date, amount)
}
