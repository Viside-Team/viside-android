package com.vside.app.util.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.databinding.ViewDataBinding
import com.vside.app.R
import com.vside.app.util.base.BaseActivity
import com.vside.app.util.base.BaseBottomSheetDialogFragment
import com.vside.app.util.base.BaseViewModel

@SuppressLint("InflateParams")
class KeyboardHeightProvider(
    /** The root activity that uses this KeyboardHeightProvider  */
    private val activity: BaseActivity<out ViewDataBinding, out BaseViewModel>,
    private val bottomSheetDialogFragment : BaseBottomSheetDialogFragment<out ViewDataBinding, out BaseViewModel>? = null
) : PopupWindow(activity) {

    constructor(
        activity: BaseActivity<out ViewDataBinding, out BaseViewModel>
    ): this(activity, null)

    /** The keyboard height observer  */
    private var observer: KeyboardHeightObserver? = null

    /** The cached landscape height of the keyboard  */
    private var keyboardLandscapeHeight = 0

    /** The cached portrait height of the keyboard  */
    private var keyboardPortraitHeight = 0

    /** The view that is used to calculate the keyboard height  */
    private val popupView: View?

    /** The parent view  */
    private val parentView: View

    /**
     * Start the KeyboardHeightProvider, this must be called after the onResume of the Activity.
     * PopupWindows are not allowed to be registered before the onResume has finished
     * of the Activity.
     */
    fun start() {
        if (!isShowing && parentView.windowToken != null) {
            setBackgroundDrawable(ColorDrawable(0))
            showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0)
        }
    }

    /**
     * Close the keyboard height provider,
     * this provider will not be used anymore.
     */
    fun close() {
        observer = null
        dismiss()
    }

    /**
     * Set the keyboard height observer to this provider. The
     * observer will be notified when the keyboard height has changed.
     * For example when the keyboard is opened or closed.
     *
     * @param observer The observer to be added to this provider.
     */
    fun setKeyboardHeightObserver(observer: KeyboardHeightObserver?) {
        this.observer = observer
    }

    /**
     * Popup window itself is as big as the window of the Activity.
     * The keyboard can then be calculated by extracting the popup view bottom
     * from the activity window height.
     */
    private fun handleOnGlobalLayout() {
        val rect = Rect()
        if(bottomSheetDialogFragment == null) {
            activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        }
        else {
            bottomSheetDialogFragment.dialog?.window?.decorView?.getWindowVisibleDisplayFrame(rect)
        }

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        val orientation = getScreenOrientation()
        var statusBarHeight = 0
        val statusBarResId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if(statusBarResId > 0) {
            statusBarHeight = activity.resources.getDimensionPixelSize(statusBarResId)
        }

        var navigationBarHeight = 0
        val navBarResId = activity.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if(navBarResId > 0) {
            navigationBarHeight = activity.resources.getDimensionPixelSize(navBarResId)
        }

        val keyboardHeight =
            if(bottomSheetDialogFragment == null) {
                activity.viewDataBinding.root.rootView.height - (statusBarHeight + navigationBarHeight + rect.height())
            }
            else {
                bottomSheetDialogFragment.viewDataBinding.root.rootView.height - (statusBarHeight + navigationBarHeight + rect.height())
            }

        when {
            keyboardHeight == 0 -> {
                notifyKeyboardHeightChanged(0, orientation)
            }
            orientation == Configuration.ORIENTATION_PORTRAIT -> {
                keyboardPortraitHeight = keyboardHeight
                notifyKeyboardHeightChanged(keyboardPortraitHeight, orientation)
            }
            else -> {
                keyboardLandscapeHeight = keyboardHeight
                notifyKeyboardHeightChanged(keyboardLandscapeHeight, orientation)
            }
        }
    }

    private fun getScreenOrientation(): Int {
        return activity.resources.configuration.orientation
    }

    private fun notifyKeyboardHeightChanged(height: Int, orientation: Int) {
        observer?.onKeyboardHeightChanged(height, orientation)
    }

    /**
     * Construct a new KeyboardHeightProvider
     */
    init {
        val inflater = activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        popupView = inflater.inflate(R.layout.layout_pop_up_window, null, false)
        contentView = popupView
        softInputMode =
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
        inputMethodMode = INPUT_METHOD_NEEDED
        parentView = activity.viewDataBinding.root
        width = 0
        height = WindowManager.LayoutParams.MATCH_PARENT
        popupView?.viewTreeObserver?.addOnGlobalLayoutListener {
            handleOnGlobalLayout()
        }
    }
}