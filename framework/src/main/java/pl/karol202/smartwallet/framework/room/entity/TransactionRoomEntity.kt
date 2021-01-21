package pl.karol202.smartwallet.framework.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import pl.karol202.smartwallet.data.model.TransactionModel
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity.Type
import java.time.LocalDate

@Entity(tableName = "transactions",
        foreignKeys = [
	        ForeignKey(entity = SubcategoryRoomEntity::class,
	                   parentColumns = ["id"],
	                   childColumns = ["subcategoryId"],
	                   onDelete = ForeignKey.CASCADE)
        ])
data class TransactionRoomEntity(@PrimaryKey val id: String,
                                 val type: Type,
                                 val subcategoryId: String,
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
	is TransactionModel.Expense -> TransactionRoomEntity(id, Type.EXPENSE, subcategoryId, date, amount)
	is TransactionModel.Income -> TransactionRoomEntity(id, Type.INCOME, subcategoryId, date, amount)
}

fun TransactionRoomEntity.toModel() = when(type)
{
	Type.EXPENSE -> TransactionModel.Expense(id, subcategoryId, date, amount)
	Type.INCOME -> TransactionModel.Income(id, subcategoryId, date, amount)
}
