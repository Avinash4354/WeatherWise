package uk.ac.tees.mad.weatherwise.utils

import uk.ac.tees.mad.weatherwise.model.Raindrop
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.random.Random

object Utils {
    fun generateRaindrops(count: Int): List<Raindrop> {
        return List(count) {
            Raindrop(
                x = Random.nextFloat(),
                length = Random.nextFloat() * 40 + 10,
                width = Random.nextFloat() * 4 + 1,
                speed = Random.nextInt(500, 1500),
                slant = Random.nextFloat() *2 -1f,
                opacity = Random.nextFloat() *0.6f +0.4f
            )
        }
    }

    fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < 1000 -> "Just now"
            diff < 60_000 -> "${diff / 1000} s"
            diff < 3_600_000 -> "${diff / 60_000} m"
            diff < 86_400_000 -> "${diff / 3_600_000} h"
            diff < 604_800_000 -> "${diff / 86_400_000} d"
            diff < 2_629_746_000 -> "${diff / 604_800_000} w"
            diff < 31_557_600_000 -> "${diff / 2_629_746_000} mo"
            else -> "${diff / 31_557_600_000} y"
        }
    }

    fun formatTimestampToTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

}