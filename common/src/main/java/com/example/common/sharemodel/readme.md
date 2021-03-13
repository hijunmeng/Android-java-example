# 共享数据功能
* 本模块主要实现了在不同的Activity或Fragment之间共享数据的功能
* 核心思想是基于引用计数和Lifecycle来达到自动清理数据的功能，避免内存泄漏

## 用法说明
### 共享普通数据
* 继承ShareModel,比如XXXShareModel
* 使用ShareModelProvider,如下
```
ShareModelProvider.get(this,XXXShareModel.class);
```
### 共享ViewModel
* 继承ViewModel，比如XXXViewModel
* 使用ShareModelProvider,如下
```
ShareViewModelProvider.get(this,XXXViewModel.class);
```
