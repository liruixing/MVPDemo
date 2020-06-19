#组件化实施demo     

##技术框架
1）设计模式  MVP 
2）ARouter+butterknife+rxjava2+retrofit2

##适合人群
组件化初学者、mvp使用者同时希望接触组件化

##框架目录大体讲解

1）config.gradle文件进行业务module，library中共性第三方库的声明（原则上而言 library 项目中需要可以随时切换到其他项目）；
2）module.build.gradle 抽取业务module的共性gradle的定义；

各module层级关系如下：
第一层：libres 、Libbase、LibNetwork ....各种library
第二层：libcommon   (其实说白了就是要集成上面的library，供业务使用)
第三层：各个业务module、app壳

##使用
集成打包：
1）gradle.properties 文件中isBuildModule=false;
2）屏蔽module中作为应用运行的特定代码，如demo 中 CommonManager.getInstance().applicationCreate(getApplication());

分开module独立运行：
1）gradle.properties 文件中isBuildModule=true;
2）在module中添加一些特定代码，确保能运行 如 CommonManager.getInstance().applicationCreate(getApplication())，
此处可通过建立module的application来解决，各个module建一个application，然后在alone文件夹中注册文件中指定;

坑点：
虽然butterknife提供了组件化的调用方案，但是切换成单一运行下还得把R2 改为R  ；

之后会提交mvvm模式的demo，供初学者参考
其实改动量就是切换一个base  & 在module中使用databinding



