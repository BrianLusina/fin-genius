package com.rusticfox.fingenius.core

import com.github.ksuid.Ksuid
import com.rusticfox.fingenius.core.entities.Identifier
import java.time.Instant

/**
 * A Unique entity ID that is generated when an entity is created. This provides a default value in the constructor &
 * allows for creation & reconstitution of the ID. It's a wrap around a string ID that is generated by Ksuid that allows
 * IDs to be sortable by generation time
 * Note that this does not have to be used, but is provided as a default
 */
data class UniqueEntityId(val id: String = Ksuid.newKsuid().toString()) : Identifier<String>(id) {
    private val ksuid: Ksuid
        get() = Ksuid.fromString(id)

    /**
     * When this ID was generated
     */
    val timestamp: Int
        get() = ksuid.timestamp

    /**
     * Gets the time this ID was generated in the system time zone
     */
    val time: String
        get() = ksuid.time

    /**
     * Payload component of this ID
     */
    val payload: String
        get() = ksuid.payload

    /**
     * Returns a log representation of this ID
     */
    val logString: String
        get() = ksuid.toLogString()

    companion object {

        /**
         * Factory method that allows this ID to be generated from a given id value
         * @param value ID value
         */
        fun from(value: String): UniqueEntityId = run {
            val ksuid = Ksuid.fromString(value)
            UniqueEntityId(ksuid.toString())
        }

        /**
         * Factory method that allows the regeneration of this ID from an Instant timestamp
         * @param instant [Instant] Instant time component
         */
        fun from(instant: Instant): UniqueEntityId = run {
            val ksuid = Ksuid.fromInstant(instant)
            UniqueEntityId(ksuid.toString())
        }
    }

    override fun toString(): String {
        return ksuid.toString()
    }
}