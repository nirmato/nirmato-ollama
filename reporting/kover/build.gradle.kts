import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import kotlinx.kover.gradle.plugin.dsl.GroupingEntityType

plugins {
    alias(libraries.plugins.kotlinx.kover)
}

description = "Kover Reporting"

dependencies {
    kover(project(":modules:annotations"))
    kover(project(":modules:client-ktor"))
    kover(project(":modules:models"))
}

kover {
    reports {
        verify {
            rule("Global minimum code coverage") {
                disabled = false
                groupBy = GroupingEntityType.APPLICATION

                bound {
                    minValue = 60
                    coverageUnits = CoverageUnit.LINE
                    aggregationForGroup = AggregationType.COVERED_PERCENTAGE
                }
                bound {
                    minValue = 60
                    coverageUnits = CoverageUnit.BRANCH
                    aggregationForGroup = AggregationType.COVERED_PERCENTAGE
                }
                bound {
                    minValue = 60
                    coverageUnits = CoverageUnit.INSTRUCTION
                    aggregationForGroup = AggregationType.COVERED_PERCENTAGE
                }
            }
        }

        total {
            html {
                charset = Charsets.UTF_8.name()
                onCheck = true
            }
            xml {
                onCheck = true
            }
            binary {
                onCheck = true
            }
        }
    }
}
