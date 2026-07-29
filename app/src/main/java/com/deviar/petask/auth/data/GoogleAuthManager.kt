package com.deviar.petask.auth.data

import android.content.Context
import android.util.Log
import com.deviar.petask.R
import androidx.credentials.CredentialManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption

import androidx.credentials.CustomCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthManager(
    private val context: Context
){
    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    private val webClientId = context.getString(R.string.web_client_id)

    private fun buildGoogleSignInRequest(): GetCredentialRequest {

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(webClientId)
            .setFilterByAuthorizedAccounts(false)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }


    suspend fun signIn(): Boolean {

        return try {

            val result = credentialManager.getCredential(
                context = context,
                request = buildGoogleSignInRequest()
            )

            val credential = result.credential

            if (
                credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {

                val googleCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)

                val firebaseCredential =
                    GoogleAuthProvider.getCredential(
                        googleCredential.idToken,
                        null
                    )

                firebaseAuth.signInWithCredential(firebaseCredential).await()

                true

            } else {

                false
            }

        } catch (e: GoogleIdTokenParsingException) {
            Log.e("GoogleAuth", "Google login failed", e)
            false

        } catch (e: Exception) {
            Log.e("GoogleAuth", "Google login failed", e)
            false
        }
    }
}
