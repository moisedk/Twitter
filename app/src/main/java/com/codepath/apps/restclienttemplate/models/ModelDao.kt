package com.codepath.apps.restclienttemplate.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ModelDao {

    // @Query annotation requires knowing SQL syntax
    // See http://www.sqltutorial.org/
    @Query("SELECT * FROM Model WHERE id = :id")
    fun byId(id: Long): Model?

    @Query("SELECT * FROM Model ORDER BY ID DESC LIMIT 300")
    fun recentItems(): List<Model?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModel(vararg models: Model?)
}