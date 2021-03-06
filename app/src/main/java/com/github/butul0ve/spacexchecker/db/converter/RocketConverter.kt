package com.github.butul0ve.spacexchecker.db.converter

import androidx.room.TypeConverter
import com.github.butul0ve.spacexchecker.db.model.Diameter
import com.github.butul0ve.spacexchecker.db.model.Height
import com.github.butul0ve.spacexchecker.db.model.Mass
import com.github.butul0ve.spacexchecker.db.model.Rocket

class RocketConverter {

    @TypeConverter
    fun fromRocket(rocket: Rocket): String {
        val sb = StringBuilder()
        sb.apply {
            append(rocket.rocketId)
            append(" ")
            append(rocket.id)
            append(" ")
            append(rocket.name ?: "")
            append(" ")
            append(rocket.isActive)
            append(" ")
            append(rocket.stages ?: 0)
            append(" ")
            append(rocket.firstFlight ?: "")
            append(" ")
            append(rocket.wiki ?: "")
            append(" ")
            append(rocket.description ?: "")
            append(" ")
            append(rocket.height?.meters ?: "")
            append(" ")
            append(rocket.height?.feet ?: "")
            append(" ")
            append(rocket.diameter?.meters ?: "")
            append(" ")
            append(rocket.diameter?.feet ?: "")
            append(" ")
            append(rocket.mass?.kg ?: "")
            append(" ")
            append(rocket.mass?.kg ?: "")
        }
        return sb.toString()
    }

    @TypeConverter
    fun toRocket(string: String): Rocket {
        val list = string.split(" ")

        val rocketId = if (list[0] != "null") {
            list[0].toLong()
        } else {
            null
        }

        return Rocket(rocketId,
                list[1],
                list[2],
                list[3].toBoolean(),
                list[4].toIntOrNull(),
                list[5],
                list[6],
                list[7],
                Height(list[8].toFloatOrNull(), list[9].toFloatOrNull()),
                Diameter(list[10].toFloatOrNull(), list[11].toFloatOrNull()),
                Mass(list[12].toLongOrNull(), list[13].toLongOrNull()))
    }
}