package g.sig.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import g.sig.core.data.local.dao.EventDao
import g.sig.core.data.local.dao.SportDao
import g.sig.core.data.local.enitites.EventLocalDto
import g.sig.core.data.local.enitites.SportLocalDto

@Database(
    entities = [EventLocalDto::class, SportLocalDto::class],
    version = 1,
    exportSchema = false,
)
internal abstract class SportsDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    abstract fun sportDao(): SportDao
}
