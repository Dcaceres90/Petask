package com.deviar.petask.profile

import android.net.Uri
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.deviar.petask.R
import com.deviar.petask.common.ui.components.UserImage
import com.deviar.petask.common.utils.createImageUri


@Composable
fun ProfileScreen(
    modifier: Modifier,
    profileViewModel: ProfileViewModel
) {
    val context = LocalContext.current
    val state = profileViewModel.state

    LaunchedEffect(Unit) {
        profileViewModel.loadUser()
    }

    var isEditing by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null) {
            profileViewModel.onChangeProfileImage(uri)
        }
    }

    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->

            if (success) {
                photoUri?.let {
                    profileViewModel.onChangeProfileImage(it)
                }
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                photoUri = createImageUri(context)

                cameraLauncher.launch(photoUri!!)

            }
        }

    Box(
        modifier
            .fillMaxSize()
            .padding(25.dp),
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserImage(
                imageUri = state.imageUri,
                size = 100.dp,
                onClick = {
                    showBottomSheet = true
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clickable {
                        isEditing = true
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Name:")
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .drawBehind {
                            drawLine(
                                color = Color.Gray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2f
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isEditing) {
                        TextField(
                            value = state.userName,
                            onValueChange = { profileViewModel.onUserNameChange(it) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    isEditing = false
                                    profileViewModel.onUserNameEditDone()
                                }
                            )
                        )
                    } else {
                        Text(state.userName)
                    }
                }
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)

                )
            }
        }
        if (showBottomSheet) {
            PhotoPickerBottomSheet(
                onDismiss = {
                    showBottomSheet = false
                },
                onCameraClick = {
                    showBottomSheet = false
                    permissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                },
                onUploadClick = {
                    showBottomSheet = false
                    galleryLauncher.launch("image/*")
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoPickerBottomSheet(
    onCameraClick: () -> Unit,
    onUploadClick: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {

        ListItem(
            headlineContent = {
                Text("Camera")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null
                )
            },
            modifier = Modifier.clickable {
                onCameraClick()
            }
        )

        ListItem(
            headlineContent = {
                Text("Upload")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Upload,
                    contentDescription = null
                )
            },
            modifier = Modifier.clickable {
                onUploadClick()
            }
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
    }
}

