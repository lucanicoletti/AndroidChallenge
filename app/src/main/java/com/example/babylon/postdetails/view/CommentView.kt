package com.example.babylon.postdetails.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.babylon.R
import com.example.babylon.postdetails.models.Comment
import kotlinx.android.synthetic.main.view_comment.view.*

/**
 * Created by Luca Nicoletti
 * on 17/04/2019
 */

class CommentView @kotlin.jvm.JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
: ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_comment, this)
    }

    fun setupView(comment: Comment) {
        tv_user_name.text = comment.name
        tv_comment_body.text = comment.body
    }
}