package build.gradle.dsl

public class KotlinCommonCompilerOptionsBuilder {
    private val arguments: MutableList<String> = mutableListOf()

    public fun add(arg: String): Boolean = arguments.add(arg)
    public fun requiresOptIn(): Boolean = arguments.add("-opt-in=kotlin.RequiresOptIn")
    public fun requiresJsr305(value: String = "strict"): Boolean = arguments.add("-Xjsr305=$value")
    public fun suppressExpectActualClasses(): Boolean = arguments.add("-Xexpect-actual-classes")
    public fun suppressVersionWarnings(): Boolean = arguments.add("-Xsuppress-version-warnings")

    public fun build(): List<String> = arguments
}
