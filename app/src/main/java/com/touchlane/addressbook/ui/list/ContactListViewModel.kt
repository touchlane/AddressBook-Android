package com.touchlane.addressbook.ui.list

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
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
    val loadingProgress: ObservableField<Boolean> = ObservableField()
    val selectedForDetails: ObservableField<DomainContact?> = ObservableField()

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
        selectedForDetails.set(contact)
        selectedForDetails.notifyChange()
    }

    companion object {

        @BindingAdapter("app:items")
        @JvmStatic
        fun setListItems(list: RecyclerViewWithPlaceholder, items: ObservableList<DomainContact>) {
            list.setAdapter(ContactListAdapter(items, list.onItemSelectedListener))
        }

        @BindingAdapter("app:inProgress")
        @JvmStatic
        fun setInProgress(srLayout: SwipeRefreshLayout, items: ObservableField<Boolean>) {
            srLayout.isRefreshing = items.get() ?: false
        }

        @BindingAdapter("app:onItemClick")
        @JvmStatic
        fun bindOnClick(list: RecyclerViewWithPlaceholder, listener: RecyclerViewWithPlaceholder.SelectItemListener) {
            list.onItemSelectedListener = listener
        }
    }
}