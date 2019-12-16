package com.lambdaschool.hackathon_portal

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var auth0: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navController = findNavController(R.id.nav_host_fragment)
//        nav_host_fragment.setupWithNavController(navController)

    }

    private fun login() {
        WebAuthProvider.login(auth0!!)
            .withScheme("demo")
            .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
            .start(this, object : AuthCallback {
                override fun onFailure(dialog: Dialog) {
                    runOnUiThread { dialog.show() }
                }
                override fun onFailure(exception: AuthenticationException) {
                    runOnUiThread { Toast.makeText(this@MainActivity, "Error: " + exception.message, Toast.LENGTH_SHORT).show() }
                }
                override fun onSuccess(credentials: Credentials) {
                    runOnUiThread {
                        val intent = Intent(this@MainActivity, MainActivity::class.java)
                        intent.putExtra(EXTRA_ACCESS_TOKEN, credentials.accessToken)
                        startActivity(intent)
                        finish()
                    }
                }
            })
    }
    private fun logout() {
        WebAuthProvider.logout(auth0!!)
            .withScheme("demo")
            .start(this, object : VoidCallback {
                override fun onSuccess(payload: Void) {}
                override fun onFailure(error: Auth0Exception) { //Log out canceled, keep the user logged in
                    showNextActivity()
                }
            })
    }
    private fun showNextActivity() {
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object {
        const val EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS"
        const val EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN"
    }
}

