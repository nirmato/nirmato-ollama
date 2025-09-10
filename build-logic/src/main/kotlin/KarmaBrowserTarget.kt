package build.gradle.plugins.build

import org.gradle.api.GradleException

public sealed class KarmaBrowserTarget(
    public val browser: KarmaBrowser,
    public val channel: KarmaBrowserChannel,
    public val headless: Boolean,
) {
    public object Chrome : KarmaBrowserTarget(KarmaBrowser.Chrome, KarmaBrowserChannel.Release, false)
    public object ChromeHeadless : KarmaBrowserTarget(KarmaBrowser.Chrome, KarmaBrowserChannel.Release, true)
    public object ChromeCanary : KarmaBrowserTarget(KarmaBrowser.Chrome, KarmaBrowserChannel.Canary, false)
    public object ChromeCanaryHeadless : KarmaBrowserTarget(KarmaBrowser.Chrome, KarmaBrowserChannel.Canary, true)
    public object Chromium : KarmaBrowserTarget(KarmaBrowser.Chromium, KarmaBrowserChannel.Release, false)
    public object ChromiumHeadless : KarmaBrowserTarget(KarmaBrowser.Chromium, KarmaBrowserChannel.Release, true)
    public object Firefox : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Release, false)
    public object FirefoxHeadless : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Release, true)
    public object FirefoxAurora : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Aurora, false)
    public object FirefoxAuroraHeadless : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Aurora, true)
    public object FirefoxDeveloper : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Developer, false)
    public object FirefoxDeveloperHeadless : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Developer, true)
    public object FirefoxNightly : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Nightly, false)
    public object FirefoxNightlyHeadless : KarmaBrowserTarget(KarmaBrowser.Firefox, KarmaBrowserChannel.Nightly, true)
    public object Safari : KarmaBrowserTarget(KarmaBrowser.Safari, KarmaBrowserChannel.Release, false)
    public object Opera : KarmaBrowserTarget(KarmaBrowser.Opera, KarmaBrowserChannel.Release, false)
    public object Ie : KarmaBrowserTarget(KarmaBrowser.Ie, KarmaBrowserChannel.Release, false)

    public companion object {
        private val targets: List<KarmaBrowserTarget> = listOf(
            // Headless
            ChromeHeadless,
            ChromeCanaryHeadless,
            ChromiumHeadless,
            FirefoxHeadless,
            FirefoxAuroraHeadless,
            FirefoxDeveloperHeadless,
            FirefoxNightlyHeadless,
            // GUI
            Chrome,
            ChromeCanary,
            Chromium,
            Firefox,
            FirefoxAurora,
            FirefoxDeveloper,
            FirefoxNightly,
            Safari,
            Opera,
            Ie,
        )

        public fun valueOf(
            browser: KarmaBrowser,
            channel: KarmaBrowserChannel = KarmaBrowserChannel.Release,
            headless: Boolean = true,
        ): KarmaBrowserTarget {
            targets.forEach {
                if (it.browser == browser && it.channel == channel && it.headless == headless) {
                    return it
                }
            }

            throw GradleException("Unknown KarmaBrowserTarget")
        }
    }
}
