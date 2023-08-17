package ge.giosan777.matutu.mbasemobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ge.giosan777.matutu.mbasemobile.dao.JournalDAO
import ge.giosan777.matutu.mbasemobile.dao.OrgDAO
import ge.giosan777.matutu.mbasemobile.dao.PersonDAO
import ge.giosan777.matutu.mbasemobile.models.Journal
import ge.giosan777.matutu.mbasemobile.models.Organization
import ge.giosan777.matutu.mbasemobile.models.Person


@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract  fun getDao(): PersonDAO

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

@Database(entities = [Organization::class], version = 1)
abstract class AppDatabaseOrg : RoomDatabase() {
    abstract fun getDao(): OrgDAO

    companion object {
        fun getDb(context: Context): AppDatabaseOrg {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabaseOrg::class.java,
                "o_base.db"
            ).allowMainThreadQueries().build()
        }
    }
}

@Database(entities = [Journal::class], version = 1)
abstract class AppDatabaseJournal : RoomDatabase() {
    abstract fun getDao(): JournalDAO

    companion object {
        fun getDb(context: Context): AppDatabaseJournal {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabaseJournal::class.java,
                "j_base.db"
            ).allowMainThreadQueries().build()
        }
    }
}