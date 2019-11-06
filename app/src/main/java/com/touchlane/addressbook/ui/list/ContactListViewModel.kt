package com.touchlane.addressbook.ui.list

import android.util.Log
import androidx.databinding.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.touchlane.addressbook.components.contactRepository
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.domain.repository.ContactRepository
import com.touchlane.addressbook.ui.base.BaseAppViewModel
import com.touchlane.addressbook.ui.widgets.RecyclerViewWithPlaceholder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactListViewModel : BaseAppViewModel() {

    private val contactsRepository: ContactRepository = contactRepository()

    val contacts: ObservableList<DomainContact> = ObservableArrayList()
    val loadingProgress: ObservableBoolean = ObservableBoolean()
    private val listeners: MutableSet<OnContactSelectedListener> = mutableSetOf()

    init {
        doRefresh()
    }

    fun doRefresh() {
        val disposable = contactsRepository.loadAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingProgress.set(true) }
            .doOnComplete { loadingProgress.set(false) }
            .subscribe({
                contacts.clear()
                contacts.addAll(it)
                loadingProgress.set(false)
            }, {
                loadingProgress.set(false)
                Log.e("ConListVM", "Can not load contacts")
            })

        addDisposable(disposable)
    }

    fun onItemClicked(contact: DomainContact) {
        listeners.forEach { it.onContactSelected(contact) }
    }

    fun registerContactSelectedListener(listener: OnContactSelectedListener) {
        listeners.add(listener)
    }

    fun unregisterContactSelectedListener(listener: OnContactSelectedListener) {
        listeners.remove(listener)
    }

    companion object {

        @BindingAdapter("app:items")
        @JvmStatic
        fun setListItems(list: RecyclerViewWithPlaceholder, items: ObservableList<DomainContact>) {
            list.setAdapter(ContactListAdapter(items, list.onItemSelectedListener))
        }

        @BindingAdapter("app:inProgress")
        @JvmStatic
        fun setInProgress(srLayout: SwipeRefreshLayout, items: ObservableBoolean) {
            srLayout.isRefreshing = items.get() ?: false
        }

        @BindingAdapter("app:onItemClick")
        @JvmStatic
        fun bindOnClick(list: RecyclerViewWithPlaceholder, listener: RecyclerViewWithPlaceholder.SelectItemListener) {
            list.onItemSelectedListener = listener
        }
    }

    interface OnContactSelectedListener {
        fun onContactSelected(contact: DomainContact)
    }
}