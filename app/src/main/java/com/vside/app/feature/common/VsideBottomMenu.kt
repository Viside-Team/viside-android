package com.vside.app.feature.common

import androidx.annotation.IdRes
import com.google.android.material.navigation.NavigationBarView
import com.vside.app.R

sealed class VsideBottomMenu(@IdRes val menuItemResId: Int) {
    object Home: VsideBottomMenu(R.id.navigation_menu_item_home)
    object Filter: VsideBottomMenu(R.id.navigation_menu_item_filter)
    object MyPage: VsideBottomMenu(R.id.navigation_menu_item_my_page)

    companion object {
        private fun findMenuById(@IdRes menuItemResId: Int): VsideBottomMenu? {
            return when(menuItemResId) {
                Home.menuItemResId -> Home
                Filter.menuItemResId -> Filter
                MyPage.menuItemResId -> MyPage
                else -> null
            }
        }

        fun getNavigationOnItemSelectedListener(
            onHomeSelected: () -> Unit,
            onFilterSelected: () -> Unit,
            onMyPageSelected: () -> Unit
        ) = NavigationBarView.OnItemSelectedListener { item ->
            var result = false
            val visideBottomMenu = findMenuById(item.itemId)
            visideBottomMenu?.let {
                result =
                    when (it) {
                        Home -> {
                            onHomeSelected()
                            true
                        }
                        Filter -> {
                            onFilterSelected()
                            true
                        }
                        MyPage -> {
                            onMyPageSelected()
                            true
                        }
                    }
            }
            result
        }
    }
}
