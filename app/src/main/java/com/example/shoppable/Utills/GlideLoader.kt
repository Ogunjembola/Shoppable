package com.example.shoppable.Utills

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shoppable.R
import java.io.IOException

class GlideLoader (val context: Context) {


    fun loadUserPicture(image: Any, imageView: ImageView){
        try {
            //Load the user image in the Imageview
            Glide
                .with(context)
                .load(Uri.parse(image.toString()))// URi of the image
                .centerCrop()// scale type of the image.
                .placeholder(R.drawable.ic_user_placeholder)// defailt place holder if the image fails to load
                .into(imageView)// the view in which the image will be loaded
        }catch (e: IOException){
            e.printStackTrace()
        }
    }


    /**
     * A function to load image from Uri or URL for the product image.
     */
    fun loadProductPicture(image: Any, imageView: ImageView) {
        try {
            // Load the user image in the ImageView.
            Glide
                .with(context)
                .load(image) // Uri or URL of the image
                .centerCrop() // Scale type of the image.
                .into(imageView) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}