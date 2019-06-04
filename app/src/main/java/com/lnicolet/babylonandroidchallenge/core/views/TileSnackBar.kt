package com.lnicolet.babylonandroidchallenge.core.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.lnicolet.babylonandroidchallenge.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class TileSnackBar(
    parent: ViewGroup, content: TileSnackBarView
) : BaseTransientBottomBar<TileSnackBar>(parent, content, content) {

    private var customTileSnackbarView: TileSnackBarView? = null

    companion object {
        // ERROR DUE TO KOTLIN - CAN'T USE @IntDef({})
        const val SHORT = 0
        const val LONG = 1
        const val INDEFINITE = 2

        // TYPE TO COLOR THE TILE SNACKBAR
        const val TYPE_ERROR = 1
        const val TYPE_SUCCESS = 0

        fun make(
            view: View,
            @StringRes title: Int = -1,
            @StringRes message: Int = -1,
            @StringRes mainButtonText: Int = -1,
            @IntRange(from = 0, to = 1) type: Int = 0,
            duration: Int = 1,
            actionListener: View.OnClickListener? = null
        ): TileSnackBar {
            // First we find a suitable parent for our custom view
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            val customTileSnackbarView = LayoutInflater.from(view.context).inflate(
                R.layout.layout_tile_snackbar,
                parent,
                false
            ) as TileSnackBarView

            val tileSnackBar = TileSnackBar(
                parent,
                customTileSnackbarView
            )


            customTileSnackbarView.setOnCloseClickListner(View.OnClickListener {
                tileSnackBar.dismiss()
            })
            tileSnackBar.setTitle(title)
            tileSnackBar.setMessage(message)
            tileSnackBar.setMainButtonText(mainButtonText)
            tileSnackBar.setType(type)
            actionListener?.let { assignedListener ->
                tileSnackBar.setOnMainActionListener(assignedListener)
            }

            // First hide the close icon, then show it only if type is indefinite
            tileSnackBar.hideCloseIcon()
            tileSnackBar.duration = when (duration) {
                SHORT -> {
                    Snackbar.LENGTH_SHORT
                }
                LONG -> {
                    Snackbar.LENGTH_LONG
                }
                else -> {
                    // this is to prevent error tile snack bar to be closed without an action
                    if (type != TYPE_ERROR) tileSnackBar.showCloseIcon()
                    Snackbar.LENGTH_INDEFINITE
                }
            }
            return tileSnackBar
        }
    }

    init {
        view.setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        view.setPadding(0, 0, 0, 0)
        customTileSnackbarView = content
    }

    fun setOnMainActionListener(assignedListener: View.OnClickListener) {
        customTileSnackbarView?.setOnMainButtonClickListener(View.OnClickListener {
            assignedListener.onClick(it)
            dismiss()
        })
    }

    fun setTitle(@StringRes title: Int) {
        customTileSnackbarView?.setTitleText(title)
    }

    fun setMessage(@StringRes subtitle: Int) {
        customTileSnackbarView?.setSubtitleText(subtitle)
    }

    fun setMainButtonText(@StringRes buttonText: Int) {
        customTileSnackbarView?.setMainButtonText(buttonText)
    }

    fun setTitle(title: String) {
        customTileSnackbarView?.setTitleText(title)
    }

    fun setMessage(subtitle: String) {
        customTileSnackbarView?.setSubtitleText(subtitle)
    }

    fun setMainButtonText(buttonText: String) {
        customTileSnackbarView?.setMainButtonText(buttonText)
    }

    fun hideMainButton() {
        customTileSnackbarView?.hideMainButton()
    }

    fun showHideButton() {
        customTileSnackbarView?.showMainButton()
    }

    fun setType(@IntRange(from = 0, to = 1) type: Int = 0) {
        when (type) {
            TYPE_ERROR -> {
                customTileSnackbarView?.setErrorState()
            }
            TYPE_SUCCESS -> {
                customTileSnackbarView?.setSuccessState()
            }
        }
    }

    fun showCloseIcon() {
        customTileSnackbarView?.showCloseIcon()
    }

    fun hideCloseIcon() {
        customTileSnackbarView?.hideCloseIcon()
    }
}


// Thanks to https://medium.com/@fabionegri/make-snackbar-great-again-51edf7c940d4

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            // We've found a CoordinatorLayout, use it
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                // If we've hit the decor content view, then we didn't find a CoL in the
                // hierarchy, so use it.
                return view
            } else {
                // It's not the content view but we'll use it as our fallback
                fallback = view
            }
        }

        if (view != null) {
            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
    return fallback
}