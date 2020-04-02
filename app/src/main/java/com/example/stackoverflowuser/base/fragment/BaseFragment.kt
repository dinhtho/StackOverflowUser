package com.example.stackoverflowuser.base.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.stackoverflowuser.R


import com.example.stackoverflowuser.base.repository.ErrorModel
import com.example.stackoverflowuser.base.viewmodel.BaseViewModel
import com.example.stackoverflowuser.base.viewmodel.ClassUtils
import com.example.stackoverflowuser.base.viewmodel.ShareViewModel
import com.example.stackoverflowuser.constants.toast
import com.example.stackoverflowuser.widget.RotateLoading


abstract class BaseFragment<T : BaseViewModel> : Fragment() {
    private lateinit var viewModel: T
    fun getViewModel() = viewModel
    protected abstract val classViewModel: Class<T>

    protected abstract val layoutId: Int


    private var progressDialogLoading: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shareViewMode = ClassUtils.getAnnotation(this, ShareViewModel::class.java)
        if (shareViewMode != null)
            viewModel = ViewModelProviders.of(activity!!).get(classViewModel)
        else {
            viewModel = ViewModelProviders.of(this).get(classViewModel)
            viewModel.error().observe(this, onError)
            viewModel.loading().observe(this, onLoading)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    abstract fun init()

    protected var onError = Observer<ErrorModel> {
        if (it.msg != null) {
            context?.toast(it.msg)
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
            progressDialogLoading = Dialog(context!!)
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


