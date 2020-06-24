package com.example.bestset

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bestset.login.AuthViewModel
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import timber.log.Timber
import java.util.*


class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel by viewModels<AuthViewModel>()
    private var authStateFlag = false
    lateinit var currentLocale : Locale

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Timber.plant(Timber.DebugTree())
        currentLocale = getResources().getConfiguration().locale;

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_weight, R.id.nav_settings), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            if (authenticationState == AuthViewModel.AuthenticationState.UNAUTHENTICATED){
               authStateFlag = false
            }else if(authenticationState == AuthViewModel.AuthenticationState.AUTHENTICATED){
                authStateFlag = true
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val signInOption = menu.findItem(R.id.log_in)
        val signOutOption = menu.findItem(R.id.log_out)
        if(authStateFlag){
            signInOption.setVisible(false)
            signOutOption.setVisible(true)
        }else{
            signInOption.setVisible(true)
            signOutOption.setVisible(false)
        }
        val englishMenuItem = menu.findItem(R.id.switch_to_english)
        val chineseMenuItem = menu.findItem(R.id.switch_to_chinese)
        if (currentLocale == Locale.CHINESE) {
            englishMenuItem.setVisible(true)
            chineseMenuItem.setVisible(false)
        }else if(currentLocale == Locale.ENGLISH){
            englishMenuItem.setVisible(false)
            chineseMenuItem.setVisible(true)
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.log_in -> startSignInFlow()
            R.id.log_out -> signOut()
//            R.id.action_settings -> navigateToSettings()
            R.id.switch_to_chinese -> updateLocale(Locale.CHINESE)
            R.id.switch_to_english -> updateLocale(Locale.ENGLISH)
        }
        return super.onOptionsItemSelected(item)
    }

    fun startSignInFlow(){
        var SIGN_IN_CODE = 1010
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_CODE
        )
    }

    private fun signOut(){
        AuthUI.getInstance().signOut(applicationContext)
    }

    override fun updateLocale(locale: Locale) {
        super.updateLocale(locale)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
