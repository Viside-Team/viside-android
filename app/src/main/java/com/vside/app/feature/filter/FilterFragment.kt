package com.vside.app.feature.filter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vside.app.R
import com.vside.app.databinding.FragmentFilterBinding
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.util.base.BaseFragment
import com.vside.app.util.common.sharedpref.SharedPrefManager
import org.koin.android.ext.android.inject

class FilterFragment: BaseFragment<FragmentFilterBinding, FilterViewModel>() {
    override val layoutResId: Int = R.layout.fragment_filter
    override val viewModel: FilterViewModel by inject()
    val filterSelectViewModel: FilterSelectViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = viewModel
        filterSelectViewModel.tokenBearer = SharedPrefManager.getString(requireContext()) { TOKEN_BEARER }
        viewDataBinding.filterSelectViewModel = filterSelectViewModel

        initData()
    }

    private fun initData() {
        addBottomSheetCallback()
        getFilteredGroupedByCategory()
        getKeywordsGroupedByCategory()
    }

    private fun addBottomSheetCallback() {
        val behavior = BottomSheetBehavior.from(viewDataBinding.clDialogFilterSelect)
        behavior.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> viewDataBinding.bindingDialogFilterSelect.tvFilterSelectComplete.visibility = View.INVISIBLE
                    else -> viewDataBinding.bindingDialogFilterSelect.tvFilterSelectComplete.visibility = View.VISIBLE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun getFilteredGroupedByCategory() {
        lifecycleScope.launchWhenCreated {
            val filteredContentRequest =
                FilteredContentRequest(filterSelectViewModel.selectedKeywordList.value?.toList() ?: listOf())
            viewModel.getFilteredContentList(
                filteredContentRequest,
                onGetSuccess = {},
                onGetFail = { toastShortOfFailMessage("컨텐츠 가져오기") }
            )
        }
    }

    private fun getKeywordsGroupedByCategory() {
        lifecycleScope.launchWhenCreated {
            filterSelectViewModel.getKeywordsGroupedByCategory(
                onGetSuccess = {},
                onGetFail = { toastShortOfFailMessage("키워드 리스트 가져오기") }
            )
        }
    }
}