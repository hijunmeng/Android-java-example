package com.example.common.sharemodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * 在不同的activity和fragment中共享{@link ShareModel}，在所有activity和fragment都销毁时自动清理资源
 * 用法实例：
 * 在activity中，
 *
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * mShareModel = ShareModelProvider.get(this,XXXShareModel.class);
 * }
 * <p>
 * 在fragment中，
 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
 * Bundle savedInstanceState) {
 * mShareModel = ShareModelProvider.get(this,XXXShareModel.class);
 * }
 */
public class ShareModelProvider {
    public interface Factory {
        @NonNull
        <T extends ShareModel> T create(@NonNull Class<T> modelClass);
    }

    private static ShareModelStore mShareModelStore;//key:以XXXShareModel.getClass().getCanonicalName();
    private static LifecycleStore mLifecycleStore;//key:以LifecycleStore.toString()
    private static RefCountStore mRefCountStore;//key:以XXXShareModel.getClass().getCanonicalName(); 当引用计数为0时表示ShareModel可以从ShareModelStore中清除并调用ShareModel.onCleared()

    /**
     * 只要cls完整类名相同，则能得到同一个实例
     *
     * @param shareModelCls ShareModel的Class
     * @param factory       shareDataCls的构建工厂，可为null,为null时cls必须拥有空构造函数，否则抛出异常
     * @param <T>           T extends ShareModel
     * @return shareModelCls实例
     */
    public static synchronized <T extends ShareModel> T get(@NonNull LifecycleOwner lifecycleOwner, @NonNull Class<T> shareModelCls, @Nullable Factory factory) {
        if (mShareModelStore == null) {
            mShareModelStore = new ShareModelStore();
        }
        if (mRefCountStore == null) {
            mRefCountStore = new RefCountStore();
        }
        if (mLifecycleStore == null) {
            mLifecycleStore = new LifecycleStore();
        }

        final String canonicalName = shareModelCls.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }

        final String lifecycleKey = lifecycleOwner.toString();//发现fragment重写了toString,因此名字变得好长
        if (mLifecycleStore.get(lifecycleKey) == null) {
            mLifecycleStore.put(lifecycleKey, lifecycleOwner);
            mRefCountStore.addOne(canonicalName);
            lifecycleOwner.getLifecycle().addObserver(new MyLifecycleEventObserver(canonicalName));
        }

        ShareModel shareModel = mShareModelStore.get(canonicalName);
        if (shareModel == null) {
            if (factory == null) {
                //使用默认的工厂
                factory = NewInstanceFactory.getInstance();
            }
            shareModel = factory.create(shareModelCls);
            mShareModelStore.put(canonicalName, shareModel);

        }
        return (T) shareModel;
    }

    public static <T extends ShareModel> T get(@NonNull LifecycleOwner lifecycleOwner, @NonNull Class<T> cls) {
        return get(lifecycleOwner, cls, NewInstanceFactory.getInstance());
    }

    /**
     * 是否正在销毁重建
     *
     * @param owner
     * @return true--表示正在销毁重建
     */
    public static boolean isChangingConfigurations(LifecycleOwner owner) {
        if (owner instanceof Fragment) {
            FragmentActivity activity = ((Fragment) owner).getActivity();
            if (activity == null) {
                return false;
            }
            return activity.isChangingConfigurations();
        }
        if (owner instanceof FragmentActivity) {
            return ((FragmentActivity) owner).isChangingConfigurations();
        }
        return false;
    }

    private static class MyLifecycleEventObserver implements LifecycleEventObserver {

        private String mShareModelStoreKey;

        public MyLifecycleEventObserver(String shareModelStoreKey) {
            mShareModelStoreKey = shareModelStoreKey;
        }

        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            if (event != Lifecycle.Event.ON_DESTROY) {
                return;
            }

            String key = source.toString();
            mLifecycleStore.remove(key);
            Log.i("123456", key + "已从LifecycleStore移除，现在剩余个数：" + mLifecycleStore.getSize());
            int count = mRefCountStore.subtractOne(mShareModelStoreKey);
            if (count == 0) {
                if (isChangingConfigurations(source)) {//为true表示正在销毁重建，此时的ShareModel不应该移除掉
                    Log.i("123456", "正发生销毁重建");
                    return;
                }
                ShareModel shareModel = mShareModelStore.get(mShareModelStoreKey);
                mShareModelStore.remove(mShareModelStoreKey);
                Log.i("123456", mShareModelStoreKey + "已从ShareDataStore移除,现在剩余个数：" + mShareModelStore.getSize());
                if (shareModel != null) {
                    shareModel.onCleared();
                }
            }
        }
    }

    /**
     * Simple factory, which calls empty constructor on the give class.
     */
    public static class NewInstanceFactory implements Factory {

        private static NewInstanceFactory sInstance;

        /**
         * Retrieve a singleton instance of NewInstanceFactory.
         *
         * @return A valid {@link NewInstanceFactory}
         */
        @NonNull
        static NewInstanceFactory getInstance() {
            if (sInstance == null) {
                sInstance = new NewInstanceFactory();
            }
            return sInstance;
        }

        @SuppressWarnings("ClassNewInstance")
        @NonNull
        @Override
        public <T extends ShareModel> T create(@NonNull Class<T> modelClass) {
            //noinspection TryWithIdenticalCatches
            try {
                return modelClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
    }

}