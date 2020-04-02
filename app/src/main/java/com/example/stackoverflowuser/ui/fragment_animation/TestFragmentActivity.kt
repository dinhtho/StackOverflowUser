package com.example.stackoverflowuser.ui.fragment_animation


import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.base.BaseActivity
import com.example.stackoverflowuser.base.viewmodel.EmptyViewModel
import kotlinx.android.synthetic.main.activity_test_fragment.*


class TestFragmentActivity : BaseActivity<EmptyViewModel>() {
    override val classViewModel: Class<EmptyViewModel>
        get() = EmptyViewModel::class.java


    override val layoutId: Int = R.layout.activity_test_fragment

    override fun init() {
        replaceFragment(FragmentA(), R.id.flRoot)
        tvChange.setOnClickListener {
            addFragment(FragmentB(), R.id.flRoot)
        }

    }

}
