# 在此文件里你可以配置你不希望被使用者混淆的类或方法等


#保持 Parcelable 不被混淆
#这条规则会导致整个工程里实现了android.os.Parcelable接口的类都会被保留下来，即使这个类并没有被任何人使用
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
