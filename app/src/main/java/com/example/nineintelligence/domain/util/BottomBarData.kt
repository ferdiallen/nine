package com.example.nineintelligence.domain.util

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
        route = NavigationHolder.BankSoalScreen.route
    ),
    BottomBarData(2, "Materi", R.drawable.open_book, route = NavigationHolder.SubjectScreen.route),
    BottomBarData(3, "Try Out", R.drawable.grade, route = NavigationHolder.TryoutScreen.route),
    BottomBarData(
        4,
        "Paket",
        R.drawable.online_payment,
        route = NavigationHolder.PackageScreen.route
    ),
    BottomBarData(
        5,
        "Profil",
        R.drawable.customer,
        route = NavigationHolder.ProfileScreenChild.route
    )
)
