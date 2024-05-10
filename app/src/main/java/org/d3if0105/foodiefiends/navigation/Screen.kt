package org.d3if0105.foodiefiends.navigation

import KEY_ID_FOODIE


sealed class Screen ( val route: String){
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_FOODIE}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}