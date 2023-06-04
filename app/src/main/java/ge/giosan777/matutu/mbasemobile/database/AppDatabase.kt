package ge.giosan777.matutu.mbasemobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ge.giosan777.matutu.mbasemobile.dao.PersonDAO
import ge.giosan777.matutu.mbasemobile.models.Person


@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): PersonDAO

    companion object {
        fun getDb(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "m_base.db"
            ).allowMainThreadQueries().build()
        }
    }
}