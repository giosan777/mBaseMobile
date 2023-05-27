package ge.giosan777.matutu.mbasemobile.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true) var id: Int?=null,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "last_name") var lastName: String,
)
