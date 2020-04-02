package com.example.stackoverflowuser.ui.fragment_animation

import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.base.fragment.BaseFragment
import com.example.stackoverflowuser.base.viewmodel.EmptyViewModel
import kotlinx.android.synthetic.main.fragment_a.*

class FragmentB : BaseFragment<EmptyViewModel>() {
    override val classViewModel: Class<EmptyViewModel>
        get() = EmptyViewModel::class.java
    override val layoutId: Int = R.layout.fragment_a
    override fun init() {
        tvLabel.text = "fragment_b"
    }
}