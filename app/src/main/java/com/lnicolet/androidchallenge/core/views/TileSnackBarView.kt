package com.lnicolet.androidchallenge.core.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.lnicolet.androidchallenge.R
import com.lnicolet.androidchallenge.core.gone
import com.lnicolet.androidchallenge.core.visible
import com.google.android.material.snackbar.ContentViewCallback
import com.lnicolet.androidchallenge.core.inflater
import com.lnicolet.androidchallenge.databinding.ViewTileSnackbarBinding

class TileSnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ContentViewCallback {

    val binding: ViewTileSnackbarBinding = ViewTileSnackbarBinding.inflate(context.inflater, this)

    override fun animateContentOut(p0: Int, p1: Int) {
        val scaleX = ObjectAnimator.ofFloat(binding.cvContainer, View.SCALE_X, 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(binding.cvContainer, View.SCALE_Y, 1f, 0f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            duration = 500
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    override fun animateContentIn(p0: Int, p1: Int) {
        val scaleX = ObjectAnimator.ofFloat(binding.cvContainer, View.SCALE_X, 0.5f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.cvContainer, View.SCALE_Y, 0.5f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            duration = 500
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    init {
        View.inflate(context, R.layout.view_tile_snackbar, this)
    }

    fun setTitleText(@StringRes title: Int) {
        if (title != -1) binding.tvTitle.text = context.getString(title)
    }

    fun setSubtitleText(@StringRes subtitle: Int) {
        if (subtitle != -1) binding.tvSubTitle.text = context.getString(subtitle)
    }

    fun setMainButtonText(@StringRes mainButtonText: Int) {
        if (mainButtonText != -1) binding.btnMainAction.text = context.getString(mainButtonText)
    }

    fun setTitleText(title: String) {
        binding.tvTitle.text = title
    }

    fun setSubtitleText(subtitle: String) {
        binding.tvSubTitle.text = subtitle
    }

    fun setMainButtonText(mainButtonText: String) {
        binding.btnMainAction.text = mainButtonText
    }

    fun hideCloseIcon() {
        binding.btnCloseAction.gone()
    }

    fun showCloseIcon() {
        binding.btnCloseAction.visible()
    }

    fun hideMainButton() {
        binding.btnMainAction.gone()
    }

    fun showMainButton() {
        binding.btnMainAction.visible()
    }

    fun setOnMainButtonClickListener(listener: OnClickListener) {
        binding.btnMainAction.setOnClickListener(listener)
    }

    fun setOnCloseClickListener(listener: OnClickListener) {
        binding.btnCloseAction.setOnClickListener(listener)
    }

    fun setErrorState() {
        binding.cvContainer.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.tile_snack_bar_error_state
            )
        )
    }

    fun setSuccessState() {
        binding.cvContainer.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.tile_snack_bar_success_state
            )
        )
    }
}