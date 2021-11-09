package com.lnicolet.androidchallenge.postdetails.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.lnicolet.androidchallenge.R
import com.lnicolet.androidchallenge.core.inflater
import com.lnicolet.androidchallenge.databinding.ViewCommentBinding
import com.lnicolet.androidchallenge.postdetails.models.Comment

/**
 * Created by Luca Nicoletti
 * on 17/04/2019
 */

class CommentView @kotlin.jvm.JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCommentBinding = ViewCommentBinding.inflate(context.inflater, this, false)

    init {
        View.inflate(context, R.layout.view_comment, this)
    }

    fun setupView(comment: Comment) {
        binding.tvUserName.text = comment.name
        binding.tvCommentBody.text = comment.body
    }
}