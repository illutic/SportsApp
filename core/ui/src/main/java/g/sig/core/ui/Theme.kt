package g.sig.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

@Composable
fun SportsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography(),
        content = content,
    )
}
