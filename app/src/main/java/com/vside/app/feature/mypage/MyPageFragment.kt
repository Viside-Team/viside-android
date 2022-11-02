package com.vside.app.feature.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.vside.app.R
import com.vside.app.databinding.FragmentMyPageBinding
import com.vside.app.feature.common.VSideUrl
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
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VSideUrl.KAKAO_CHANNEL_WEB_LINK))
                startActivity(intent)
            }

            isTermsOfServiceClicked.observe(requireActivity()) {

            }

            isPrivacyPolicyClicked.observe(requireActivity()) {

            }

            isLogoutClicked.observe(requireActivity()) {

            }

            isWithDrawClicked.observe(requireActivity()) {

            }
        }
    }
}