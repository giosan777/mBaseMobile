package ge.giosan777.matutu.mbasemobile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ge.giosan777.matutu.mbasemobile.models.Person


@Dao
interface PersonDAO {

//    @Insert
//    fun insertAll(vararg people: Person)

    @Insert
    fun insertAllPeoples(peopleList:MutableList<Person>)

    @Query("SELECT * FROM person")
    fun getAllPeople(): List<Person>

    @Query("DELETE FROM Person")
    fun deleteAll()

    @Query("SELECT * FROM person WHERE phone LIKE :startingWith || '%'")
    fun findByPhoneStartingWith(startingWith:String ): List<Person>


}