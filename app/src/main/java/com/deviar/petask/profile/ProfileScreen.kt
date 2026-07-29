package com.deviar.petask.profile

import android.net.Uri
import android.Manifest
import android.util.Log
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
import androidx.compose.material.icons.filled.ArrowCircleLeft
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
import androidx.compose.runtime.collectAsState
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
import com.deviar.petask.common.utils.createImageUri


@Composable
fun ProfileScreen(
    modifier: Modifier,
    profileViewModel: ProfileViewModel,
    userPfp: Painter
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("Pepe") }
    var isEditing by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if (uri != null) {
            profileViewModel.onProfileImageSelected(uri)
        }
    }
    val profileImageUri by profileViewModel.profileImageUri.collectAsState()

    LaunchedEffect(profileImageUri) {
        Log.d("PROFILE", "UI recibió: $profileImageUri")
    }

    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->

            Log.d("PROFILE", "success = $success")
            Log.d("PROFILE", "uri = $photoUri")

            if (success) {
                photoUri?.let {
                    profileViewModel.onProfileImageSelected(it)
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
            if (profileImageUri != null) {
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            showBottomSheet = true
                        },
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = userPfp,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            showBottomSheet = true
                        }
                )
            }
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
                            value = name,
                            onValueChange = { name = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    isEditing = false
                                }
                            )
                        )
                    } else {
                        Text(name)
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

