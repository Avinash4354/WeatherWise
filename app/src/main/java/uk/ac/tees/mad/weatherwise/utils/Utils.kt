package uk.ac.tees.mad.weatherwise.utils

import android.content.Context
import android.net.Uri
import uk.ac.tees.mad.weatherwise.R
import uk.ac.tees.mad.weatherwise.model.Raindrop
import uk.ac.tees.mad.weatherwise.model.Snowflake
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

    fun generateSnowflakes(count: Int): List<Snowflake> {
        return List(count) {
            Snowflake(
                x = Random.nextFloat(),
                size = Random.nextFloat() * 6f + 4f,
                opacity = Random.nextFloat() * 0.5f + 0.5f,
                drift = Random.nextFloat() * 0.002f - 0.001f
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

    fun getBackground(weather:String):Int{
        return when(weather){
            "Clear" -> R.drawable.clear
            "Clouds" -> R.drawable.cloud
            "Rain" -> R.drawable.rain
            "Drizzle" -> R.drawable.rain
            "Thunderstorm" -> R.drawable.thunder_storm
            "Snow" -> R.drawable.snow
            else -> R.drawable.atmosphere
        }
    }

    fun getRealPathFromURI(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            it.moveToFirst()
            val index = it.getColumnIndex("_data")
            if (index != -1) it.getString(index) else null
        }
    }

}