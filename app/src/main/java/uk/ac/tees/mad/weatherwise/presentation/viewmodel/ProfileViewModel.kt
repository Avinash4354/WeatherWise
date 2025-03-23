package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth:FirebaseAuth
):ViewModel() {
    private val _name = MutableStateFlow("Name")
    val name:StateFlow<String> get() = _name

    init {
        _name.value = auth.currentUser?.displayName.toString()
    }

    fun updateProfile(newName:String){
        _name.value = newName
        viewModelScope.launch {
            val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(_name.value)
                .build()
            auth.currentUser?.updateProfile(profileUpdate)
        }
    }

    fun logOut(){
        auth.signOut()
    }
}