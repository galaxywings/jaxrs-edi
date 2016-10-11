# 无界EDI平台

构建基于Gradle, 在根目录下运行`gradlew`命令会自动下载Gradle.

项目包括`esb`和`dashboard`两个子项目

## esb

运行

1. 下载[Mule-standalone](https://developer.mulesoft.com/download-mule-esb-runtime)，解压而后设置该目录为环境变量`MULE_HOME`
1. 启动`$MULE_HOME\bin\mule`
1. 到项目根目录运行Gradle task进行部署
```
gradlew :esb:deployLocally
```

## dashboard

运行

    gradlew :dashboard:bootRun
