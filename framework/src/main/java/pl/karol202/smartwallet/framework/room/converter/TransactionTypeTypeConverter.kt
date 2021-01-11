package pl.karol202.smartwallet.framework.room.converter

import androidx.room.TypeConverter
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity

class TransactionTypeTypeConverter
{
	@TypeConverter
	fun fromTransactionType(transactionType: TransactionRoomEntity.TransactionType) = transactionType.name

	@TypeConverter
	fun toTransactionType(value: String) = enumValueOf<TransactionRoomEntity.TransactionType>(value)
}
