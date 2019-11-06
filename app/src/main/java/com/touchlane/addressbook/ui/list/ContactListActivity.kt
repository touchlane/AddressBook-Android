package com.touchlane.addressbook.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.SupportMenuInflater
import androidx.databinding.Observable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.touchlane.addressbook.R
import com.touchlane.addressbook.databinding.ActivityContactListBinding
import com.touchlane.addressbook.ui.base.BaseViewModelAppActivity
import com.touchlane.addressbook.ui.details.ContactDetailsActivity
import com.touchlane.addressbook.ui.edit.CreateContactActivity

class ContactListActivity : BaseViewModelAppActivity<ContactListViewModel>(ContactListViewModel::class.java) {

    private lateinit var binding: ActivityContactListBinding

    override fun showBackButton(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactListBinding.inflate(layoutInflater)
        binding.vm = viewModel
        setContentView(binding.root)

        setupViews()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.selectedForDetails.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.selectedForDetails.get()?.let {
                    ContactDetailsActivity.start(this@ContactListActivity, it.id!!)
                }
            }
        })
    }

    private fun setupViews() {
        binding.contactsList.setLayoutManager(
            LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
            )
        )
        binding.refreshLayout.setOnRefreshListener { viewModel.doRefresh() }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        SupportMenuInflater(this).inflate(R.menu.contacts_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_contact -> {
                onAddContact()
                true
            }
            else -> false
        }
    }

    private fun onAddContact() {
        startActivityForResult(Intent(this, CreateContactActivity::class.java), REQUEST_CODE_ADD_CONTACT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_ADD_CONTACT) {
            viewModel.doRefresh()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val REQUEST_CODE_ADD_CONTACT = 1
    }
}
