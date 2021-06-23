/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package models.ccg

import enumeratum.EnumEntry
import models.{Enum, EnumEntryRadioItemSupport, Enumerable, RadioSupport, WithName}

import scala.collection.immutable

sealed trait TreatedProfessionallyQuestion extends EnumEntry with EnumEntryRadioItemSupport

object TreatedProfessionallyQuestion
    extends Enum[TreatedProfessionallyQuestion] with RadioSupport[TreatedProfessionallyQuestion] {

  case object StronglyAgree extends WithName("StronglyAgree") with TreatedProfessionallyQuestion
  case object Agree extends WithName("Agree") with TreatedProfessionallyQuestion
  case object NeitherAgreeNorDisagree extends WithName("NeitherAgreeNorDisagree") with TreatedProfessionallyQuestion
  case object Disagree extends WithName("Disagree") with TreatedProfessionallyQuestion
  case object StronglyDisagree extends WithName("StronglyDisagree") with TreatedProfessionallyQuestion

  override val values: immutable.IndexedSeq[TreatedProfessionallyQuestion] = findValues

  implicit val enumerable: Enumerable[TreatedProfessionallyQuestion] =
    Enumerable(values.map(v => v.toString -> v): _*)
  override val baseMessageKey: String = "treatedProfessionallyQuestion"
}
