package com.includehelp.machinetesttask.api.interfaces;

public interface ResultListenerInterface {
    void onSuccess(int code,String message);
    void onFail(int Code,String message);
}
