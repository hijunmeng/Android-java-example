package com.example.common.sharemodel;

import java.util.HashMap;

/**
 * ShareModel的存储器
 */
public class ShareModelStore {

    private final HashMap<String, ShareModel> mMap = new HashMap<>();

    final void put(String key, ShareModel shareModel) {
        ShareModel oldViewModel = mMap.put(key, shareModel);
        if (oldViewModel != null) {
            oldViewModel.onCleared();
        }
    }

    final ShareModel get(String key) {
        return mMap.get(key);
    }

    final int getSize() {
        return mMap.size();
    }

    final void remove(String key) {
        mMap.remove(key);
    }

    public final void clear() {
        mMap.clear();
    }
}