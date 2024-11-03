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
        public fun fromValue(value: String): KarmaBrowser? = values().firstOrNull { it.value == value }
    }
}
