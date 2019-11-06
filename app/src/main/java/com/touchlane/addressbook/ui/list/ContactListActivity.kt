package com.touchlane.addressbook.ui.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.view.SupportMenuInflater
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.touchlane.addressbook.R
import com.touchlane.addressbook.databinding.ActivityContactListBinding
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.ui.base.BaseViewModelAppActivity
import com.touchlane.addressbook.ui.details.ContactDetailsActivity
import com.touchlane.addressbook.ui.edit.CreateContactActivity


class ContactListActivity : BaseViewModelAppActivity<ContactListViewModel>(ContactListViewModel::class.java) {

    private lateinit var binding: ActivityContactListBinding
    private val contactSelectedListener = createContactSelectedListener()

    override fun showBackButton(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactListBinding.inflate(layoutInflater)
        binding.vm = viewModel
        setContentView(binding.root)

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        viewModel.registerContactSelectedListener(contactSelectedListener)
    }

    override fun onStop() {
        super.onStop()
        viewModel.unregisterContactSelectedListener(contactSelectedListener)
    }

    private fun createContactSelectedListener(): ContactListViewModel.OnContactSelectedListener {
        return object : ContactListViewModel.OnContactSelectedListener {
            override fun onContactSelected(contact: DomainContact) {
                ContactDetailsActivity.start(this@ContactListActivity, contact.id!!)
            }
        }
    }

    private fun setupViews() {
        binding.contactsList.setLayoutManager(
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        )
        binding.refreshLayout.setOnRefreshListener { viewModel.doRefresh() }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        SupportMenuInflater(this).inflate(R.menu.contacts_menu, menu)
        setupSeacrh(menu)
        return true
    }

    private fun setupSeacrh(menu: Menu?) {
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        val queryField = searchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        val closeBtn = searchView.findViewById<ImageView>(R.id.search_close_btn)
        val whiteColor = ContextCompat.getColor(this, R.color.white)
        queryField.setTextColor(whiteColor)
        queryField.setHintTextColor(whiteColor)
        closeBtn.setColorFilter(whiteColor)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean = false

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.search(query)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (super.onOptionsItemSelected(item).not()) {
            return when (item.itemId) {
                R.id.add_contact -> {
                    onAddContact()
                    true
                }
                else -> false
            }
        }
        return false
    }

    private fun onAddContact() {
        CreateContactActivity.start(this)
    }
}
