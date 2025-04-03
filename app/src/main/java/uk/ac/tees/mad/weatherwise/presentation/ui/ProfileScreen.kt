package uk.ac.tees.mad.weatherwise.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.weatherwise.presentation.components.EditProfileBottomSheet
import uk.ac.tees.mad.weatherwise.presentation.components.ProfileSection
import uk.ac.tees.mad.weatherwise.presentation.components.SettingButton
import uk.ac.tees.mad.weatherwise.presentation.navigation.Screens
import uk.ac.tees.mad.weatherwise.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController,
                  viewModel: ProfileViewModel = hiltViewModel(),
                  modifier: Modifier = Modifier) {
    val name by viewModel.name.collectAsState()
    var showEditProfile by remember { mutableStateOf(false) }
    val imageUri by viewModel.imageUri.collectAsState()

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.changeImageUri(uri)
            viewModel.onPicSelected(true)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        ProfileSection(
            name,
            imageUri,
            onEditClick = {showEditProfile = true},
        )
        SettingButton(
            title = "Current Location",
            icon = Icons.Default.LocationOn
        ) {
            navController.navigate(Screens.CurrentLocationScreen.route)
        }
        SettingButton(
            title = "Log Out",
            icon = Icons.AutoMirrored.Filled.ExitToApp
        ) {
            viewModel.logOut()
            navController.navigate(Screens.AuthenticationScreen.route){
                popUpTo(Screens.MainScreen.route){
                    inclusive = true
                }
            }
        }
    }
    if (showEditProfile){
        EditProfileBottomSheet(
            name,
            imageUri,
            onSave = {viewModel.updateProfile(it, context)
                     showEditProfile = false
                     viewModel.onPicSelected(false)
                     },
            onImageClick = {launcher.launch("image/*")},
            onDismiss = {showEditProfile = false
            viewModel.onPicSelected(false)
            }
        )
    }
}