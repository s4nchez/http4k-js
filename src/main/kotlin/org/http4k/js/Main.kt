package org.http4k.js

/*
var request = new XMLHttpRequest();
request.open('GET', '/bar/foo.txt', false);  // `false` makes the request synchronous
request.send(null);

if (request.status === 200) {
  console.log(request.responseText);
}
 */

external class XMLHttpRequest {
    fun open(method: String, uri: String, sync: Boolean)
    fun send(data: Any?)

    var status: Int
    var responseText: String
}

fun main(args: Array<String>) {
    println("Hello JavaScript! Let's make a request")

    val request = XMLHttpRequest()
    request.open("GET", "http://httpbin.org/get", false)
    request.send(null)


    if (request.status == 200) {
        println("got ${request.responseText}")
    }

}