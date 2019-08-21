package com.example.movieappstarter.utils.base

import androidx.recyclerview.widget.ItemTouchHelper

interface TouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
}