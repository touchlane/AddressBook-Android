package com.touchlane.addressbook.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.touchlane.addressbook.R
import com.touchlane.addressbook.databinding.ViewTextSectionBinding

class TextSection(context: Context,
                  attrs: AttributeSet? = null,
                  defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context, set: AttributeSet?) : this(context, set, 0)

    private val binding: ViewTextSectionBinding

    init {
        binding = ViewTextSectionBinding.inflate(LayoutInflater.from(context), this, true)
        orientation = VERTICAL

        attrs?.run {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextSection)

            val maxLines = typedArray.getInteger(R.styleable.TextSection_maxSectionLines, MAX_LINES_NOT_SET)
            if (maxLines != MAX_LINES_NOT_SET) {
                binding.sectionText.maxLines = maxLines
            }

            typedArray.recycle()
        }
    }

    fun setText(text: CharSequence?) {
        binding.sectionText.text = text
    }

    companion object {

        private const val MAX_LINES_NOT_SET = -1

        @BindingAdapter("app:text")
        @JvmStatic
        fun setSectionText(
            textSection: TextSection,
            textToBind: CharSequence?
        ) {
            textSection.setText(textToBind)
        }
    }
}