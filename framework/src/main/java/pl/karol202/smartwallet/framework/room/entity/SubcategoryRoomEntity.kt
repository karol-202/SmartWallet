package pl.karol202.smartwallet.framework.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import pl.karol202.smartwallet.data.model.SubcategoryModel

@Entity(tableName = "subcategories",
        foreignKeys = [
	        ForeignKey(entity = CategoryRoomEntity::class,
	                   parentColumns = ["id"],
	                   childColumns = ["categoryId"],
	                   onDelete = ForeignKey.RESTRICT)
        ],
        indices = [
	        Index("categoryId")
        ]
)
data class SubcategoryRoomEntity(@PrimaryKey val id: String,
                                 val categoryId: String,
                                 val name: String)

fun SubcategoryModel.toRoomEntity() = SubcategoryRoomEntity(id, categoryId, name)

fun SubcategoryRoomEntity.toModel() = SubcategoryModel(id, categoryId, name)
