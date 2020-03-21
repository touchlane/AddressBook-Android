package com.touchlane.addressbook.domain.error

interface ErrorDispatcher {

    fun dispatch(error: Throwable): String
}