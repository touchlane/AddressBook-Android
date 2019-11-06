package com.touchlane.addressbook.ui.details

import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.domain.repository.ContactRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ContactDetailsViewModelTest {

    private val testId = 1L
    private val testContact = DomainContact(testId,
        "first",
        "last",
        "email@email.com",
        "address",
        "12341"
    )

    private lateinit var viewModel: ContactDetailsViewModel
    private lateinit var repository: ContactRepository

    @Before
    fun setUp() {
        repository = Mockito.mock(ContactRepository::class.java)
        viewModel = ContactDetailsViewModel(repository)
    }

    @Test
    fun testLoadDetails() {
        Mockito.`when`(repository.findById(testId)).thenReturn(Single.just(testContact))

        viewModel.onLoadContact(testId)
        Mockito.verify(repository, Mockito.only()).findById(testId)
    }
}