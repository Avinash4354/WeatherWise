package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.data.local.WeatherEntity
import uk.ac.tees.mad.weatherwise.domain.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val auth: FirebaseAuth,
):ViewModel() {
    private val currentUser = auth.currentUser?.uid?:""

    private val _favoriteLocationData = MutableStateFlow<List<WeatherEntity>>(emptyList())
    val favoriteLocationData:StateFlow<List<WeatherEntity>> get() = _favoriteLocationData

    init {
        viewModelScope.launch {
            repository.getFavoriteLocations(currentUser).collect{
                _favoriteLocationData.value = it
            }
        }
    }

    fun deleteFavorite(entity: WeatherEntity){
        viewModelScope.launch {
            repository.deleteFavoriteLocation(entity)
        }
    }
}