package br.thiago.moviemdb.presenter.auth.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.thiago.moviemdb.R

import br.thiago.moviemdb.databinding.ActivityAuthBinding
import br.thiago.moviemdb.presenter.auth.enums.AuthenticationDestinations
import br.thiago.moviemdb.presenter.main.activity.MainActivity
import br.thiago.moviemdb.util.FirebaseHelper
import br.thiago.moviemdb.util.getSerializableCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarTranslucent()

       initNavigation()

       isAuthenticated()
    }

    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val graph = navController.navInflater.inflate(R.navigation.auth_graph)
        graph.setStartDestination(getDestination())
        navController.graph = graph

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.onboardingFragment) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    private fun setStatusBarTranslucent() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun isAuthenticated() {
        if (FirebaseHelper.isAuthenticated()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun getDestination(): Int {
        val destination =
            intent.getSerializableCompat<AuthenticationDestinations>(AUTHENTICATION_PARAMETER)

        return when (destination) {
            AuthenticationDestinations.LOGIN_SCREEN -> {
                R.id.authentication
            }

            else -> {
                R.id.splashFragment
            }
        }
    }

    companion object {
        const val AUTHENTICATION_PARAMETER = "AUTHENTICATION_PARAMETER"
    }

}