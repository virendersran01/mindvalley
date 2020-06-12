package com.frank.mindvalley.db.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class CategoryDb(
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "name") val name: String
)