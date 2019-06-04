package com.lnicolet.babylonandroidchallenge.core.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.lnicolet.babylonandroidchallenge.R
import com.lnicolet.babylonandroidchallenge.core.gone
import com.lnicolet.babylonandroidchallenge.core.visible
import com.google.android.material.snackbar.ContentViewCallback
import kotlinx.android.synthetic.main.view_tile_snackbar.view.*

class TileSnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ContentViewCallback {

    override fun animateContentOut(p0: Int, p1: Int) {
        val scaleX = ObjectAnimator.ofFloat(cv_container, View.SCALE_X, 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(cv_container, View.SCALE_Y, 1f, 0f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            duration = 500
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    override fun animateContentIn(p0: Int, p1: Int) {
        val scaleX = ObjectAnimator.ofFloat(cv_container, View.SCALE_X, 0.5f, 1f)
        val scaleY = ObjectAnimator.ofFloat(cv_container, View.SCALE_Y, 0.5f, 1f)
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
        if (title != -1) tv_title.text = context.getString(title)
    }

    fun setSubtitleText(@StringRes subtitle: Int) {
        if (subtitle != -1) tv_sub_title.text = context.getString(subtitle)
    }

    fun setMainButtonText(@StringRes mainButtonText: Int) {
        if (mainButtonText != -1) btn_main_action.text = context.getString(mainButtonText)
    }

    fun setTitleText(title: String) {
        tv_title.text = title
    }

    fun setSubtitleText(subtitle: String) {
        tv_sub_title.text = subtitle
    }

    fun setMainButtonText(mainButtonText: String) {
        btn_main_action.text = mainButtonText
    }

    fun hideCloseIcon() {
        btn_close_action.gone()
    }

    fun showCloseIcon() {
        btn_close_action.visible()
    }

    fun hideMainButton() {
        btn_main_action.gone()
    }

    fun showMainButton() {
        btn_main_action.visible()
    }

    fun setOnMainButtonClickListener(listener: OnClickListener) {
        btn_main_action.setOnClickListener(listener)
    }

    fun setOnCloseClickListner(listener: OnClickListener) {
        btn_close_action.setOnClickListener(listener)
    }

    fun setErrorState() {
        cv_container.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.tile_snack_bar_error_state
            )
        )
    }

    fun setSuccessState() {
        cv_container.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.tile_snack_bar_success_state
            )
        )
    }
}