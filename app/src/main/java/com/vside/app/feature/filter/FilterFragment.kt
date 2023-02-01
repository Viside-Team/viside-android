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
import com.vside.app.feature.common.view.LoginDialogFragment
import com.vside.app.feature.content.ContentActivity
import com.vside.app.feature.filter.data.request.FilteredContentRequest
import com.vside.app.util.base.BaseFragment
import com.vside.app.util.common.DataTransfer
import com.vside.app.util.common.sharedpref.SharedPrefManager
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
                if(SharedPrefManager.getBoolean(requireContext()) { IS_LOGGED_IN }) {
                    toggleScrapContent(it)
                    return@observe
                }

                val loginDialog = LoginDialogFragment()
                loginDialog.show(parentFragmentManager, loginDialog.tag)
            }

            selectedKeywordSet.observe(requireActivity()) {
                getFilteredContents()
            }
        }

        with(filterSelectViewModel) {
            isKeywordItemClicked.observe(requireActivity()) {
                it.isSelected.value?.let { bool ->
                    it.isSelected.value = !bool
                    if(it.isSelected.value == true) addKeyword(it.keyword)
                    else removeKeyword(it.keyword)
                }
            }

            isAllClearClicked.observe(requireActivity()) {
                setSelectedKeywords(null)
            }

            isCompleteClicked.observe(requireActivity()) {
                if(viewDataBinding.bindingDialogFilterSelect.tvFilterSelectComplete.isActivated) {
                    viewModel.selectedKeywordSet.value = filterSelectViewModel.selectedKeywordSet.value
                    val behavior = BottomSheetBehavior.from(viewDataBinding.clDialogFilterSelect)
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }
    }

    private fun addBottomSheetCallback() {
        val behavior = BottomSheetBehavior.from(viewDataBinding.clDialogFilterSelect)
        behavior.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    filterSelectViewModel.setSelectedKeywords(viewModel.selectedKeywordSet.value)
                }
            }

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

    private fun getFilteredContents() {
        lifecycleScope.launchWhenCreated {
            val filteredContentRequest =
                FilteredContentRequest(viewModel.selectedKeywordSet.value?.toList() ?: listOf())
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