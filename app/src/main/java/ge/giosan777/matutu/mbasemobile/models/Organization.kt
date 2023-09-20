package ge.giosan777.matutu.mbasemobile.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "organizations")
data class Organization(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "organization_name") var organizationName: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "address") var address: String?,
    @ColumnInfo(name = "web_site") var webSite: String?,
    @ColumnInfo(name = "img") var img: String?,
    @ColumnInfo(name = "languages") var languages: String?,
    @ColumnInfo(name = "location") var location: String?
)
