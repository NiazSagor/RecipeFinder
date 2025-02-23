package com.example.recipefinder.ui.signin

import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SignInScreen(
    paddingValues: PaddingValues,
    onAuthComplete: () -> Unit
) {
    Scaffold(
        modifier = Modifier.padding(paddingValues)
    ) { paddingValues ->
        SignInContent(paddingValues = paddingValues, onAuthComplete = onAuthComplete)
    }
}


@Composable
fun SignInContent(
    paddingValues: PaddingValues,
    onAuthComplete: () -> Unit
) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
            onAuthComplete()
        },
        onAuthError = {
            user = null
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.smiling_face),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Log in or create an account to save your favorite recipes",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
            Button(
                onClick = {
                    val gso =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()

                    val googleSignInClient =
                        GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Sign In",
                )
            }
        }
    }
}

@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, androidx.activity.result.ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            onAuthError(e)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        paddingValues = PaddingValues()
    ) {

    }
}
