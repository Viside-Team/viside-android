package com.vside.app.feature.auth.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.vside.app.R
import com.vside.app.databinding.ViewLoginBtnBinding

class LoginBtnView: ConstraintLayout {
    private lateinit var viewDataBinding: ViewLoginBtnBinding

    constructor(context: Context) : super(context) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet? = null) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.view_login_btn, this, false)

        attrs?.let {
            val typedArr = context.obtainStyledAttributes(attrs, R.styleable.LoginBtnView)

            val bgColor = typedArr.getColor(R.styleable.LoginBtnView_loginBgColor, ContextCompat.getColor(context, R.color.kakao_color))
            viewDataBinding.cvLoginBtn.setCardBackgroundColor(bgColor)

            val iconDrawable = typedArr.getDrawable(R.styleable.LoginBtnView_loginIcon)
            viewDataBinding.tvLoginBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(iconDrawable, null, null, null)

            val text = typedArr.getString(R.styleable.LoginBtnView_loginText)
            viewDataBinding.tvLoginBtn.text = text

            val textColor = typedArr.getColor(R.styleable.LoginBtnView_loginTextColor, ContextCompat.getColor(context, R.color.black))
            viewDataBinding.tvLoginBtn.setTextColor(textColor)

            typedArr.recycle()
        }

        addView(viewDataBinding.root)
    }
}