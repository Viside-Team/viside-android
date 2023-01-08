package com.vside.app.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.vside.app.R
import com.vside.app.databinding.FragmentHomeBinding
import com.vside.app.feature.common.view.VSideToast
import com.vside.app.feature.content.ContentActivity
import com.vside.app.util.base.BaseFragment
import com.vside.app.util.common.DataTransfer
import com.vside.app.util.common.sharedpref.SharedPrefManager
import org.koin.android.ext.android.inject

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layoutResId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel

        initData()
        observeData()
    }

    private fun initData() {
        getHomeContentList()
        getProfile()
    }

    private fun observeData() {
        with(viewModel) {
            isHomeContentItemClicked.observe(requireActivity()) {
                startActivity(
                    Intent(requireContext(), ContentActivity::class.java)
                        .putExtra(DataTransfer.CONTENT_URL, it.contentUrl)
                )
            }

            isHomeContentBookmarkClicked.observe(requireActivity()) {

            }
        }
    }

    private fun showToastIfFirstLoggedIn() {
        if(!SharedPrefManager.getBoolean(requireContext()) { HAVE_SEEN_WELCOME_TOAST }) {
            showToastShort(VSideToast.createShortCenterToast(requireContext(), "Welcome to Vside!"))
            SharedPrefManager.setBoolean(requireContext(), { HAVE_SEEN_WELCOME_TOAST }, true)
        }
    }

    private fun getHomeContentList() {
        lifecycleScope.launchWhenCreated {
            viewModel.getHomeContentList(
                onGetSuccess = { showToastIfFirstLoggedIn() },
                onGetFail = { toastShortOfFailMessage("컨텐츠 가져오기") }
            )
        }
    }

    private fun getProfile() {
        lifecycleScope.launchWhenCreated {
            viewModel.getProfile(
                onGetSuccess = {},
                onGetFail = {toastShortOfFailMessage("프로필 정보 가져오기")}
            )
        }
    }
}