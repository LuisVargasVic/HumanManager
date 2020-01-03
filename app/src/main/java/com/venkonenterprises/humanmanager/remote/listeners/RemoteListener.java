package com.venkonenterprises.humanmanager.remote.listeners;

public interface RemoteListener {
    void preExecute();
    void postExecute(Boolean result);
}
