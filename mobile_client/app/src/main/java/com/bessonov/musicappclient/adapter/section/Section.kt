package com.bessonov.musicappclient.adapter.section

data class Section<T> (
    var title: String,
    val type: SectionType,
    val items: List<T>,
    val orientation: Int
)