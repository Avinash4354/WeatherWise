package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.data.model.GeocodeResult
import uk.ac.tees.mad.weatherwise.data.remote.GeocodingApiService
import uk.ac.tees.mad.weatherwise.utils.Constants
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: GeocodingApiService
):ViewModel() {

    private val _locations = MutableStateFlow<List<GeocodeResult>>(emptyList())
    val locations: StateFlow<List<GeocodeResult>> get() = _locations
    private val _query = MutableStateFlow("")
    val query :StateFlow<String> get() = _query

    fun searchLocations(){
        viewModelScope.launch {
            try {
                val result = repository.getGeocode(_query.value, apiKey = Constants.WEATHER_API)
                _locations.value = result
            }
            catch (e:Exception){
                Log.e("Geocode Error", e.message.toString())
            }
        }
    }

    fun onChangeQuery(newQuery:String){
        _query.value = newQuery
    }
}