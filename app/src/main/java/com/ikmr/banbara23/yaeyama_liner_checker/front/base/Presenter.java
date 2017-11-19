package com.ikmr.banbara23.yaeyama_liner_checker.front.base;

/**
 * Presenterのベース
 */
public interface Presenter<V> {

    void attachView(V view);

    void detachView();
}
