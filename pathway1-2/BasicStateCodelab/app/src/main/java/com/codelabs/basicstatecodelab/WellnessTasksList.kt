package com.codelabs.basicstatecodelab

fun getWellnessTasks() = List(30) { WellnessTask(it, "Task # $it") }