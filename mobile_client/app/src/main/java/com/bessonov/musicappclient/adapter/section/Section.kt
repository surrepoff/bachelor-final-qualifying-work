package com.bessonov.musicappclient.adapter.section

data class Section<T>(
    var title: String,
    var type: SectionType,
    var items: List<T>,
    var orientation: Int,
    var info: String
)