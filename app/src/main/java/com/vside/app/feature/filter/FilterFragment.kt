package com.vside.app.feature.filter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vside.app.R
import com.vside.app.databinding.FragmentFilterBinding
import com.vside.app.feature.common.data.Content
import com.vside.app.feature.common.data.ContentItem
import com.vside.app.feature.content.ContentActivity
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.util.base.BaseFragment
import com.vside.app.util.common.DataTransfer
import com.vside.app.util.common.sharedpref.SharedPrefManager
import com.vside.app.util.log.VsideLog
import org.koin.android.ext.android.inject
import java.math.BigInteger

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
        observeData()
    }

    private fun initData() {
        addBottomSheetCallback()
        getFilteredGroupedByCategory()
        getKeywordsGroupedByCategory()
    }

    private fun observeData() {
        with(viewModel) {
            isContentItemClicked.observe(requireActivity()) {
                startActivity(
                    Intent(requireContext(), ContentActivity::class.java)
                        .putExtra(DataTransfer.CONTENT, Content(it))
                )
            }

            isContentBookmarkClicked.observe(requireActivity()) {
                toggleScrapContent(it)
            }
        }
    }

    private fun addBottomSheetCallback() {
        val behavior = BottomSheetBehavior.from(viewDataBinding.clDialogFilterSelect)
        behavior.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if(slideOffset<0.5) viewDataBinding.bindingDialogFilterSelect.tvFilterSelectComplete.visibility = View.INVISIBLE
                else viewDataBinding.bindingDialogFilterSelect.tvFilterSelectComplete.visibility = View.VISIBLE
            }
        })
    }

    private fun toggleScrapContent(contentItem: ContentItem) {
        lifecycleScope.launchWhenCreated {
            if(contentItem.isScrapClickable.value == true) {
                contentItem.isScrapClickable.value = false
                val isBookmarked = contentItem.isBookmark.value
                isBookmarked?.let {
                    contentItem.isBookmark.value = !isBookmarked
                }
                viewModel.toggleScrapContent(
                    contentItem.contentId ?: BigInteger("0"),
                    onPostSuccess = {
                        contentItem.isScrapClickable.value = true
                    },
                    onPostFail = {
                        toastShortOfFailMessage("스크랩 / 스크랩 취소")
                        contentItem.isScrapClickable.value = false
                        contentItem.isBookmark.value = isBookmarked
                    }
                )
            }
        }
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