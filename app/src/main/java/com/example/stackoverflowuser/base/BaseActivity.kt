package com.example.stackoverflowuser.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.stackoverflowuser.R


import com.example.stackoverflowuser.base.repository.ErrorModel
import com.example.stackoverflowuser.base.viewmodel.BaseViewModel
import com.example.stackoverflowuser.constants.toast
import com.example.stackoverflowuser.widget.RotateLoading


abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {
    private lateinit var viewModel: T
    fun getViewModel() = viewModel
    protected abstract val classViewModel: Class<T>

    protected abstract val layoutId: Int


    private var progressDialogLoading: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        viewModel = ViewModelProviders.of(this).get(classViewModel)
        viewModel.error().observe(this, onError)
        viewModel.loading().observe(this, onLoading)

        init()
    }

    abstract fun init()

    fun addFragment(
        fragment: Fragment,
        id: Int,
        backStack: Boolean = true
    ) {
        val transaction = supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right
            )
            val currentFragment = supportFragmentManager!!.fragments.lastOrNull()
            currentFragment?.let {
                transaction.hide(it)
            }

            if (backStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.add(id, fragment, fragment.javaClass.simpleName).commit()
        }
    }

    fun replaceFragment(
        fragment: Fragment,
        id: Int,
        backStack: Boolean = false
    ) {
        val transaction = supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            if (backStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.replace(id, fragment, fragment.javaClass.simpleName).commit()
        }
    }

    protected var onError = Observer<ErrorModel> {
        if (it.msg != null) {
            toast(it.msg)
        }
    }

    protected var onLoading = Observer<Boolean> {
        if (it) {
            showProgressLoading()
        } else {
            hideProgressLoading()
        }
    }

    fun showProgressLoading() {
        if (progressDialogLoading == null) {
            val view = layoutInflater.inflate(R.layout.layout_progress_loading, null)
            val rotateLoading = view.findViewById(R.id.layout_loading_rotate) as RotateLoading
            rotateLoading.start()
            progressDialogLoading = Dialog(this)
            progressDialogLoading!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialogLoading!!.setContentView(view)
            progressDialogLoading!!.setCancelable(false)
            progressDialogLoading!!.setCanceledOnTouchOutside(false)

            val window = progressDialogLoading!!.window
            window?.setBackgroundDrawableResource(R.drawable.bg_layout_loading)
        }
        if (!progressDialogLoading!!.isShowing) {
            progressDialogLoading!!.show()
        }
    }

    fun hideProgressLoading() {
        if (progressDialogLoading != null && progressDialogLoading!!.isShowing)
            progressDialogLoading!!.dismiss()
    }
}


