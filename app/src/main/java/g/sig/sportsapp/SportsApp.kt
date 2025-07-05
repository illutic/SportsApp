package g.sig.sportsapp

import android.app.Application
import g.sig.core.data.dataModule
import g.sig.sportsapp.features.home.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SportsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SportsApp)
            modules(dataModule)
            modules(homeModule)
        }
    }
}
