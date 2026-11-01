package ua.morozova.popcorntime.ui.auth

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
import ua.morozova.popcorntime.domain.ValidationError
import ua.morozova.popcorntime.ui.auth.AuthContract.Effect
import ua.morozova.popcorntime.ui.auth.AuthContract.Intent
import ua.morozova.popcorntime.ui.auth.AuthContract.State
import ua.morozova.popcorntime.ui.components.AppLogo
import ua.morozova.popcorntime.ui.components.CircleBackButton
import ua.morozova.popcorntime.ui.components.LabeledTextField
import ua.morozova.popcorntime.ui.components.PrimaryButton
import ua.morozova.popcorntime.ui.components.SecondaryButton
import ua.morozova.popcorntime.ui.components.TextDivider

@Composable
fun AuthScreen(
    onNavigateToHome: (username: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentOnHome by rememberUpdatedState(onNavigateToHome)
    val currentOnRegister by rememberUpdatedState(onNavigateToRegister)
    val currentOnBack by rememberUpdatedState(onNavigateBack)

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is Effect.NavigateToHome -> currentOnHome(effect.username)
                Effect.NavigateToRegister -> currentOnRegister()
                Effect.NavigateBack -> currentOnBack()
            }
        }
    }

    AuthContent(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun AuthContent(
    state: State,
    onIntent: (Intent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    val usernameActions = KeyboardActions(
        onNext = { focusManager.moveFocus(FocusDirection.Next) }
    )
    val passwordActions = KeyboardActions(
        onDone = {
            keyboard?.hide()
            focusManager.clearFocus()
            onIntent(Intent.SignInClicked)
        }
    )

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
                                text = stringResource(Res.string.auth_title),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = stringResource(Res.string.auth_subtitle),
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
                                    keyboardType = KeyboardType.Text,
                                    capitalization = KeyboardCapitalization.None,
                                    autoCorrectEnabled = false,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = usernameActions
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LabeledTextField(
                                label = stringResource(Res.string.auth_password_label),
                                value = state.password,
                                onValueChange = { onIntent(Intent.PasswordChanged(it)) },
                                errorMessage = state.passwordError?.message(),
                                visualTransformation = if (state.isPasswordVisible)
                                    VisualTransformation.None
                                else
                                    PasswordVisualTransformation(),
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
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = passwordActions
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            PrimaryButton(
                                text = stringResource(Res.string.btn_sign_in),
                                onClick = {
                                    keyboard?.hide()
                                    focusManager.clearFocus()
                                    onIntent(Intent.SignInClicked)
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            TextDivider(text = stringResource(Res.string.or_divider))

                            Spacer(modifier = Modifier.height(16.dp))

                            SecondaryButton(
                                text = stringResource(Res.string.btn_signup),
                                onClick = { onIntent(Intent.SignUpClicked) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ValidationError.message(): String = stringResource(
    when (this) {
        ValidationError.UsernameEmpty -> Res.string.error_username_empty
        ValidationError.UsernameTooShort -> Res.string.error_username_short
        ValidationError.UsernameInvalidChars -> Res.string.error_username_chars
        ValidationError.PasswordEmpty -> Res.string.error_password_empty
        ValidationError.PasswordTooShort -> Res.string.error_password_short
        ValidationError.PasswordNoLetter -> Res.string.error_password_letter
        ValidationError.PasswordNoDigit -> Res.string.error_password_digit
    }
)