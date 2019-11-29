import kotlin.reflect.full.memberProperties

@Deprecated("As of Gradle 6.0, settings file doesn't allow buildSrc to be used.")
object Modules {
    const val app = ":app"
    const val data = ":data"
    const val domain = ":domain"
    const val base = ":base"
    const val test_shared = ":test-shared"

    fun getAllModules() = Modules::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}
