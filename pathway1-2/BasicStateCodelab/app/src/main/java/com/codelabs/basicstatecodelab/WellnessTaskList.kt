package com.codelabs.basicstatecodelab

fun getWellnessTasks() = List(30) { i -> WellnessTask(id = i, label = "Task # $i") }