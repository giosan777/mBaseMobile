package ge.giosan777.matutu.mbasemobile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ge.giosan777.matutu.mbasemobile.models.Organization


@Dao
interface OrgDAO {

//    @Insert
//    fun insertAll(vararg people: Person)

    @Insert
    fun insertAllOrganizations(organizationList:MutableList<Organization>)

    @Query("SELECT * FROM organizations")
    fun getAllOrganizations(): List<Organization>

    @Query("DELETE FROM organizations")
    fun deleteAll()

    @Query("SELECT * FROM organizations WHERE phone LIKE :startingWith")
    fun findByPhoneStartingWith(startingWith:String ): List<Organization>
}