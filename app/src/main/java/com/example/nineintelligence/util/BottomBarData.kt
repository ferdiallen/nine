package com.example.nineintelligence.util

import com.example.nineintelligence.R
import com.example.nineintelligence.navigation.NavigationHolder

data class BottomBarData(
    val id: Int,
    val label: String,
    val icon: Int,
    val route: String? = null
)

val listBottomNavigation = listOf(
    BottomBarData(
        1,
        "Bank Soal",
        R.drawable.hand_with_pen,
        route = NavigationHolder.HomeScreenChild.route
    ),
    BottomBarData(2, "Materi", R.drawable.open_book),
    BottomBarData(3, "Try Out", R.drawable.grade),
    BottomBarData(4, "Paket", R.drawable.online_payment),
    BottomBarData(
        5,
        "Profil",
        R.drawable.customer,
        route = NavigationHolder.ProfileScreenChild.route
    )
)
