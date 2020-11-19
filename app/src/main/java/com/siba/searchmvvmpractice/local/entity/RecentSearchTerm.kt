package com.siba.searchmvvmpractice.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.siba.searchmvvmpractice.model.SearchTermData

@Entity(tableName = "recent_search_term_table")

data class RecentSearchTerm(
        @PrimaryKey(autoGenerate = true)
        val searchTermId : Int = 0,
        val keyword : String
)