package com.touchlane.addressbook.ui.edit

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.touchlane.addressbook.databinding.ActivityEditContactBinding
import com.touchlane.addressbook.ui.base.BaseViewModelAppActivity

class CreateContactActivity : BaseViewModelAppActivity<CreateContactViewModel>(CreateContactViewModel::class.java) {

    private lateinit var binding: ActivityEditContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditContactBinding.inflate(layoutInflater)
        binding.vm = viewModel
        setContentView(binding.root)

        setupObservers()
    }

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
}