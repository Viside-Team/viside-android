package com.vside.app.feature.common.view

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.vside.app.databinding.LayoutToastBinding

object VSideToast {
    private fun createShortToast(context: Context, message: String): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: LayoutToastBinding = LayoutToastBinding.inflate(inflater).apply {
            tvToastText.text = message
        }

        return Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    fun createShortCenterToast(context: Context, message: String): Toast =
        createShortToast(context, message).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }


    fun createShortCenterHorizontalToast(context: Context, message: String): Toast =
        createShortToast(context, message)
}