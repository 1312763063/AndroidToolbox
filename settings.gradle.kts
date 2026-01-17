pluginManagement {
    repositories {
        // 1. 清华镜像：Google 插件
        maven("https://mirrors.tuna.tsinghua.edu.cn/google/android/")
        // 2. 阿里云：Gradle 插件
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        // 3. 阿里云：Maven Central 插件
        maven("https://maven.aliyun.com/repository/public")
        // 4. 原路兜底，镜像挂掉还能回退
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // 与上面顺序一致，先镜像后官方
        maven("https://mirrors.tuna.tsinghua.edu.cn/google/android/")
        maven("https://maven.aliyun.com/repository/public")
        google()
        mavenCentral()
    }
}

rootProject.name = "Android工具箱"
include(":app")