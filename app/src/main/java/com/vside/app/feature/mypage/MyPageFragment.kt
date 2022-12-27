package com.vside.app.feature.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.vside.app.R
import com.vside.app.databinding.FragmentMyPageBinding
import com.vside.app.feature.auth.data.request.WithdrawRequest
import com.vside.app.feature.common.VSideUrl
import com.vside.app.feature.common.view.TwoBtnDialogFragment
import com.vside.app.util.base.BaseFragment
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

    private fun initData() {
        getScrapList()
        getProfile()
    }

    private fun observeData() {
        with(viewModel) {
            isSeeAllClicked.observe(requireActivity()) {

            }

            isInquiryClicked.observe(requireActivity()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VSideUrl.KAKAO_CHANNEL_WEB_LINK_URL))
                startActivity(intent)
            }

            isTermsOfServiceClicked.observe(requireActivity()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VSideUrl.TERMS_OF_SERVICE_URL))
                startActivity(intent)
            }

            isPrivacyPolicyClicked.observe(requireActivity()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VSideUrl.PRIVACY_POLICY_URL))
                startActivity(intent)
            }

            isLogoutClicked.observe(requireActivity()) {
                val twoBtnDialogFragment = TwoBtnDialogFragment("로그아웃을 하셔도 언제든지\n다시 돌아올 수 있어요!", "로그아웃을 진행하시겠어요?")
                twoBtnDialogFragment.viewModel.isLeftBtnClicked.observe(viewLifecycleOwner) {

                }
                twoBtnDialogFragment.viewModel.isRightBtnClicked.observe(viewLifecycleOwner) {
                    twoBtnDialogFragment.dismiss()
                }
                twoBtnDialogFragment.show(childFragmentManager, twoBtnDialogFragment.tag)
            }

            isWithDrawClicked.observe(requireActivity()) {
                val twoBtnDialogFragment = TwoBtnDialogFragment("계정을 삭제하면 ${viewModel.userName.value}님의\n기록이 모두 사라져요.", "삭제를 진행하시겠어요?")
                twoBtnDialogFragment.viewModel.isLeftBtnClicked.observe(viewLifecycleOwner) {

                }
                twoBtnDialogFragment.viewModel.isRightBtnClicked.observe(viewLifecycleOwner) {
                    twoBtnDialogFragment.dismiss()
                }
                twoBtnDialogFragment.show(childFragmentManager, twoBtnDialogFragment.tag)
            }
        }
    }

    private fun getScrapList() {
        lifecycleScope.launchWhenCreated {
            viewModel.getScrapList(
                onGetSuccess = {},
                onGetFail = { toastShortOfFailMessage("스크랩 리스트 가져오기") }
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

    private fun signOut() {
        lifecycleScope.launchWhenCreated {
            viewModel.signOut(
                onSignOutSuccess = {},
                onSignOutFail = { toastShortOfFailMessage("로그아웃") }
            )
        }
    }

    private fun withDraw() {
        lifecycleScope.launchWhenCreated {
            viewModel.withdraw(
                WithdrawRequest(SharedPrefManager.getString(requireContext()) { SNS_ID }),
                onWithdrawSuccess = {},
                onWithdrawFail = { toastShortOfFailMessage("회원 탈퇴")}
            )
        }
    }
}