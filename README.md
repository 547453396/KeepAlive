保活措施
===============

主要介绍一些保活措施，无法保证进程永生不死，只能提高存活率。

1. 通过系统广播或第三方广播拉活。通过监听开机、网络、解锁等系统广播及推送广播拉活。（具体可参见WatchmenReceiver类）
2. 利用系统Service机制拉活。即service中onStartCommand方法设置flags = START_STICKY;或者onDestroy中重新启动service。
3. startForeground设置为前台服务避免kill，Android4.3及以上需要设置id为0时通知栏才不显示该通知。
4. 双进程守护保证remote提醒进程存活率[Daemon](https://github.com/Marswin/MarsDaemon)；这个对6.0以下版本有大效果！
5. JobSchedulerService和账号同步机制补充，对华为手机有效果。
6. 1像素页面，息屏启动亮屏销毁，保证在前台。上线后有用户反馈解锁可能会出现假死现象，即该界面没有被销毁，后去除该方式。
7. 是否忽略电池优化
8. 联盟唤醒，但是必须保证app设置了可相互唤醒
9. [墨迹天气插件解决帮助](http://share.mojichina.com/clockhelp/index.html)

**参考资料**

* [ANDROID进程保活](http://www.jianshu.com/p/72b1f57a8dac) 
* [安卓保活大全](http://dev.qq.com/topic/57ac4a0ea374c75371c08ce8)  
* [保活手段](http://www.jianshu.com/p/63aafe3c12af)
* [Android闹钟设置的解决方案](http://www.androidchina.net/5338.html?sukey=3997c0719f151520cd198e139daaf7df88d30308a0d1715c2b047f78f16d21cbb1a6c7c41aa5cea192b55de79d5a61d6)

 

