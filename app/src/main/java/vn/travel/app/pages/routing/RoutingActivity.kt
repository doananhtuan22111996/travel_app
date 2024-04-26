package vn.travel.app.pages.routing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import vn.travel.app.pages.main.MainActivity

/**
Follow by: https://developer.android.com/develop/ui/views/launch/splash-screen/migrate
SDK < 31: We need to RoutingActivity to transform splash theme to application theme
SDK >= 31: postSplashScreenTheme will handle it, too
 */
class RoutingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        val intent = Intent(this@RoutingActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}