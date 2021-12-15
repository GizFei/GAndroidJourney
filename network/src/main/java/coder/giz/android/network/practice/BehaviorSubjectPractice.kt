package coder.giz.android.network.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coder.giz.android.network.exception.ErrorCode
import coder.giz.android.network.exception.NetworkException
import coder.giz.android.network.helper.NetDataWrapper
import coder.giz.android.yfutility.util.YFLog
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by GizFei on 2021/12/11
 */
class BehaviorSubjectPractice {

    companion object {
        private const val TAG = "BehaviorSubjectPractice"
        private inline fun log(msg: () -> String) = YFLog.w(TAG, msg())
        private inline fun logE(msg: () -> String) = YFLog.e(TAG, msg())
    }

    private val mBehaviorSubject = BehaviorSubject.create<NetDataWrapper<Int>>().toSerialized()

    private var mInteger = 0
    var nextSubjectEmitType = SubjectEmitType.NEXT

    private val mMessageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = mMessageLiveData

    fun postRequest() {
        val d = mBehaviorSubject
            .take(1)
            .doOnNext {
                log { "postRequest doOnNext after take: $it" }
                mMessageLiveData.postValue("postRequest doOnNext after take: $it")
            }
            .doOnSubscribe {
                tryEmit()
            }
            .flatMap {
                log { "postRequest flatMap" }
                if (it.data != null) {
                    Observable.just("FlatMap item: ${it.data}")
                } else {
                    Observable.error(it.throwable)
                }
            }
            .retry(3, this::isTokenExpired)
            .doOnNext {
                log { "postRequest doOnNext after retry: $it" }
                mMessageLiveData.postValue("postRequest doOnNext after retry: $it")
            }
            .doOnError {
                logE { "postRequest doOnError: $it" }
                mMessageLiveData.postValue("postRequest doOnError: $it")
            }
            .subscribe(
                {},
                {
                    logE { "subscribe handle error: $it" }
                }
            )
    }

    fun postRequestStepByStep() {
        val oneItemObservable = mBehaviorSubject.take(1)
        log { "oneItemObservable: $oneItemObservable" }

        val observable2 = oneItemObservable.doOnNext {
            log { "postRequest doOnNext after take: $it" }
            mMessageLiveData.postValue("postRequest doOnNext after take: $it")
        }
        log { "observable.doOnNext: $observable2" }

        val observable3 = observable2.doOnSubscribe {
            tryEmit()
        }
        log { "observable.doOnSubscribe: $observable3" }

        val observable4 = observable3.flatMap {
            log { "postRequest flatMap" }
            if (it.data != null) {
                Observable.just("FlatMap item: ${it.data}")
            } else {
                Observable.error(it.throwable)
            }
        }
        log { "observable.flatMap: $observable4" }

        val observable5 = observable4.retry(3, this::isTokenExpired)
        log { "observable.retry: $observable5" }

        val d = observable5
            .doOnNext {
                log { "postRequest doOnNext after retry: $it" }
                mMessageLiveData.postValue("postRequest doOnNext after retry: $it")
            }
            .doOnError {
                logE { "postRequest doOnError: $it" }
                mMessageLiveData.postValue("postRequest doOnError: $it")
            }
            .subscribe(
                {},
                {
                    logE { "subscribe handle error: $it" }
                }
            )
    }

    fun postRequestEmitter() {
        val observable = Observable.create<Int> {
            it.onNext(10)
            it.onNext(20)
            it.onNext(30)
            it.onError(NetworkException(ErrorCode.TOKEN_EXPIRED, "Token is expired!"))
        }

        val d = observable
            .doOnNext {
                log { "postRequest doOnNext after take: $it" }
                mMessageLiveData.postValue("postRequest doOnNext after take: $it")
            }
            .flatMap {
                log { "postRequest flatMap" }
                Observable.just("FlatMap item: $it")
            }
            .retry(3, this::isTokenExpired)
            .subscribe(
                {
                    log { "subscribe handle onNext: $it" }
                    mMessageLiveData.postValue("subscribe onNext: $it")
                },
                {
                    logE { "subscribe handle error: $it" }
                    mMessageLiveData.postValue("postRequest onError: $it")
                }
            )
    }

    private fun tryEmit() {
        log { "BehaviorSubjectPractice tryEmit" }
        when (nextSubjectEmitType) {
            SubjectEmitType.NEXT -> {
                mBehaviorSubject.onNext(
                    NetDataWrapper(data = mInteger++)
                )
            }
            SubjectEmitType.ERROR_TOKEN_EXPIRED -> {
                mBehaviorSubject.onNext(
                    NetDataWrapper(throwable = NetworkException(ErrorCode.TOKEN_EXPIRED, "Token is expired!"))
                )
            }
        }
    }

    private fun isTokenExpired(throwable: Throwable): Boolean {
        return throwable is NetworkException
                && throwable.errorCode == ErrorCode.TOKEN_EXPIRED
    }

    // Subject发送的数据类型
    enum class SubjectEmitType {
        NEXT,
        ERROR_TOKEN_EXPIRED,
    }
}