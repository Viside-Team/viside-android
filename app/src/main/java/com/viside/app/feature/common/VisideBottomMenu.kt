package com.viside.app.feature.common

import androidx.annotation.IdRes
import com.google.android.material.navigation.NavigationBarView
import com.viside.app.R

sealed class VisideBottomMenu(@IdRes val menuItemResId: Int) {
    object Home: VisideBottomMenu(R.id.navigation_menu_item_home)
    object Filter: VisideBottomMenu(R.id.navigation_menu_item_filter)
    object MyPage: VisideBottomMenu(R.id.navigation_menu_item_my_page)

    companion object {
        private fun findMenuById(@IdRes menuItemResId: Int): VisideBottomMenu? {
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
