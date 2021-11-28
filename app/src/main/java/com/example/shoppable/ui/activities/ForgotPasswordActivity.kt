package com.example.shoppable.ui.activities

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.example.shoppable.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        setupActionbar()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())

        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val submit_btn: Button = findViewById(R.id.submit_btn)

        submit_btn.setOnClickListener {

            resetPassword()
        }
    } private fun setupActionbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_forgot_password_activity)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)

        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

    }
    /* private fun resettPassword(){
         val submit_btn: Button= findViewById(R.id.submit_btn)

         submit_btn.setOnClickListener{

             resetPassword()
         }
     }*/

    private fun resetPassword(): Boolean {

        val email: EditText = findViewById(R.id.email_forgotpass)




        return when {

            TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_email), true)
                false
            }


            else -> {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.toString())
                    .addOnCompleteListener { task ->
                        hideProgressDialog()
                    }
                showErrorSnackBar(resources.getString(R.string.forgotpass), false)
                true
            }
        }
    }

}
