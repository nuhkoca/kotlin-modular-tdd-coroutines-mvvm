import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

private object InternalBuildType {
    const val RELEASE = "release"
    const val DEBUG = "debug"
}

interface BuildTypeCreator {
    val name: String

    fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<BuildType>,
        project: Project
    ): BuildType
}

object Debug : BuildTypeCreator {
    override val name = InternalBuildType.DEBUG

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<BuildType>,
        project: Project
    ): BuildType {
        return namedDomainObjectContainer.maybeCreate(name).apply {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
}

object Release : BuildTypeCreator {
    override val name = InternalBuildType.RELEASE

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<BuildType>,
        project: Project
    ): BuildType {
        return namedDomainObjectContainer.maybeCreate(name).apply {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt",
                    project.layout.buildDirectory
                ),
                "proguard-rules.pro"
            )
        }
    }
}
