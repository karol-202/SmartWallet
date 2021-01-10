package pl.karol202.smartwallet.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.smartwallet.data.model.IncomeModel
import pl.karol202.smartwallet.data.model.NewIncomeModel

@Entity(tableName = "incomes")
data class IncomeRoomEntity(@PrimaryKey(autoGenerate = true) val id: Long,
                             val amount: Double)

fun IncomeModel.toRoomEntity() = IncomeRoomEntity(id, amount)
fun NewIncomeModel.toRoomEntity() = IncomeRoomEntity(0, amount)
fun IncomeRoomEntity.toEntity() = IncomeModel(id, amount)
