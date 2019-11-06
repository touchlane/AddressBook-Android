package com.touchlane.addressbook.domain.repository

import com.touchlane.addressbook.db.model.DbContact
import com.touchlane.addressbook.db.service.ContactService
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.domain.toDomain
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ContactRepositoryTest {

    private val testDbContact =  DbContact(firstName = "first", lastName = "last", email = "email@email.com", phone = "12341", address = "")

    private lateinit var contactRepository: ContactRepository
    private lateinit var dbServiceMock: ContactService

    @Before
    fun setUp() {
        dbServiceMock = Mockito.mock(ContactService::class.java)
        contactRepository = ContactRepositoryImpl(dbServiceMock)
    }

    @Test
    fun testSave() {
        val mockDomainContact = toDomain(testDbContact)
        Mockito.`when`(dbServiceMock.save(testDbContact)).thenReturn(Single.just(testDbContact))
        contactRepository.save(mockDomainContact)
        Mockito.verify(dbServiceMock, Mockito.only()).save(testDbContact)
    }

    @Test
    fun testSearchAll() {
        val query = ""
        Mockito.`when`(dbServiceMock.search(query)).thenReturn(Observable.just(listOf(testDbContact)))
        val found = contactRepository.search(query)
        Mockito.verify(dbServiceMock, Mockito.only()).search(query)
        Assert.assertEquals(listOf(testDbContact).map(::toDomain), found.blockingFirst())
    }


    @Test
    fun testSearchNotFound() {
        val query = "query"
        Mockito.`when`(dbServiceMock.search(query)).thenReturn(Observable.just(listOf()))
        val found = contactRepository.search(query)
        Mockito.verify(dbServiceMock, Mockito.only()).search(query)
        Assert.assertEquals(listOf<DomainContact>(), found.blockingFirst())
    }

    @Test
    fun testFindById() {
        val id = 1L
        val mockDomainContact = toDomain(testDbContact)
        Mockito.`when`(dbServiceMock.findById(id)).thenReturn(Single.just(testDbContact))
        val result = contactRepository.findById(id)
        Mockito.verify(dbServiceMock, Mockito.only()).findById(id)
        Assert.assertEquals(mockDomainContact, result.blockingGet())
    }
}
