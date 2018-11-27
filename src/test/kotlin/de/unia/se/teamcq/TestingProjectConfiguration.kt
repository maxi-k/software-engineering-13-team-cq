package de.unia.se.teamcq

import io.kotlintest.AbstractProjectConfig

object TestingProjectConfiguration : AbstractProjectConfig() {

    private var testStartedTime: Long = 0

    override fun beforeAll() {
        testStartedTime = System.currentTimeMillis()
    }

    override fun afterAll() {
        val deltaTime = System.currentTimeMillis() - testStartedTime
        println("Tested for $deltaTime milliseconds.")
    }
}
