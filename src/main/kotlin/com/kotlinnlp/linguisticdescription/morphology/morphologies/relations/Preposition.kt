/* Copyright 2017-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.linguisticdescription.morphology.morphologies.relations

import com.kotlinnlp.linguisticdescription.morphology.POS
import com.kotlinnlp.linguisticdescription.morphology.SingleMorphology
import com.kotlinnlp.linguisticdescription.morphology.properties.Gender
import com.kotlinnlp.linguisticdescription.morphology.properties.GrammaticalCase
import com.kotlinnlp.linguisticdescription.morphology.properties.Number
import com.kotlinnlp.linguisticdescription.morphology.properties.interfaces.CaseDeclinable
import com.kotlinnlp.linguisticdescription.morphology.properties.interfaces.Genderable
import com.kotlinnlp.linguisticdescription.morphology.properties.interfaces.Numerable

/**
 * The 'preposition' morphology.
 *
 * @param pos the POS of this morphology
 */
sealed class Preposition(pos: POS) : SingleMorphology(pos), Relation {

  /**
   * The 'preposition' morphology.
   *
   * @property lemma the lemma
   * @property oov whether this morphology has been automatically generated by the system (Out Of Vocabulary)
   */
  class Base(override val lemma: String, override val oov: Boolean) : Preposition(POS.Prep)

  /**
   * The 'articulated preposition' morphology.
   *
   * @property lemma the lemma
   * @property oov whether this morphology has been automatically generated by the system (Out Of Vocabulary)
   * @property gender the 'gender' grammatical property
   * @property number the 'number' grammatical property
   * @property case the 'grammatical case' grammatical property
   */
  class Articulated(
    override val lemma: String,
    override val oov: Boolean,
    override val gender: Gender = Gender.Undefined,
    override val number: Number = Number.Undefined,
    override val case: GrammaticalCase = GrammaticalCase.Undefined
  ) : Preposition(POS.PrepArt), Genderable, Numerable, CaseDeclinable

  /**
   * The 'possessive preposition' morphology.
   *
   * @property lemma the lemma
   * @property oov whether this morphology has been automatically generated by the system (Out Of Vocabulary)
   */
  class Possessive(override val lemma: String, override val oov: Boolean) : Preposition(POS.PrepPoss)

  /**
   * The 'comparative preposition' morphology.
   *
   * @property lemma the lemma
   * @property oov whether this morphology has been automatically generated by the system (Out Of Vocabulary)
   */
  class Comparative(override val lemma: String, override val oov: Boolean) : Preposition(POS.PrepCompar)
}
