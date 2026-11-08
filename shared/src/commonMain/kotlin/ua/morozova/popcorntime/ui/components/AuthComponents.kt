package ua.morozova.popcorntime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import popcorntime.shared.generated.resources.Res
import popcorntime.shared.generated.resources.app_name
import popcorntime.shared.generated.resources.footer_privacy
import popcorntime.shared.generated.resources.footer_restore
import popcorntime.shared.generated.resources.footer_terms
import popcorntime.shared.generated.resources.ic_login
import popcorntime.shared.generated.resources.ic_movie
import popcorntime.shared.generated.resources.ic_person_add
import popcorntime.shared.generated.resources.ic_visibility
import popcorntime.shared.generated.resources.ic_visibility_off
import popcorntime.shared.generated.resources.tab_create_account
import popcorntime.shared.generated.resources.tab_sign_in

@Composable
fun AuthTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CircleBackButton(onClick = onBackClick)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.ic_movie),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(Res.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun AuthChip(
    text: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(50)
    Row(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .border(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh, shape)
            .padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 11.sp,
            letterSpacing = 0.8.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

enum class AuthTab { SignIn, CreateAccount }

@Composable
fun AuthTabs(
    selected: AuthTab,
    onSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(18.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(1.dp, MaterialTheme.colorScheme.surfaceContainerHigh, shape)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AuthTabItem(
            text = stringResource(Res.string.tab_sign_in),
            icon = Res.drawable.ic_login,
            isSelected = selected == AuthTab.SignIn,
            onClick = onSignInClick
        )
        AuthTabItem(
            text = stringResource(Res.string.tab_create_account),
            icon = Res.drawable.ic_person_add,
            isSelected = selected == AuthTab.CreateAccount,
            onClick = onCreateAccountClick
        )
    }
}

@Composable
private fun RowScope.AuthTabItem(
    text: String,
    icon: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(14.dp)
    val contentColor = if (isSelected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)

    Row(
        modifier = Modifier
            .weight(1f)
            .height(44.dp)
            .clip(shape)
            .then(
                if (isSelected) Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f), shape)
                else Modifier
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = contentColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun AuthPrimaryButton(
    text: String,
    icon: DrawableResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(18.dp)
    val glow = MaterialTheme.colorScheme.primary.copy(alpha = 0.55f)
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .shadow(elevation = 16.dp, shape = shape, ambientColor = glow, spotColor = glow),
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PasswordVisibilityToggle(
    isVisible: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(
                if (isVisible) Res.drawable.ic_visibility_off else Res.drawable.ic_visibility
            ),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp)
        )
    }
}

/** Нижній рядок: Terms • Privacy • Restore. */
@Composable
fun AuthFooter(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        FooterText(stringResource(Res.string.footer_terms))
        FooterDot()
        FooterText(stringResource(Res.string.footer_privacy))
        FooterDot()
        FooterText(stringResource(Res.string.footer_restore))
    }
}

@Composable
private fun FooterText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun FooterDot() {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .size(3.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}
