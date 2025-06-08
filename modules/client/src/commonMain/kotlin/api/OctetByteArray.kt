package org.nirmato.ollama.api

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(OctetByteArray.Companion::class)
public class OctetByteArray(public val value: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null || this::class != other::class) {
            return false
        }

        other as OctetByteArray

        return value.contentEquals(other.value)
    }

    override fun hashCode(): Int = value.contentHashCode()

    override fun toString(): String = "OctetByteArray(${hex(value)})"

    public companion object : KSerializer<OctetByteArray> {
        private val digits = "0123456789abcdef".toCharArray()

        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OctetByteArray", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: OctetByteArray): Unit = encoder.encodeString(hex(value.value))

        override fun deserialize(decoder: Decoder): OctetByteArray = OctetByteArray(hex(decoder.decodeString()))

        private fun hex(bytes: ByteArray): String {
            val result = CharArray(bytes.size * 2)
            var resultIndex = 0
            val digits = digits

            for (element in bytes) {
                val b = element.toInt() and 0xff
                result[resultIndex++] = digits[b shr 4]
                result[resultIndex++] = digits[b and 0x0f]
            }

            return result.concatToString()
        }

        private fun hex(s: String): ByteArray {
            val result = ByteArray(s.length / 2)
            for (idx in result.indices) {
                val srcIdx = idx * 2
                val high = s[srcIdx].toString().toInt(16) shl 4
                val low = s[srcIdx + 1].toString().toInt(16)
                result[idx] = (high or low).toByte()
            }

            return result
        }
    }
}
