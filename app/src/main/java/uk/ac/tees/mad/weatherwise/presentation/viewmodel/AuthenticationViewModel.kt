package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val auth:FirebaseAuth
):ViewModel() {

    private val _email = MutableStateFlow("")
    val email:StateFlow<String> get() = _email
    private val _password = MutableStateFlow("")
    val password:StateFlow<String> get() = _password
    private val _name = MutableStateFlow("")
    val name:StateFlow<String> get() = _name
    private val _isLoading  = MutableStateFlow(false)
    val isLoading:StateFlow<Boolean> get() = _isLoading
    private val _isSignUp = MutableStateFlow(false)
    val isSignUp:StateFlow<Boolean> get() = _isSignUp
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess:StateFlow<Boolean> get() = _loginSuccess

    private fun login(context: Context) {
        if (_email.value.isEmpty() || _password.value.isEmpty()) {
            Toast.makeText(context, "Enter full details", Toast.LENGTH_SHORT).show()
            return
        }

        _isLoading.value = true
        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    _loginSuccess.value = true
                } else {
                    Toast.makeText(context, "Login Failed, please check details", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signUp(context: Context) {
        if (_email.value.isEmpty() || _password.value.isEmpty() || _name.value.isEmpty()) {
            Toast.makeText(context, "Enter full details", Toast.LENGTH_SHORT).show()
            return
        }

        _isLoading.value = true
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val profileUpdate = UserProfileChangeRequest.Builder()
                        .setDisplayName(_name.value)
                        .build()
                    auth.currentUser?.updateProfile(profileUpdate)?.addOnSuccessListener{
                        _loginSuccess.value = true
                        Toast.makeText(context, "New user created", Toast.LENGTH_SHORT).show()
                    }
                        ?.addOnFailureListener{
                            Toast.makeText(context, "Authentication failed, please check details", Toast.LENGTH_SHORT).show()
                        }

                } else {
                    Toast.makeText(context, "Authentication failed, please check network", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun authenticate(context: Context) {
        if (_isSignUp.value) {
            signUp(context)
        } else {
            login(context)
        }
    }

    fun onIsSignUpChange(value:Boolean){
        _isSignUp.value = value
    }

    fun onNameChange(newName:String){
        _name.value = newName
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

}