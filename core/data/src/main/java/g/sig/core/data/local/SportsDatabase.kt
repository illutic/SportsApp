package g.sig.core.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import g.sig.core.data.local.dao.EventDao
import g.sig.core.data.local.dao.SportDao
import g.sig.core.data.local.enitites.EventLocalDto
import g.sig.core.data.local.enitites.SportLocalDto
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val databaseModule =
    module {
        single {
            Room
                .databaseBuilder(
                    context = androidContext(),
                    klass = SportsDatabase::class.java,
                    name = "sports_database",
                ).build()
        }
    }

@Database(
    entities = [EventLocalDto::class, SportLocalDto::class],
    version = 1,
    exportSchema = false,
)
internal abstract class SportsDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    abstract fun sportDao(): SportDao
}
