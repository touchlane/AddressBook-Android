package com.touchlane.addressbook.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.Observable
import com.touchlane.addressbook.databinding.ActivityContactDetailsBinding
import com.touchlane.addressbook.ui.base.BaseViewModelAppActivity

class ContactDetailsActivity : BaseViewModelAppActivity<ContactDetailsViewModel>(ContactDetailsViewModel::class.java) {

    private lateinit var binding: ActivityContactDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactDetailsBinding.inflate(layoutInflater)
        binding.vm = viewModel
        setContentView(binding.root)

        viewModel.onLoadContact(intent.getLongExtra(EXTRA_CONTACT_ID, -1L))

        viewModel.contact.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.contact.get()?.also {
                    title = "${it.firstName} ${it.lastName}"
                }
            }
        })
    }

    override fun showBackButton(): Boolean = true

    companion object {

        private const val EXTRA_CONTACT_ID = "extra-contact_id"

        fun start(context: Context, id: Long) {
            val intent = Intent(context, ContactDetailsActivity::class.java)
            intent.putExtra(EXTRA_CONTACT_ID, id)
            context.startActivity(intent)
        }
    }
}