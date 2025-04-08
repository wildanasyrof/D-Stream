package com.wldnasyrf.ds.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.wldnasyrf.ds.R

class CategoryChipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setupView()
    }

    private fun setupView() {
        // Default styling
        background = ContextCompat.getDrawable(context, R.drawable.category_chip_bg)
        setTextColor(ContextCompat.getColor(context, R.color.text_primary))
        textSize = 14f
        gravity = Gravity.CENTER

        // Padding (internal spacing)
        val horizontalPadding = resources.getDimensionPixelSize(R.dimen.spacing_sm)
        val verticalPadding = resources.getDimensionPixelSize(R.dimen.spacing_xs)
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)

        // Set default margin (external spacing - 4dp)
        val horizontalMargin = resources.getDimensionPixelSize(R.dimen.spacing_xs)
        layoutParams = (layoutParams ?: LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )).apply {
            (this as? ViewGroup.MarginLayoutParams)?.let {
                it.marginStart = horizontalMargin
                it.marginEnd = horizontalMargin
            }
        }
    }
}