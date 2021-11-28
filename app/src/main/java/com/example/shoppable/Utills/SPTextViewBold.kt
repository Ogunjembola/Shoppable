package com.example.shoppable.Utills

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class SPTextViewBold (context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    init {
        // Call the function to apply the font to the components.
        applyFont()

    }

    private fun applyFont() {
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)

    }
}