package uk.ac.tees.mad.weatherwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
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

    private fun login() {
        if (_email.value.isEmpty() || _password.value.isEmpty()) {
            // add toast
            return
        }

        _isLoading.value = true
        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    // toast add
                    _loginSuccess.value = true
                } else {
                    // add toast
                }
            }
    }

    private fun signUp() {
        if (_email.value.isEmpty() || _password.value.isEmpty() || _name.value.isEmpty()) {
            // add Toast
            return
        }

        _isLoading.value = true
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _loginSuccess.value = true
                    // add toast
                } else {
                    // add toast
                }
            }
    }

    fun authenticate() {
        if (_isSignUp.value) {
            signUp()
        } else {
            login()
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

    fun onIsLoadingChange(value:Boolean){
        _isLoading.value = value
    }

}