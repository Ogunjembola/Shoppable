package com.example.shoppable.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.shoppable.Firestore.FirestoreClass
import com.example.shoppable.R
import com.example.shoppable.models.User
import com.example.shoppable.Utills.Constants
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())

        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        val login: Button = findViewById(R.id.btn_login)
        val forgot_password: TextView = findViewById(R.id.tv_forgot_password)
        val register: TextView = findViewById(R.id.tv_register)

// Click event assigned to Forgot Password text.
        forgot_password.setOnClickListener(this)
        // Click event assigned to Login button.
        login.setOnClickListener(this)
        // Click event assigned to Register text.
        register.setOnClickListener(this)
    }

    /**
     * In Login screen the clickable components are Login Button, ForgotPassword text and Register Text.
     */
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_forgot_password -> {

                    // Launch the forgot password screen when the user clicks on the forgot password text.
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {

                    loginRegisteredUser()
                }

                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    /**
     * A function to validate the login entries of a user.
     */
    private fun vallidateLoginDeatails(): Boolean {

        val email: EditText = findViewById(R.id.email_edt)
        val password: EditText = findViewById(R.id.et_password)



        return when {

            TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_email), true)
                false
            }
            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_password), true)
                false

            }
            else -> {
                // showErrorSnackBar(resources.getString(R.string.successful_login),false)
                true
            }

        }
    }

    fun userLoggedInSuccess(user: User) {

        // Hide the progress dialog.
        hideProgressDialog()

        // Print the user details in the log as of now.
        /* Log.i("First Name: ", user.firstName)
         Log.i("Last Name: ", user.lastName)
         Log.i("Email: ", user.email)*/

        if (user.profileCompleted == 0) {
            // If the user profile is incomplete then launch the UserProfileActivity.
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else {
            // Redirect the user to Main Screen after log in.
            startActivity(Intent(this@LoginActivity, DashActivity::class.java))
        }
        finish()
    }

    private fun loginRegisteredUser() {
        val email_edt: EditText = findViewById(R.id.email_edt)
        val et_password: EditText = findViewById(R.id.et_password)

        if (vallidateLoginDeatails()) {

            //show the progress dialog
            showProgressDialog(resources.getString(R.string.please_wait))

            val email = email_edt.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }

            //login to FIrebase
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    //hideprogress bar
                    //  hideProgressDialog()

                    if (task.isSuccessful) {
                        FirestoreClass().getUserDetails(this@LoginActivity)
                        //showErrorSnackBar(resources.getString(R.string.successful_login),false)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }

                }
        }
    }

}