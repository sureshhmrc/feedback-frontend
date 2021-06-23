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

package models

import enumeratum.EnumEntry

import scala.collection.immutable

sealed trait AbleToDo extends EnumEntry with EnumEntryRadioItemSupport {
  val value: Int
  val messageKey = s"${AbleToDo.baseMessageKey}.${entryName.toLowerCase}"
}

object AbleToDo extends Enum[AbleToDo] with RadioSupport[AbleToDo] {
  override val baseMessageKey: String = "site"
  override val values: immutable.IndexedSeq[AbleToDo] = findValues

  def from(bool: Boolean): AbleToDo = if (bool) Yes else No

  def to(yesNo: AbleToDo): Boolean = yesNo match {
    case Yes => true
    case No  => false
  }

  implicit val enumerable: Enumerable[AbleToDo] =
    Enumerable(values.map(v => v.toString -> v): _*)

  case object Yes extends AbleToDo {
    val value = 1
  }

  case object No extends AbleToDo {
    val value = 0
  }

}
