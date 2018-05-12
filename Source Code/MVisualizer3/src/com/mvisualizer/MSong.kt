package com.mvisualizer

open class MSong constructor(val type: FType, val path: String, var title: String) {
    enum class FType { File, SCTrack }
    override fun toString(): String { return this.title }
}