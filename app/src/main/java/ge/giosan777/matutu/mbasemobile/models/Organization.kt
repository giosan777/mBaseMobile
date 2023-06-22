package ge.giosan777.matutu.mbasemobile.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "organizations")
data class Organization(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "org_name") var orgName: String,
    @ColumnInfo(name = "description") var description: String,
)
