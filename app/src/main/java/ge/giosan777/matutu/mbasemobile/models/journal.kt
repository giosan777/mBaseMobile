package ge.giosan777.matutu.mbasemobile.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal")
data class Journal(
    @PrimaryKey(autoGenerate = true) var id: Int?=null,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "type")var type: Int,
    @ColumnInfo(name = "date")val date: Long,
//    @ColumnInfo(name = "duration")val duration: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Journal

        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }
}