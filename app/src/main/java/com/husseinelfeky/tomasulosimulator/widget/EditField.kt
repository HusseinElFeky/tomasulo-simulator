package com.husseinelfeky.tomasulosimulator.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.getIntOrThrow
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import com.husseinelfeky.tomasulosimulator.R
import kotlinx.android.synthetic.main.view_edit_field.view.*

class EditField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    fun interface TextValidator {
        fun validate(text: String): Boolean
    }

    private var textValidator: TextValidator? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_edit_field, this, true)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.EditField,
            defStyleAttr,
            0
        )
        with(typedArray) {
            text_input_layout.hint = getString(R.styleable.EditField_android_hint)

            text_input_layout.endIconMode = getInteger(
                R.styleable.EditField_endIconMode,
                END_ICON_NONE
            )
            if (hasValue(R.styleable.EditField_endIconDrawable)) {
                text_input_layout.endIconDrawable = getDrawable(
                    R.styleable.EditField_endIconDrawable
                )
            }

            edit_text.inputType = getInt(
                R.styleable.EditField_android_inputType,
                InputType.TYPE_CLASS_TEXT
            )

            if (hasValue(R.styleable.EditField_android_maxLength)) {
                edit_text.filters = arrayOf(
                    InputFilter.LengthFilter(getIntOrThrow(R.styleable.EditField_android_maxLength))
                )
            }

            edit_text.setLines(getInt(R.styleable.EditField_android_lines, 1))

            text_input_layout.isEnabled = getBoolean(R.styleable.EditField_android_enabled, true)
            if (hasValue(R.styleable.EditField_inputEnabled)) {
                setInputEnabled(getBoolean(R.styleable.EditField_inputEnabled, true))
            }
            text_input_layout.isErrorEnabled = getBoolean(R.styleable.EditField_errorEnabled, true)
        }
        typedArray.recycle()
    }

    override fun setEnabled(isEnabled: Boolean) {
        text_input_layout.isEnabled = isEnabled
    }

    private fun setInputEnabled(isInputEnabled: Boolean) {
        val textColor = edit_text.currentTextColor
        edit_text.isEnabled = isInputEnabled
        edit_text.setTextColor(textColor)
    }

    fun getText(): Editable? {
        return edit_text.text
    }

    fun setText(@StringRes resourceId: Int, validateText: Boolean = false) {
        edit_text.setText(resourceId)
        if (validateText) {
            textValidator?.validate(getText().toString())
        }
    }

    fun setText(text: CharSequence, validateText: Boolean = false) {
        edit_text.setText(text)
        if (validateText) {
            textValidator?.validate(getText().toString())
        }
    }

    fun getHint(): CharSequence? {
        return text_input_layout.hint
    }

    fun setHint(@StringRes resourceId: Int) {
        setHint(context.getString(resourceId))
    }

    private fun setHint(text: CharSequence) {
        text_input_layout.hint = text
    }

    fun showError(@StringRes resourceId: Int) {
        showError(context.getString(resourceId))
    }

    fun showError(text: CharSequence) {
        text_input_layout.error = text
    }

    fun hideError() {
        text_input_layout.error = null
    }

    fun setEndIconMode(@TextInputLayout.EndIconMode endIconMode: Int) {
        text_input_layout.endIconMode = endIconMode
    }

    fun setEndIconDrawableResource(@DrawableRes resourceId: Int) {
        setEndIconDrawable(ContextCompat.getDrawable(context, resourceId))
    }

    private fun setEndIconDrawable(endIconDrawable: Drawable?) {
        text_input_layout.endIconDrawable = endIconDrawable
    }

    fun setEndIconOnClickListener(clickListener: OnClickListener) {
        text_input_layout.setEndIconOnClickListener(clickListener)
    }

    fun setEndIconOnClickListener(clickListener: (View) -> Unit) {
        text_input_layout.setEndIconOnClickListener(clickListener)
    }

    fun setOnClickListener(clickListener: (View) -> Unit) {
        edit_text.isFocusable = false
        edit_text.isClickable = true
        edit_text.setOnClickListener(clickListener)
    }

    inline fun addTextChangedListener(
        crossinline beforeTextChanged: (
            text: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) -> Unit = { _, _, _, _ -> },
        crossinline onTextChanged: (
            text: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) -> Unit = { _, _, _, _ -> },
        crossinline afterTextChanged: (text: Editable?) -> Unit = {}
    ): TextWatcher {
        return edit_text.addTextChangedListener(beforeTextChanged, onTextChanged, afterTextChanged)
    }

    private fun setOnInputTypedListener(onInputTyped: (String) -> Unit) {
        var isTextWritten = false

        edit_text.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && isTextWritten) {
                onInputTyped.invoke(getText().toString())
            }
        }

        edit_text.addTextChangedListener {
            isTextWritten = true
        }
    }

    fun setTextValidator(textValidator: TextValidator) {
        this.textValidator = textValidator
        setOnInputTypedListener {
            textValidator.validate(it)
        }
    }

    fun isInputValid(): Boolean {
        return textValidator?.validate(getText().toString()) ?: true
    }
}
