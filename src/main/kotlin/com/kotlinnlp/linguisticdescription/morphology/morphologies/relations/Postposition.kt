/* Copyright 2017-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.linguisticdescription.morphology.morphologies.relations

import com.kotlinnlp.linguisticdescription.morphology.POS
import com.kotlinnlp.linguisticdescription.morphology.SingleMorphology

/**
 * The 'postposition' morphology.
 *
 * @param pos the POS of this morphology
 */
sealed class Postposition(pos: POS) : SingleMorphology(pos), Relation {

  /**
   * The 'postposition' morphology.
   *
   * @property lemma the lemma
 * @property oov whether this morphology has been automatically generated by the system (Out Of Vocabulary)
   */
  class Base(override val lemma: String, override val oov: Boolean) : Postposition(POS.Postpos)

  /**
   * The 'possessive postposition' morphology.
   *
   * @property lemma the lemma
 * @property oov whether this morphology has been automatically generated by the system (Out Of Vocabulary)
   */
  class Possessive(override val lemma: String, override val oov: Boolean) : Postposition(POS.PostposPoss)
}
