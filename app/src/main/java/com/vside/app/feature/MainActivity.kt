package com.vside.app.feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.vside.app.R
import com.vside.app.databinding.ActivityMainBinding
import com.vside.app.feature.common.VsideBottomMenu
import com.vside.app.feature.filter.FilterFragment
import com.vside.app.feature.home.HomeFragment
import com.vside.app.feature.mypage.MyPageFragment
import com.vside.app.util.base.BaseActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by inject()

    private val homeFragment: HomeFragment by lazy {
        HomeFragment()
    }

    private val filterFragment: FilterFragment by lazy {
        FilterFragment()
    }

    private val myPageFragment: MyPageFragment by lazy {
        MyPageFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.viewModel = viewModel

        setUpBottomNavigationView()

        initTab()
    }

    private fun initTab() {
        viewDataBinding.bnvMain.selectedItemId = VsideBottomMenu.Home.menuItemResId
    }

    private fun setUpBottomNavigationView() {
        viewDataBinding.bnvMain.run {
            // 디자인 된 아이콘 적용 위해 기존 아이콘 틴트는 없앰
            itemIconTintList = null

            // 탭 선택 리스너 설정
            setOnItemSelectedListener(
                VsideBottomMenu.getNavigationOnItemSelectedListener(
                    onHomeSelected = {
                        changeFragment(homeFragment)
                    },
                    onFilterSelected = {
                        changeFragment(filterFragment)
                    },
                    onMyPageSelected = {
                        changeFragment(myPageFragment)
                    }
                )
            )
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                if(!supportFragmentManager.fragments.contains(fragment)) {
                    add(viewDataBinding.fragmentContainerViewMain.id, fragment)
                    supportFragmentManager.fragments.forEach {
                        if(it!=fragment){
                            hide(it)
                        }
                        else {
                            show(it)
                        }
                    }
                }
                else {
                    supportFragmentManager.fragments.forEach {
                        if(it!=fragment){
                            hide(it)
                        }
                        else {
                            show(it)
                        }
                    }
                }
                commit()
            }
    }

}