package nl.joozd.aoc2023.days.day15

import java.util.LinkedList

class AocHashMap: MutableMap<String, Int> {
    private val boxes = Array(256){ LinkedList<KeyValuePair>() }

    /**
     * Returns a [MutableSet] of all key/value pairs in this map.
     * NOTE editing this set does not edit the HASHMAP
     */
    override val entries: MutableSet<MutableMap.MutableEntry<String, Int>>
        get() = boxes.toList().flatten().toMutableSet()

    /**
     * Returns a [MutableSet] of all keys in this map.
     */
    override val keys: MutableSet<String>
        get() = boxes.toList().flatten().map { it.key }.toMutableSet()

    /**
     * Returns the number of key/value pairs in the map.
     */
    override val size: Int
        get() = boxes.sumOf { it.size }

    /**
     * Returns a [MutableCollection] of all values in this map. Note that this collection may contain duplicate values.
     */
    override val values: MutableCollection<Int>
        get() = boxes.toList().flatten().map { it.value }.toMutableSet()

    /**
     * Removes all elements from this map.
     */
    override fun clear() {
        boxes.all { it.clear(); true }
    }

    /**
     * Returns `true` if the map is empty (contains no elements), `false` otherwise.
     */
    override fun isEmpty(): Boolean =
        boxes.all { it.isEmpty() }

    /**
     * Removes the specified key and its corresponding value from this map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in the map.
     */
    override fun remove(key: String): Int? {
        val hash = hash(key)
        return boxes[hash].firstOrNull { it.key == key }?.also{
            boxes[hash].remove(it)
        }?.value

    }

    /**
     * Updates this map with key/value pairs from the specified map [from].
     */
    override fun putAll(from: Map<out String, Int>) {
        from.forEach{
            put(it.key, it.value)
        }
    }

    /**
     * Associates the specified [value] with the specified [key] in the map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in the map.
     */
    override fun put(key: String, value: Int): Int? {
        val hash = hash(key)
        val kvp = boxes[hash].firstOrNull { it.key == key }
        val prev = kvp?.value
        kvp?.setValue(value) ?: boxes[hash].add(KeyValuePair(key, value))
        return prev
    }

    /**
     * Returns the value corresponding to the given [key], or `null` if such a key is not present in the map.
     */
    override fun get(key: String): Int? =
        boxes[hash(key)].firstOrNull { it.key == key }?.value

    /**
     * Returns `true` if the map maps one or more keys to the specified [value].
     */
    override fun containsValue(value: Int): Boolean =
        value in values

    /**
     * Returns `true` if the map contains the specified [key].
     */
    override fun containsKey(key: String): Boolean =
        boxes[hash(key)].any { it.key == key}

    fun focusingPower(): Int =
        keys.sumOf {
            getFocusingPowerOfLens(it)
        }



    private fun getFocusingPowerOfLens(lens: String): Int{
        val hash = hash(lens)
        val slot = boxes[hash].indexOfFirst { it.key == lens } + 1
        val focalLength = get(lens)!!

        return (hash+1) * slot * focalLength
    }

    class KeyValuePair(override val key: String, private var v: Int): MutableMap.MutableEntry<String, Int>{
        /**
         * Changes the value associated with the key of this entry.
         *
         * @return the previous value corresponding to the key.
         */
        override fun setValue(newValue: Int): Int {
            val prev = v
            v = newValue
            return prev
        }

        override val value get() = v
    }

    private fun hash(s: String): Int =
        s.fold(0) { acc, c ->
            (acc + c.code) * 17 % 256
        }

}