pluginManagement {
    repositories {
        //google()
        //mavenCentral()
        google {
            url = uri("https://maven.aliyun.com/repository/google")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/central")
        }

        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }

        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
        }
        //gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        //google()
        //mavenCentral()
        google {
            url = uri("https://maven.aliyun.com/repository/google")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/central")
        }

        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }

        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
        }
    }
}

rootProject.name = "RecyclerViewKotlinExample"
include(":app")
