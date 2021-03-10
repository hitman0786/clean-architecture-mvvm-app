package com.mvvm.clean_architecture_showcase_app.ui.base

import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mvvm.clean_architecture_showcase_app.common.EMPTY_STRING
import com.mvvm.clean_architecture_showcase_app.common.extensions.*

abstract class BaseActivity : AppCompatActivity() {

  fun showError(@StringRes errorMessage: Int, rootView: View) = snackbar(errorMessage, rootView)
  
  fun showError(errorMessage: String?, rootView: View) = snackbar(errorMessage ?: EMPTY_STRING, rootView)
  
  fun showLoading(progressBar: ProgressBar) = progressBar.visible()
  
  fun hideLoading(progressBar: ProgressBar) = progressBar.gone()
  
  fun addFragment(fragment: Fragment, containerId: Int, addToBackStack: Boolean = false) {
    showFragment(fragment, containerId, addToBackStack)
  }
  
  override fun onBackPressed() {
    if (supportFragmentManager.backStackEntryCount <= 1) finish() else goBack()
  }
}