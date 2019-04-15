package com.example.babylon.postdetails.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.babylon.R
import com.example.babylon.core.getViewModel
import com.example.babylon.postdetails.viewmodels.PostDetailsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_post_detail.*
import javax.inject.Inject

class PostDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: PostDetailsViewModel


    private val args: PostDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_post_detail, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory) {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.post?.let { post ->
            tv_title.text = post.title
            tv_body.text = post.title
        }
    }
}