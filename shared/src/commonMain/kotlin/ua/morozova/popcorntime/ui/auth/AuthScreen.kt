package ua.morozova.popcorntime.ui.auth

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import popcorntime.shared.generated.resources.*
import ua.morozova.popcorntime.ui.auth.AuthContract.Effect
import ua.morozova.popcorntime.ui.auth.AuthContract.Intent
import ua.morozova.popcorntime.ui.auth.AuthContract.State
import ua.morozova.popcorntime.ui.components.AuthChip
import ua.morozova.popcorntime.ui.components.AuthFooter
import ua.morozova.popcorntime.ui.components.AuthPrimaryButton
import ua.morozova.popcorntime.ui.components.AuthTab
import ua.morozova.popcorntime.ui.components.AuthTabs
import ua.morozova.popcorntime.ui.components.AuthTopBar
import ua.morozova.popcorntime.ui.components.LabeledTextField
import ua.morozova.popcorntime.ui.components.PasswordVisibilityToggle
import ua.morozova.popcorntime.ui.components.message

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            AuthTopBar(
                onBackClick = { onIntent(Intent.BackClicked) },
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                AuthChip(text = stringResource(Res.string.auth_chip))

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(Res.string.auth_heading),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 30.sp,
                    lineHeight = 36.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(Res.string.auth_description),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                AuthTabs(
                    selected = AuthTab.SignIn,
                    onSignInClick = { },
                    onCreateAccountClick = { onIntent(Intent.SignUpClicked) }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
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

                        Spacer(modifier = Modifier.height(16.dp))

                        LabeledTextField(
                            label = stringResource(Res.string.auth_password_label),
                            value = state.password,
                            onValueChange = { onIntent(Intent.PasswordChanged(it)) },
                            errorMessage = state.passwordError?.message(),
                            visualTransformation = if (state.isPasswordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            labelTrailing = {
                                Text(
                                    text = stringResource(Res.string.forgot_password),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            trailing = {
                                PasswordVisibilityToggle(
                                    isVisible = state.isPasswordVisible,
                                    onClick = { onIntent(Intent.TogglePasswordVisibility) }
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = passwordActions
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        AuthPrimaryButton(
                            text = stringResource(Res.string.btn_sign_in),
                            icon = Res.drawable.ic_movie,
                            onClick = {
                                keyboard?.hide()
                                focusManager.clearFocus()
                                onIntent(Intent.SignInClicked)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            AuthFooter(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
        }
    }
}
