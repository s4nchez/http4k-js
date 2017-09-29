package org.http4k.js

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.startCoroutine
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.js.Promise

external class XMLHttpRequest {
    fun open(method: String, uri: String)
    fun send()
    fun addEventListener(name: String, callback: (something: Any) -> Unit)

    var status: Int
    var responseText: String
}


fun main(args: Array<String>) {
    println("Hello JavaScript! Let's make an async request")

    launch {
        val response: XMLHttpRequest = makeRequest("GET", "http://httpbin.org/get").await()
        println("response (${response.status}) = ${response.responseText}")
    }
}

suspend fun <T> Promise<T>.await(): T = suspendCoroutine { cont ->
    then({ cont.resume(it) }, { cont.resumeWithException(it) })
}

fun launch(block: suspend () -> Unit) {
    block.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext get() = EmptyCoroutineContext
        override fun resume(value: Unit) {}
        override fun resumeWithException(e: Throwable) {
            console.log("Coroutine failed: $e")
        }
    })
}

fun makeRequest(method: String, url: String): Promise<XMLHttpRequest> = Promise { resolve, _ ->
    val xhr = XMLHttpRequest()
    xhr.open(method, url)
    xhr.addEventListener("load", { e ->
        resolve(xhr)
    })
    xhr.send()
}