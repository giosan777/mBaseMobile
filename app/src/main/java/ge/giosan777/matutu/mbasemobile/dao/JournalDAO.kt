package ge.giosan777.matutu.mbasemobile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ge.giosan777.matutu.mbasemobile.models.Journal
import ge.giosan777.matutu.mbasemobile.models.Organization


@Dao
interface JournalDAO {

    @Insert
    fun insertAll(vararg journal: Journal)

    @Insert
    fun insertAllJournal(journalList:MutableList<Journal>)

    @Query("SELECT * FROM journal")
    fun getAllJournal(): List<Journal>

    @Query("DELETE FROM journal")
    fun deleteAll()

//    @Query("SELECT * FROM journal WHERE organization_name LIKE :startingNameWith || '%'")
//    fun findByNameStartingWith(startingNameWith:String ): List<Organization>
//
//
//    @Query("SELECT * FROM organizations WHERE category LIKE :startingCategoryWith || '%'")
//    fun findByCategoryStartingWith(startingCategoryWith:String ): List<Organization>
}