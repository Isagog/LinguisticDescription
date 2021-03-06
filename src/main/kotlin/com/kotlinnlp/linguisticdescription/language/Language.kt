/* Copyright 2017-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.linguisticdescription.language

/**
 * Enum class containing the supported languages.
 * The IDs are sequential and start from 0, except for the [Unknown] language with ID -1.
 *
 * @property id an unique numeric ID of the language
 * @property isoCode the ISO 639-1 code of the language
 */
enum class Language(val id: Int, val isoCode: String) {
  Unknown(-1, "--"),
  Arabic(0, "ar"),
  Bulgarian(1, "bg"),
  Bengali(2, "bn"),
  Czech(3, "cs"),
  Danish(4, "da"),
  German(5, "de"),
  Greek(6, "el"),
  English(7, "en"),
  Spanish(8, "es"),
  Persian(9, "fa"),
  Finnish(10, "fi"),
  French(11, "fr"),
  Hebrew(12, "he"),
  Hindi(13, "hi"),
  Hungarian(14, "hu"),
  Italian(15, "it"),
  Japanese(16, "ja"),
  Korean(17, "ko"),
  Dutch(18, "nl"),
  Polish(19, "pl"),
  Portuguese(20, "pt"),
  Romanian(21, "ro"),
  Russian(22, "ru"),
  Slovak(23, "sk"),
  Slovenian(24, "sl"),
  Swedish(25, "sv"),
  Thai(26, "th"),
  Turkish(27, "tr"),
  Chinese(28, "zh")
}
