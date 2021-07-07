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

import play.api.data.Form
import play.api.i18n.Messages
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.RadioItem

sealed trait AbleToDo {
  val value: Int
}

object AbleToDo {

  val baseMessageKey: String = "ableToDo"

  val values: Seq[AbleToDo] = List(Yes, No)

  def from(bool: Boolean): AbleToDo = if (bool) Yes else No

  def to(yesNo: AbleToDo): Boolean = yesNo match {
    case Yes => true
    case No  => false
  }

  def options(form: Form[_])(implicit messages: Messages): Seq[RadioItem] = values.map { value =>
    RadioItem(
      id = Some(s"$baseMessageKey-${value.toString}"),
      value = Some(value.toString),
      content = Text(messages(s"$baseMessageKey.$value")),
      checked = form(baseMessageKey).value.contains(value.toString)
    )
  }

  implicit val enumerable: Enumerable[AbleToDo] =
    Enumerable(values.map(v => v.toString -> v): _*)

  case object Yes extends WithName("Yes") with AbleToDo {
    val value = 1
  }

  case object No extends WithName("No") with AbleToDo {
    val value = 0
  }

  implicit object AbleToDoWrites extends Writes[AbleToDo] {
    def writes(ableToDo: AbleToDo) = Json.toJson(ableToDo.toString)
  }

  implicit object AbleToDoReads extends Reads[AbleToDo] {
    override def reads(json: JsValue): JsResult[AbleToDo] = json match {
      case JsString(Yes.toString) => JsSuccess(Yes)
      case JsString(No.toString)  => JsSuccess(No)
      case _                      => JsError("Unknown AbleToDo")
    }
  }

}
