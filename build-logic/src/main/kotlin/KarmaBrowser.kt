package build.gradle.plugins.build

public enum class KarmaBrowser(public val value: String) {
    Chrome("Chrome"),
    Chromium("Chromium"),
    Firefox("Firefox"),
    PhantomJs("Phantom JS"),
    Safari("Safari"),
    Opera("Opera"),
    Ie("Internet Explorer"),
    ;

    public companion object {
        public fun fromValueOrNull(value: String): KarmaBrowser? = entries.firstOrNull { it.value == value }
    }
}
