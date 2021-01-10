package pl.karol202.smartwallet.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.smartwallet.data.model.ExpenseModel
import pl.karol202.smartwallet.data.model.NewExpenseModel

@Entity(tableName = "expenses")
data class ExpenseRoomEntity(@PrimaryKey(autoGenerate = true) val id: Long,
                             val amount: Double)

fun ExpenseModel.toRoomEntity() = ExpenseRoomEntity(id, amount)
fun NewExpenseModel.toRoomEntity() = ExpenseRoomEntity(0, amount)
fun ExpenseRoomEntity.toEntity() = ExpenseModel(id, amount)
