package com.touchlane.addressbook.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

abstract class BaseViewModelAppActivity<VM: BaseAppViewModel>(cls: Class<VM>) : AppCompatActivity() {

    protected val viewModel: VM by lazy { ViewModelProviders.of(this).get(cls) }

    fun <T> LiveData<T?>.observe(action: (T?) -> Unit) {
        observe(this@BaseViewModelAppActivity, Observer { action.invoke(it) })
    }

    fun <T> LiveData<T?>.observeNonNull(action: (T) -> Unit) {
        observe(this@BaseViewModelAppActivity, Observer { it?.run(action)})
    }

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

    protected abstract fun showBackButton(): Boolean
}