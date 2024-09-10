package org.nirmato.ollama.infrastructure

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import io.ktor.util.hex

@Serializable(OctetByteArray.Companion::class)
public class OctetByteArray(public val value: ByteArray) {
    public companion object : KSerializer<OctetByteArray> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OctetByteArray", PrimitiveKind.STRING)
        override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: OctetByteArray): Unit = encoder.encodeString(hex(value.value))
        override fun deserialize(decoder: Decoder): OctetByteArray = OctetByteArray(hex(decoder.decodeString()))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as OctetByteArray
        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }

    override fun toString(): String {
        return "OctetByteArray(${hex(value)})"
    }
}
