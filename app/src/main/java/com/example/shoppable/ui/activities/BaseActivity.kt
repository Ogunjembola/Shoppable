package com.example.shoppable.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.shoppable.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce =false
    private lateinit var mProgressDialog: Dialog
    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }

    fun showProgressDialog(string: String) {


        mProgressDialog = Dialog(this)


        /* Set the screen content  from layout resources.
        the resource will be inflated, adding  all top- level views  to the screen.
         */
        mProgressDialog.setContentView(R.layout.dialog_progress)


        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen
        mProgressDialog.show()

    }

    fun hideProgressDialog() {
        // Dissmiss dialog removing it from the screen
        mProgressDialog.dismiss()
    }
    fun doubleBackExit(){
        if (doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()
        @Suppress("DEPRECATION")
        Handler().postDelayed({doubleBackToExitPressedOnce= false},2000)
    }
}