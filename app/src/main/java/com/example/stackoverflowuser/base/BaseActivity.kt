package com.example.stackoverflowuser.base

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.User
import com.example.stackoverflowuser.ui.reputation.ReputationActivity
import kotlinx.android.synthetic.main.activity_main.*


import com.example.stackoverflowuser.application.MyApplication
import com.example.stackoverflowuser.constants.toast
import com.example.stackoverflowuser.ui.main.MainActivityViewModel
import com.example.stackoverflowuser.widget.RotateLoading
import com.google.android.gms.analytics.HitBuilders
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {
    private lateinit var viewModel: T
    private var progressDialogLoading: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(getClassViewModel())
        viewModel.error.observe(this, Observer {
            if (it.message != null) {
                toast(it.message!!)
            }
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                showProgressLoading()
            } else {
                hideProgressLoading()
            }
        })
    }

    protected abstract fun getClassViewModel(): Class<T>


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


