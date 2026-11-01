package ua.morozova.popcorntime.ui.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import popcorntime.shared.generated.resources.*
import ua.morozova.popcorntime.ui.components.AppLogo
import ua.morozova.popcorntime.ui.components.CircleBackButton
import ua.morozova.popcorntime.ui.components.LabeledTextField
import ua.morozova.popcorntime.ui.components.PrimaryButton
import ua.morozova.popcorntime.ui.components.message
import ua.morozova.popcorntime.ui.register.RegisterContract.Effect
import ua.morozova.popcorntime.ui.register.RegisterContract.Intent
import ua.morozova.popcorntime.ui.register.RegisterContract.State

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentOnBack by rememberUpdatedState(onNavigateBack)

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                Effect.NavigateBack -> currentOnBack()
            }
        }
    }

    RegisterContent(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun RegisterContent(
    state: State,
    onIntent: (Intent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    val nextAction = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
    val doneAction = KeyboardActions(
        onDone = {
            keyboard?.hide()
            focusManager.clearFocus()
            onIntent(Intent.RegisterClicked)
        }
    )

    val passwordTransformation =
        if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleBackButton(onClick = { onIntent(Intent.BackClicked) })
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AppLogo()

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(Res.string.register_title),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = stringResource(Res.string.register_subtitle),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            LabeledTextField(
                                label = stringResource(Res.string.auth_username_label),
                                value = state.username,
                                onValueChange = { onIntent(Intent.UsernameChanged(it)) },
                                errorMessage = state.usernameError?.message(),
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.None,
                                    autoCorrectEnabled = false,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = nextAction
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LabeledTextField(
                                label = stringResource(Res.string.register_email_label),
                                value = state.email,
                                onValueChange = { onIntent(Intent.EmailChanged(it)) },
                                errorMessage = state.emailError?.message(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    autoCorrectEnabled = false,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = nextAction
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LabeledTextField(
                                label = stringResource(Res.string.auth_password_label),
                                value = state.password,
                                onValueChange = { onIntent(Intent.PasswordChanged(it)) },
                                errorMessage = state.passwordError?.message(),
                                visualTransformation = passwordTransformation,
                                trailing = {
                                    Text(
                                        text = if (state.isPasswordVisible) "🙈" else "👁",
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable { onIntent(Intent.TogglePasswordVisibility) }
                                            .padding(12.dp)
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = nextAction
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LabeledTextField(
                                label = stringResource(Res.string.register_confirm_label),
                                value = state.confirmPassword,
                                onValueChange = { onIntent(Intent.ConfirmPasswordChanged(it)) },
                                errorMessage = state.confirmPasswordError?.message(),
                                visualTransformation = passwordTransformation,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = doneAction
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            PrimaryButton(
                                text = stringResource(Res.string.btn_register),
                                onClick = {
                                    keyboard?.hide()
                                    focusManager.clearFocus()
                                    onIntent(Intent.RegisterClicked)
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(Res.string.have_account_question),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = stringResource(Res.string.btn_sign_in),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.clickable { onIntent(Intent.SignInClicked) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
