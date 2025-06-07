package org.nirmato.ollama.annotations

@InternalApi
public enum class Version {
    Unreleased,
    V0_1_0;

    override fun toString(): String {
        return if (this == Unreleased) {
            name.lowercase()
        } else {
            name.drop(1).replace('_', '.')
        }
    }
}
