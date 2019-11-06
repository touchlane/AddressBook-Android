package com.touchlane.addressbook.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.touchlane.addressbook.BR
import com.touchlane.addressbook.databinding.ItemContactBinding
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.ui.widgets.RecyclerViewWithPlaceholder

class ContactListAdapter(private val contacts: List<DomainContact>,
                         private val selectListener: RecyclerViewWithPlaceholder.SelectItemListener)
    : RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position], selectListener)
    }

    class ContactViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(domainContact: DomainContact, listener: RecyclerViewWithPlaceholder.SelectItemListener) {
            binding.setVariable(BR.vm, domainContact)
            binding.root.setOnClickListener { listener.onClick(domainContact) }
        }
    }
}