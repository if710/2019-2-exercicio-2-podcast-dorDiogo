package br.ufpe.cin.android.podcast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemFeedDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemFeeds(vararg itemFeeds: ItemFeed)

    @Query("SELECT * FROM item_feeds")
    fun getAllItemFeeds(): List<ItemFeed>

    @Query("SELECT * FROM item_feeds WHERE downloadLink LIKE :q")
    fun getItemFeed(q: String): ItemFeed
}