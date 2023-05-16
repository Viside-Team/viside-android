package com.vside.app.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.depayse.data.remote.mapper.toData
import com.vside.app.R
import com.vside.app.databinding.FragmentHomeBinding
import com.vside.app.feature.common.view.LoginDialogFragment
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) refreshData()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        if(SharedPrefManager.getBoolean(requireContext()) { IS_LOGGED_IN }) {
            viewModel.refreshHomeContentList()
            viewModel.getProfile()
        }
    }

    private fun initData() {
        viewModel.getHomeContentList()
        viewModel.getProfile()
        showToastIfFirstLoggedIn()
    }

    private fun observeData() {
        with(viewModel) {
            isContentItemClicked.observe(requireActivity()) {
                startActivity(
                    Intent(requireContext(), ContentActivity::class.java)
                        .putExtra(DataTransfer.CONTENT, it.toData())
                )
            }

            isContentBookmarkClicked.observe(requireActivity()) {
                if(SharedPrefManager.getBoolean(requireContext()) { IS_LOGGED_IN }) {
                    viewModel.toggleScrapContent(it)
                    return@observe
                }

                val loginDialog = LoginDialogFragment()
                loginDialog.show(parentFragmentManager, loginDialog.tag)
            }
        }
    }

    private fun showToastIfFirstLoggedIn() {
        if(!SharedPrefManager.getBoolean(requireContext()) { HAVE_SEEN_WELCOME_TOAST }) {
            showToastShort(VSideToast.createShortCenterToast(requireContext(), "Welcome to V side!"))
            SharedPrefManager.setBoolean(requireContext(), { HAVE_SEEN_WELCOME_TOAST }, true)
        }
    }
}