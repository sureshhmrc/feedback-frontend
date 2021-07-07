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
import viewmodels.RadioOption

sealed trait HowDoYouFeelQuestion {
  val value: Int
}

object HowDoYouFeelQuestion {

  val baseMessageKey: String = "howDoYouFeelQuestion"

  case object VerySatisfied extends WithName("VerySatisfied") with HowDoYouFeelQuestion {
    val value = 5
  }
  case object Satisfied extends WithName("Satisfied") with HowDoYouFeelQuestion {
    val value = 4
  }
  case object Moderate extends WithName("Moderate") with HowDoYouFeelQuestion {
    val value = 3
  }
  case object Dissatisfied extends WithName("Dissatisfied") with HowDoYouFeelQuestion {
    val value = 2
  }
  case object VeryDissatisfied extends WithName("VeryDissatisfied") with HowDoYouFeelQuestion {
    val value = 1
  }

  val values: Seq[HowDoYouFeelQuestion] =
    List(VerySatisfied, Satisfied, Moderate, Dissatisfied, VeryDissatisfied)

  val options: Seq[RadioOption] = values.map { value =>
    RadioOption("howDoYouFeelQuestion", value.toString)
  }

  def options(form: Form[_])(implicit messages: Messages): Seq[RadioItem] = values.map { value =>
    RadioItem(
      id = Some(s"$baseMessageKey-${value.toString}"),
      value = Some(value.toString),
      content = Text(messages(s"$baseMessageKey.$value")),
      checked = form(baseMessageKey).value.contains(value.toString)
    )
  }

  implicit val enumerable: Enumerable[HowDoYouFeelQuestion] =
    Enumerable(values.map(v => v.toString -> v): _*)

  implicit object HowDoYouFeelQuestionWrites extends Writes[HowDoYouFeelQuestion] {
    def writes(howDoYouFeelQuestion: HowDoYouFeelQuestion) = Json.toJson(howDoYouFeelQuestion.toString)
  }

  implicit object HowDoYouFeelQuestionReads extends Reads[HowDoYouFeelQuestion] {
    override def reads(json: JsValue): JsResult[HowDoYouFeelQuestion] = json match {
      case JsString(VerySatisfied.toString)    => JsSuccess(VerySatisfied)
      case JsString(Satisfied.toString)        => JsSuccess(Satisfied)
      case JsString(Moderate.toString)         => JsSuccess(Moderate)
      case JsString(Dissatisfied.toString)     => JsSuccess(Dissatisfied)
      case JsString(VeryDissatisfied.toString) => JsSuccess(VeryDissatisfied)
      case _                                   => JsError("Unknown howDoYouFeelQuestion")
    }
  }
}
