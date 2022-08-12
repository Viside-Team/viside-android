package com.viside.app.util.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.viside.app.util.log.VisideLog
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 별도의 데이터 전달 없이 이벤트를 트리거하기 위한 클래스
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            VisideLog.w("여러 개의 observer 가 등록되었습니다. 하나의 observer 만 변화를 감지할 수 있음을 주의하세요.")
        }

        // 내부의 MutableLiveData를 관찰
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * 호출을 clean 하게 하기 위해 T 가 Void 인 경우 사용
     */
    @MainThread
    fun call() {
        value = null
    }
}