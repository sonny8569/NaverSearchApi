package com.partron.naverbookapiproject.utill

import com.partron.naverbookapiproject.Https.Data.Book

interface OnItemClickListener {
    fun onClickListener(data: Book)
}