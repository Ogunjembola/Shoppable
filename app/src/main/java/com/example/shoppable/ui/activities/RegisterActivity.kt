package com.example.shoppable.ui.activities

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.shoppable.Firestore.FirestoreClass
import com.example.shoppable.R
import com.example.shoppable.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())

        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        val tv_login: TextView = findViewById(R.id.tv_login)
        tv_login.setOnClickListener {
            onBackPressed()
        }
       setupActionBar()
       val btn_register: TextView = findViewById(R.id.btn_register)
        btn_register.setOnClickListener {
           registerUser()

        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {
        val toolbarr: Toolbar =findViewById(R.id.toolbar_register_activity)
        setSupportActionBar(toolbarr)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
        }
        toolbarr.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegistrationDetails(): Boolean {
        val firstname: EditText = findViewById(R.id.et_first_name)
        val lastname: EditText = findViewById(R.id.et_last_name)
        val email: EditText = findViewById(R.id.et_email)
        val password: EditText = findViewById(R.id.et_password)
        val confirmPassword: EditText = findViewById(R.id.et_confirm_password)
        val cb_terms_and_condition: CheckBox = findViewById(R.id.cb_terms_and_condition)
        return when {
            TextUtils.isEmpty(firstname.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_first_name), true)
                false
            }

            TextUtils.isEmpty(lastname.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_last_name), true)
                false
            }

            TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_email), true)
                false
            }

            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_password), true)
                false
            }
            TextUtils.isEmpty(confirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_confirm_password), true)
                false
            }
            password.text.toString().trim { it <= ' ' } != confirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.error_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.error_msg_agree_terms),
                    true
                )
                false
            }

            else -> {
                showErrorSnackBar("Your details are valid ", false)
                true
            }


        }
    }


    /**
     * A function to register the user with email and password using FirebaseAuth.
     */

     private fun registerUser() {

         // Check with validate function if the entries are valid or not.
         if (validateRegistrationDetails()) {

             // Show the progress dialog.
             showProgressDialog(resources.getString(R.string.please_wait))
             val et_email: EditText = findViewById(R.id.et_email)
             val et_password: EditText = findViewById(R.id.et_password)
             val et_first_name: EditText = findViewById(R.id.et_first_name)
             val et_last_name: EditText = findViewById(R.id.et_last_name)
             val email: String = et_email.text.toString().trim { it <= ' ' }
             val password: String = et_password.text.toString().trim { it <= ' ' }

             // Create an instance and create a register a user with email and password.
             FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(
                     OnCompleteListener<AuthResult> { task ->

                         // Hide the progress dialog
                         // hideProgressDialog()

                         // If the registration is successfully done
                         if (task.isSuccessful) {

                             // Firebase registered user
                             val firebaseUser: FirebaseUser = task.result!!.user!!

                             val user = User(
                                 firebaseUser.uid,
                                 et_first_name.text.toString().trim { it <= ' ' },
                                 et_last_name.text.toString().trim { it <= ' ' },
                                 et_email.text.toString().trim { it <= ' ' }
                             )


                             /**
                              * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
                              * and send him to Login Screen.
                              */


                             /**
                              * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
                              * and send him to Login Screen.
                              */
                             FirestoreClass().registerUser(this@RegisterActivity, user)
                             FirebaseAuth.getInstance().signOut()
                             // Finish the Register Screen
                             finish()
                         } else {
                             hideProgressDialog()
                             // If the registering is not successful then show error message.
                             showErrorSnackBar(task.exception!!.message.toString(), true)
                         }
                     })
         }
     }

     fun userRegistrationSuccess() {

         // Hide the progress dialog
         hideProgressDialog()

         // TODO Step 5: Replace the success message to the Toast instead of Snackbar.
         Toast.makeText(
             this@RegisterActivity,
             resources.getString(R.string.registry_successful),
             Toast.LENGTH_SHORT
         ).show()


         /**
          * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
          * and send him to Intro Screen for Sign-In
          */
         FirebaseAuth.getInstance().signOut()
         // Finish the Register Screen
         finish()
     }
}