package org.http4k.js

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

        makeRequest("GET", "http://httpbin.org/get").then({ response ->
            println("response (${response.status}) = ${response.responseText}")
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