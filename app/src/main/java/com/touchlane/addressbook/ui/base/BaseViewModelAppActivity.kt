package com.touchlane.addressbook.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.koin.android.ext.android.get

abstract class BaseViewModelAppActivity<VM: BaseAppViewModel> : AppCompatActivity() {

    protected val viewModel: VM by lazy { instantiateViewModel() }

    protected abstract fun instantiateViewModel(): VM

    protected abstract fun showBackButton(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected fun <T> LiveData<T?>.observeNonNull(action: (T) -> Unit) {
        observe(this@BaseViewModelAppActivity, Observer { it?.run(action)})
    }
}