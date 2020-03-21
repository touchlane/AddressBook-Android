package com.touchlane.addressbook.di

import androidx.room.Room
import com.touchlane.addressbook.db.AddressBookDatabase
import com.touchlane.addressbook.db.service.ContactService
import com.touchlane.addressbook.db.service.ContactServiceImpl
import com.touchlane.addressbook.domain.error.ErrorDispatcher
import com.touchlane.addressbook.domain.error.ErrorDispatcherImpl
import com.touchlane.addressbook.domain.repository.ContactRepository
import com.touchlane.addressbook.domain.repository.ContactRepositoryImpl
import com.touchlane.addressbook.ui.details.ContactDetailsViewModel
import com.touchlane.addressbook.ui.edit.CreateContactViewModel
import com.touchlane.addressbook.ui.list.ContactListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val dbModule = module {

    single {
        Room.databaseBuilder(
            get(),
            AddressBookDatabase::class.java, "address-book.db"
        ).build()
    }

    single { get<AddressBookDatabase>().contactDao() }

    single<ContactService> { ContactServiceImpl(get()) }
}

val domainModule = module {

    single<ContactRepository> { ContactRepositoryImpl(get()) }

    single<ErrorDispatcher> { ErrorDispatcherImpl(get()) }
}

val viewModelModule = module {

    viewModel { ContactListViewModel(get()) }

    viewModel { CreateContactViewModel(get(), get()) }

    viewModel { ContactDetailsViewModel(get()) }
}
