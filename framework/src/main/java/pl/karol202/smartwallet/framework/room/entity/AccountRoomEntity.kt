package pl.karol202.smartwallet.framework.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.karol202.smartwallet.data.model.AccountModel

@Entity(tableName = "accounts")
data class AccountRoomEntity(@PrimaryKey val id: String,
                             val name: String)

fun AccountModel.toRoomEntity() = AccountRoomEntity(id, name)

fun AccountRoomEntity.toModel() = AccountModel(id, name)
