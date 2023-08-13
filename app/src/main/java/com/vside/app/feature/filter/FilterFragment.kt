package com.vside.app.feature.filter

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vside.app.R
import com.vside.app.databinding.FragmentFilterBinding
import com.vside.app.feature.common.data.Content
import com.vside.app.feature.common.view.LoginDialogFragment
import com.vside.app.feature.content.ContentActivity
import com.vside.app.util.base.BaseFragment
import com.vside.app.util.common.DataTransfer
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
        observeData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) refreshData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFilteredContentList()
    }

    private fun refreshData() {
        if(SharedPrefManager.getBoolean(requireContext()) { IS_LOGGED_IN }) {
            viewModel.getFilteredContentList()
        }
    }

    private fun initData() {
        addBottomSheetCallback()
        filterSelectViewModel.getKeywordsGroupedByCategory()
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
                    viewModel.toggleScrapContent(it)
                    return@observe
                }

                val loginDialog = LoginDialogFragment()
                loginDialog.show(parentFragmentManager, loginDialog.tag)
            }

            selectedKeywordSet.observe(requireActivity()) {
                viewModel.getFilteredContentList()
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
                if(!SharedPrefManager.getBoolean(requireContext()) { IS_LOGGED_IN }) {
                    val loginDialog = LoginDialogFragment()
                    loginDialog.show(parentFragmentManager, loginDialog.tag)
                    return@observe
                }
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
}