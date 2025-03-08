package uk.ac.tees.mad.weatherwise.utils

import uk.ac.tees.mad.weatherwise.model.Raindrop
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
}