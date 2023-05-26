package ge.giosan777.matutu.mbasemobile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ge.giosan777.matutu.mbasemobile.models.Person


@Dao
interface PersonDAO {
    // Добавление Person в бд
    @Insert
    fun insertAll(vararg people: Person)

    // Получение всех Person из бд
    @Query("SELECT * FROM person")
    fun getAllPeople(): List<Person?>?

    // Получение всех Person из бд с условием
    @Query("SELECT * FROM person WHERE phone LIKE :phone")
    fun getAllPeopleWithPhone(phone: String?): List<Person?>?
}