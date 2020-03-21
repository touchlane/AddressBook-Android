package com.touchlane.addressbook.ui.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.touchlane.addressbook.databinding.ActivityEditContactBinding
import com.touchlane.addressbook.ui.base.BaseViewModelAppActivity
import org.koin.android.ext.android.get

class CreateContactActivity : BaseViewModelAppActivity<CreateContactViewModel>() {

    private lateinit var binding: ActivityEditContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditContactBinding.inflate(layoutInflater)
        binding.vm = viewModel
        setContentView(binding.root)

        setupObservers()
    }

    override fun instantiateViewModel(): CreateContactViewModel = get()

    override fun showBackButton(): Boolean = true

    private fun setupObservers() {
        binding.vm?.saveResult?.observeNonNull {
            if (it.success) {
                setResult(Activity.RESULT_OK)
                finish()
            } else if (it.message.isNullOrBlank().not()){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CreateContactActivity::class.java))
        }
    }
}