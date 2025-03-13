package uk.ac.tees.mad.weatherwise.data.model

data class GeocodeResult(
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double
)