package g.sig.sportsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import g.sig.core.ui.SportsAppTheme
import g.sig.sportsapp.navigation.SportsNavHost

class SportsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            SportsAppTheme {
                SportsNavHost()
            }
        }
    }
}
