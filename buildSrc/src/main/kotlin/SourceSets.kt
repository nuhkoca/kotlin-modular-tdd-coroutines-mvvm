import com.android.build.gradle.api.AndroidSourceSet
import org.gradle.api.NamedDomainObjectContainer
import java.io.File

object InternalSourceSets {
    const val MAIN = "main"
    const val TEST = "test"
    const val ANDROID_TEST = "androidTest"
}

interface SourceSetCreator {
    val name: String

    fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
    )
}

object Main : SourceSetCreator {
    override val name = InternalSourceSets.MAIN

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
    ) {
        return namedDomainObjectContainer.named(name).configure {
            java.srcDirs(File("src/main/kotlin"))
        }
    }
}

object TTest : SourceSetCreator {
    override val name = InternalSourceSets.TEST

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
    ) {
        return namedDomainObjectContainer.named(name).configure {
            java.srcDirs(File("src/test/kotlin"))
        }
    }
}

object AndroidTest : SourceSetCreator {
    override val name = InternalSourceSets.ANDROID_TEST

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
    ) {
        return namedDomainObjectContainer.named(name).configure {
            java.srcDirs(File("src/androidTest/kotlin"))
        }
    }
}
