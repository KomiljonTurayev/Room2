package com.goldstein.room2

class BackgroundThread() : Thread() {
    override fun run() {
        loadImage()
    }

    private fun loadImage() {
        println("Loading image")
        sleep(3000)
        println("Loading finish")
    }
}

fun main() {
    println("Main thread started")

    val thread = BackgroundThread()
    thread.start()

    println("Main thread finished")

}