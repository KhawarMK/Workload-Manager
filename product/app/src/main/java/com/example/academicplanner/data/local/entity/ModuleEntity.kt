package com.example.academicplanner.data.local.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
Entity class representing a module in the database.
 */
@Entity(tableName = "modules")
data class ModuleEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo(name = "module_code")
    val moduleCode : String = "CS2800",
    @ColumnInfo(name = "module_name")
    val moduleName : String = "Final Year Project",
    @ColumnInfo(name = "module_credits")
    val moduleCredits : Int = 15,
    /**
     * Used in the UI to visually color-code cards, timetable classes, and calendar sessions.
     */
    @ColumnInfo(name = "module_colors")
    val moduleColors : String = "#000000",



)

