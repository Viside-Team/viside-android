package com.vside.app.feature.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.depayse.data.remote.mapper.toData
import com.skydoves.sandwich.ApiResponse
import com.vside.app.R
import com.vside.app.databinding.FragmentMyPageBinding
import com.vside.app.feature.auth.LoginActivity
import com.vside.app.feature.common.VSideUrl
import com.vside.app.feature.common.view.TwoBtnDialogFragment
import com.vside.app.feature.content.ContentActivity
import com.vside.app.util.auth.removeUserInfo
import com.vside.app.util.base.BaseFragment
import com.vside.app.util.common.DataTransfer
import com.vside.app.util.common.sharedpref.SharedPrefManager
import org.koin.android.ext.android.inject

class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {
    override val layoutResId: Int = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by inject()

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
        if(viewModel.isLoggedIn.value == true) {
            viewModel.getScrapList()
            viewModel.getProfile()
        }
    }

    private fun initData() {
        viewModel.getScrapList()
        viewModel.getProfile()
        initIsLoggedIn()
    }

    private fun observeData() {
        with(viewModel) {
            isSeeAllClicked.observe(requireActivity()) {
                val intent = Intent(requireContext(), BookShelfActivity::class.java)
                startActivity(intent)
            }

            isLoginClicked.observe(requireActivity()) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }

            isInquiryClicked.observe(requireActivity()) {
                viewDataBinding.layoutLoading.progressCl.visibility = View.VISIBLE
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VSideUrl.KAKAO_CHANNEL_WEB_LINK_URL))
                startActivity(intent)
                viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
            }

            isTermsOfServiceClicked.observe(requireActivity()) {
                viewDataBinding.layoutLoading.progressCl.visibility = View.VISIBLE
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VSideUrl.TERMS_OF_SERVICE_URL))
                startActivity(intent)
                viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
            }

            isPrivacyPolicyClicked.observe(requireActivity()) {
                viewDataBinding.layoutLoading.progressCl.visibility = View.VISIBLE
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VSideUrl.PRIVACY_POLICY_URL))
                startActivity(intent)
                viewDataBinding.layoutLoading.progressCl.visibility = View.GONE
            }

            isLogoutClicked.observe(requireActivity()) {
                val twoBtnDialogFragment = TwoBtnDialogFragment("로그아웃을 하셔도 언제든지\n다시 돌아올 수 있어요!", "로그아웃을 진행하시겠어요?")
                twoBtnDialogFragment.viewModel.isLeftBtnClicked.observe(viewLifecycleOwner) {
                    logout()
                }
                twoBtnDialogFragment.viewModel.isRightBtnClicked.observe(viewLifecycleOwner) {
                    twoBtnDialogFragment.dismiss()
                }
                twoBtnDialogFragment.show(childFragmentManager, twoBtnDialogFragment.tag)
            }

            isWithDrawClicked.observe(requireActivity()) {
                val twoBtnDialogFragment = TwoBtnDialogFragment("계정을 삭제하면 ${viewModel.userName.value}님의\n기록이 모두 사라져요.", "삭제를 진행하시겠어요?")
                twoBtnDialogFragment.viewModel.isLeftBtnClicked.observe(viewLifecycleOwner) {
                    withdraw()
                }
                twoBtnDialogFragment.viewModel.isRightBtnClicked.observe(viewLifecycleOwner) {
                    twoBtnDialogFragment.dismiss()
                }
                twoBtnDialogFragment.show(childFragmentManager, twoBtnDialogFragment.tag)
            }

            isContentItemClicked.observe(requireActivity()) {
                startActivity(
                    Intent(requireContext(), ContentActivity::class.java)
                        .putExtra(DataTransfer.CONTENT, it.toData())
                )
            }
        }
    }

    private fun logout() {
        lifecycleScope.launchWhenCreated {
            val deferred = viewModel.signOutAsync()
            if(deferred.await() is ApiResponse.Success) {
                removeUserInfo(requireContext())
                startActivity(
                    Intent(
                        requireContext(),
                        LoginActivity::class.java
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                )
            }
        }
    }

    private fun withdraw() {
        lifecycleScope.launchWhenCreated {
            val deferred = viewModel.withdrawAsync(
                    SharedPrefManager.getString(requireContext()) { SNS_ID }
            )
            if(deferred.await() is ApiResponse.Success) {
                removeUserInfo(requireContext())
                startActivity(
                    Intent(
                        requireContext(),
                        LoginActivity::class.java
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                )
            }
        }
    }

    private fun initIsLoggedIn() {
        viewModel.isLoggedIn.value = SharedPrefManager.getBoolean(requireContext()) { IS_LOGGED_IN }
    }
}