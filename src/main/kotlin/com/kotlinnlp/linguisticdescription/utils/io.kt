/* Copyright 2017-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.linguisticdescription.utils

import java.io.*
import java.util.*

/**
 * The Serializer provides methods to serialize and deserialize any object
 */
object Serializer {

  /**
   * Serialize an object and write it to an output stream.
   *
   * @param outputStream the [OutputStream] in which to write the serialized the object
   */
  fun <T> serialize(obj: T, outputStream: OutputStream) {

    val oos = ObjectOutputStream(outputStream)

    oos.writeObject(obj)
    oos.close()
  }

  /**
   * Read an object (serialized) from an input stream and decode it.
   *
   * @param inputStream the [InputStream] from which to read the serialized object
   *
   * @return the object read from [inputStream] and decoded
   */
  @Suppress("UNCHECKED_CAST")
  fun <T> deserialize(inputStream: InputStream): T {

    val ois = ObjectInputStream(inputStream)
    val obj = ois.readObject() as T

    ois.close()

    return obj
  }
}

/**
 * Loop through the lines of a file.
 *
 * @param filename the name of the file
 * @param callback the callback called for each line
 */
fun forEachLine(filename: String, callback: (String) -> Unit) {

  val inputStream = FileInputStream(filename)
  val sc = Scanner(inputStream)

  while (sc.hasNextLine()) {
    callback(sc.nextLine())
  }

  if (sc.ioException() != null) {
    throw sc.ioException()
  }

  inputStream.close()
  sc.close()
}

/**
 * @return the number of lines of this file
 */
fun getNumOfLines(filename: String): Int {

  var count = 0

  val newLineByte = '\n'.toByte()
  val inputStream = FileInputStream(filename)
  val buffer = ByteArray(8192)
  var n: Int = inputStream.read(buffer)

  while (n > 0) {

    (0 until n).forEach { i -> if (buffer[i] == newLineByte) count++ }

    n = inputStream.read(buffer)
  }

  inputStream.close()

  return count
}

/**
 * @return this [String] converted to an [InputStream]
 */
fun String.toInputStream(): InputStream = ByteArrayInputStream(this.toByteArray())
