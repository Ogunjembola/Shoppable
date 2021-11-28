package com.example.shoppable.Utills

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class SPEditText(context: Context, attrs: AttributeSet): AppCompatEditText(context,attrs){
    init {
        // Call the function to apply the font to the components.
        applyFont()

    }

    private fun applyFont() {
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        setTypeface(typeface)

    }
}