package com.vside.app.feature.home

import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.FragmentHomeBinding
import com.vside.app.feature.common.view.VSideToast
import com.vside.app.util.base.BaseFragment
import com.vside.app.util.common.sharedpref.SharedPrefManager
import org.koin.android.ext.android.inject

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel

        showToastIfFirstLoggedIn()
    }

    private fun showToastIfFirstLoggedIn() {
        if(!SharedPrefManager.getBoolean(requireContext()) { HAVE_SEEN_WELCOME_TOAST }) {
            showToastShort(VSideToast.createShortCenterToast(requireContext(), "Welcome to Vside!"))
            SharedPrefManager.setBoolean(requireContext(), { HAVE_SEEN_WELCOME_TOAST }, true)
        }
    }


}