package ge.giosan777.matutu.mbasemobile.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ge.giosan777.matutu.mbasemobile.dao.PersonDAO
import ge.giosan777.matutu.mbasemobile.models.Person


@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPersonDao(): PersonDAO
}