package com.eex.common.api;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author Administrator
 * @date 2017/6/5 0005
 */

public class RxSchedulers {

    /**
     *     .compose(RxSchedulers.io_main());
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
                    @Override
                    public ObservableSource<T> apply(Observable<T> upstream) {
                        return  upstream
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                };
    }

    public static <T> FlowableTransformer<T, T> io_main_flowable() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return  upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}