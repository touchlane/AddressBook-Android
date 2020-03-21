package com.touchlane.addressbook.ui.list

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.domain.repository.ContactRepository
import com.touchlane.addressbook.ui.base.BaseAppViewModel
import com.touchlane.addressbook.ui.widgets.RecyclerViewWithPlaceholder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ContactListViewModel(private val contactsRepository: ContactRepository) : BaseAppViewModel() {

    val contacts: ObservableList<DomainContact> = ObservableArrayList()
    val loadingProgress: ObservableBoolean = ObservableBoolean()
    private val listeners: MutableSet<OnContactSelectedListener> = mutableSetOf()
    private val searchCriteria: BehaviorSubject<String> = BehaviorSubject.createDefault("")

    init {
        doRefresh()
    }

    fun doRefresh() {
        val disposable =
            searchCriteria.flatMap(contactsRepository::search)
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

    fun search(query: String) {
        searchCriteria.onNext(query)
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