package com.vside.app.feature.common

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.vside.app.R
import com.vside.app.feature.filter.FilterFragment
import com.vside.app.feature.home.HomeFragment
import com.vside.app.feature.mypage.MyPageFragment
import kotlin.reflect.KClass

enum class VsideBottomMenu(@IdRes val menuItemResId: Int, val kClass: KClass<out Fragment>) {
    Home(R.id.navigation_menu_item_home, HomeFragment::class),
    Filter(R.id.navigation_menu_item_filter, FilterFragment::class),
    MyPage(R.id.navigation_menu_item_my_page, MyPageFragment::class);

    companion object {
        val defaultBottomMenu = Home

        fun getNavigationOnItemSelectedListener(
            fragmentList: List<Fragment>,
            onItemSelected: (Fragment?) -> Unit
        ) = NavigationBarView.OnItemSelectedListener { item ->
            var result = false
            val vsideBottomMenu = VsideBottomMenu.values()
                .find { it.menuItemResId == item.itemId }
            val kClass = VsideBottomMenu.values()
                .find { it.menuItemResId == item.itemId }?.kClass
            vsideBottomMenu?.let {
                fragmentList.find { it1 -> it1::class == kClass }
                    ?.let { it2 ->
                        onItemSelected(it2)
                        result = true
                    }
            }
            result
        }
    }
}
