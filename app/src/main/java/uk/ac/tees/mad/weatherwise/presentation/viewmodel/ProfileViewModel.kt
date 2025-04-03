package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.weatherwise.utils.Utils.getRealPathFromURI
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth:FirebaseAuth,
    private val cloudinary: Cloudinary
):ViewModel() {
    private val _name = MutableStateFlow("Name")
    val name:StateFlow<String> get() = _name
    private val userId = auth.currentUser!!.uid

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri:StateFlow<Uri?> get() = _imageUri
    private val _profilePicChange = MutableStateFlow(false)
    val profilePicChange:StateFlow<Boolean> get() = _profilePicChange


    init {
        viewModelScope.launch {
            _name.value = auth.currentUser?.displayName.toString()
            _imageUri.value = auth.currentUser?.photoUrl
            Log.e("ImageUri",_imageUri.value?.path.toString())
        }
    }

    fun uploadImageToCloudinary(context: Context) {
        if (_imageUri.value!=null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val filePath = getRealPathFromURI(context, _imageUri.value!!) ?: return@launch

                    val request = cloudinary.uploader()
                        .upload(filePath, ObjectUtils.asMap(
                            "public_id", "profile_pictures/$userId",
                            "overwrite", true
                        ))

                    val imageUrl = request["secure_url"] as? String ?: ""
                    viewModelScope.launch {
                        val profileUpdate = UserProfileChangeRequest.Builder()
                            .setPhotoUri(Uri.parse(imageUrl))
                            .build()
                        auth.currentUser?.updateProfile(profileUpdate)
                    }

                } catch (_: Exception) {

                }
            }
        }
    }


    fun updateProfile(newName:String, context: Context){
        _name.value = newName
        viewModelScope.launch {
            val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(_name.value)
                .build()
            auth.currentUser?.updateProfile(profileUpdate)
            if (_profilePicChange.value){
                uploadImageToCloudinary(context)
            }
        }
    }

    fun onPicSelected(value:Boolean){
        _profilePicChange.value = value
    }

    fun changeImageUri(uri:Uri?){
        _imageUri.value = uri
    }

    fun logOut(){
        auth.signOut()
    }
}