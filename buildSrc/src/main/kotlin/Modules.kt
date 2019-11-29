import kotlin.reflect.full.memberProperties

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
