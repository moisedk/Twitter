package com.codepath.apps.restclienttemplate

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codepath.apps.restclienttemplate.models.Model
import com.codepath.apps.restclienttemplate.models.ModelDao

@Database(entities = [Model::class], version = 2)
abstract class MyDatabase : RoomDatabase() {
    abstract fun sampleModelDao(): ModelDao?

    companion object {
        // Database name to be used
        const val NAME = "MyDatabase"
    }
}