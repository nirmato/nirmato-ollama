package build.gradle.plugins.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.apply
import org.gradle.toolchains.foojay.FoojayToolchainsPlugin

public class FoojayBuildPlugin : Plugin<Settings> {
    override fun apply(settings: Settings): Unit = settings.run {
        apply<FoojayToolchainsPlugin>()
    }

    public companion object {
        public const val PLUGIN_ID: String = "build-foojay"
    }
}
