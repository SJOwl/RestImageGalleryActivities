package au.sj.owl.restimagegallery.home.link

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Database
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.arch.persistence.room.RoomDatabase

/**
 * table of links
 */
@Entity(tableName = "links")
class Link(
        @ColumnInfo(name = "link_str")
        var link: String
          ) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface LinksDao {

    @Query("SELECT * FROM links WHERE id = :id")
    fun getLink(id: Int): LiveData<Link>

    @Query("SELECT * FROM links")
    fun getLinks(): LiveData<List<Link>>

    @Insert(onConflict = REPLACE)
    fun saveLink(l: Link)

    @Insert(onConflict = REPLACE)
    fun saveLinks(links: List<Link>)
}

@Database(entities = arrayOf(Link::class), version = 1)
abstract class LinksDB : RoomDatabase() {

    abstract fun linksDao(): LinksDao
}