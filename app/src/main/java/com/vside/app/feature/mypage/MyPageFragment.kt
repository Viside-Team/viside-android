package com.vside.app.feature.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.FragmentMyPageBinding
import com.vside.app.feature.common.VSideUrl
import com.vside.app.feature.common.view.TwoBtnDialogFragment
import com.vside.app.util.base.BaseFragment
import org.koin.android.ext.android.inject

class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>() {
    override val layoutResId: Int = R.layout.fragment_my_page
    override val viewModel: MyPageViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel

        observeData()
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
                val twoBtnDialogFragment = TwoBtnDialogFragment("계정을 삭제하면 이은지님의\n기록이 모두 사라져요.", "삭제를 진행하시겠어요?")
                twoBtnDialogFragment.viewModel.isLeftBtnClicked.observe(viewLifecycleOwner) {

                }
                twoBtnDialogFragment.viewModel.isRightBtnClicked.observe(viewLifecycleOwner) {
                    twoBtnDialogFragment.dismiss()
                }
                twoBtnDialogFragment.show(childFragmentManager, twoBtnDialogFragment.tag)
            }
        }
    }
}