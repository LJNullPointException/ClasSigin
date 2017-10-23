package com.design.classsign.common.retrofit;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.design.classsign.common.ApiService;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by yuer on 2016/8/24.
 */
public class RequestEntity {

    private static int ORIGINAL_DELAY_IN_MILLIS = 180000;// 默认3分钟

    public static ApiService getApiService() {
        return RetrofitNewInstance.getInstance().create(ApiService.class);
    }

    public static void setOriginalDelayInMillis(int originalDelayInMillis) {
        ORIGINAL_DELAY_IN_MILLIS = originalDelayInMillis;
    }
//    public static Subscription request(Observable<Object> observable, ResponseInterface responseIn, String requestTag,boolean needCache) {
//        return request(observable, responseIn, requestTag, needCache);
//    }

    /**
     * @param observable
     * @param responseIn 请求回调接口
     * @param requestTag 请求标识，这里两个作用，用着缓存和返回的标识
     * @param needCache  缓存标识符，是否缓存，默认否
     * @param cacheKey  缓存key，需要唯一值对应每个请求
     * @return
     */
    public static Subscription request(Observable<Object> observable,
                                       ResponseInterface responseIn,
                                       final String requestTag,
                                       final boolean needCache,
                                       @Nullable final String cacheKey) {
        final ResponseInterface response = responseIn;
        Observer observer = new Observer<String>() {

            @Override
            public void onCompleted() {
                response.onCompleted(requestTag);
            }

            @Override
            public void onError(Throwable e) {
                String object = null;
                if (needCache && !TextUtils.isEmpty(cacheKey)) {

                    //TODO 缓存数据，这里用不到
//                    object = CacheDataBase.readCache(cacheKey);
                }
                if (object != null && !object.equals("")) {
                    response.onSuccess(object, requestTag);
                } else {
                    response.onError(e, requestTag);
                }
            }

            @Override
            public void onNext(String object) {
                if (needCache && !TextUtils.isEmpty(cacheKey)) {
//
// TODO 缓存数据
//    CacheDataBase.saveCache(object, cacheKey);
                }
                response.onSuccess(object, requestTag);
            }
        };
        Subscription subscruiption = observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        response.onStart(requestTag);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscruiption;
    }

    /**
     * 轮询请求，用于心跳，注意，这个方法不会在主线程执行，请求网络和返回数据的处理都是在子线程中
     * @param observable
     * @param responseIn
     * @param requestTag
     * @param needCache
     * @param needRepeat
     * @param cacheKey
     * @return
     */
    public static Subscription request(Observable<Object> observable,
                                       ResponseInterface responseIn,
                                       final String requestTag,
                                       final boolean needCache,
                                       final boolean needRepeat,
                                       @Nullable final String cacheKey) {

        final ResponseInterface response = responseIn;
        Observer observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                response.onCompleted(requestTag);
            }

            @Override
            public void onError(Throwable e) {
                String object = null;
                if (needCache && !TextUtils.isEmpty(cacheKey)) {
// 缓存数据
// object = CacheDataBase.readCache(cacheKey);
                }
                if (object != null && !object.equals("")) {
                    response.onSuccess(object, requestTag);
                } else {
                    response.onError(e, requestTag);
                }
            }

            @Override
            public void onNext(String object) {
                if (needCache && !TextUtils.isEmpty(cacheKey)) {
//
// TODO 缓存数据
// CacheDataBase.saveCache(object, cacheKey);
                }
                response.onSuccess(object, requestTag);
            }
        };
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        response.onStart(requestTag);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()) // 在子线程回调
                .takeUntil(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object response) {
                        /**
                         * /** 在这里，我们可以检查服务器返回的数据是否正确，和决定我们是否应该
                         *  停止轮询。
                         */
//                        NullParamsRequestBean bean = JSONUtils.json2Bean(response.toString(),NullParamsRequestBean.class);
                        return false;
                    }
                })
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
//                        return observable.compose(zipWithFlatMap());
                        return observable.flatMap(new Func1<Void, Observable<?>>() {
                            @Override
                            public Observable<?> call(Void aVoid) {
//                                if(mCounter > ATTEMPTS){
//                                    // 由我们自己终止
//                                    throw new RuntimeException();
//                                }
                                return Observable.timer(ORIGINAL_DELAY_IN_MILLIS, TimeUnit.MILLISECONDS);
                            }
                        });
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {

                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                return Observable.timer(ORIGINAL_DELAY_IN_MILLIS, TimeUnit.MILLISECONDS);
                            }
                        });
                    }
                })
                .subscribe(observer);
        return subscription;
    }


    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
//    public static <T> Observable.Transformer<ResponseBean<T>, T> handleResult() {
//        return new Observable.Transformer<ResponseBean<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<ResponseBean<T>> tObservable) {
//                return tObservable.flatMap(new Func1<ResponseBean<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(ResponseBean<T> result) {
//                        LogUtil.i("RequestEntity","result from network : " + result);
//                        if (result.getCode() == 0) {
//                            return createData(result.getData());
//                        } else {
//                            return Observable.error(new ServerException(result.getCode(), result.getMessage()));
//                        }
//                    }
//                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//        return null;
//    }

//    /**
//     * 创建成功的数据
//     *
//     * @param data
//     * @param <T>
//     * @return
//     */
//    private static <T> Observable<T> createData(final T data) {
//        return Observable.create(new Observable.OnSubscribe<T>() {
//            @Override
//            public void call(Subscriber<? super T> subscriber) {
//                try {
//                    subscriber.onNext(data);
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//
//    }

}



