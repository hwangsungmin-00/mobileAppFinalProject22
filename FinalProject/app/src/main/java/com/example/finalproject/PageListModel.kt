package com.example.finalproject

data class PageListModel(val response:PageMyBody)

data class PageMyBody(val body:PageMyItems)

data class PageMyItems(val items:PageMyItem)

data class PageMyItem(val item:MutableList<ItemModel>?)