/*
 * Copyright 2020 HM Revenue & Customs
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

package utils

import controllers.routes
import models.{CheckMode, UserAnswers}
import pages.{EothoNumberOfEstablishmentsPage, EothoWhichRegionPage}
import viewmodels.AnswerRow

class CheckYourAnswersHelper(userAnswers: UserAnswers) {

  def eothoNumberOfEstablishments: Option[AnswerRow] = userAnswers.get(EothoNumberOfEstablishmentsPage) map { x =>
    AnswerRow(
      "eothoNumberOfEstablishments.checkYourAnswersLabel",
      s"eothoNumberOfEstablishments.$x",
      true,
      routes.EothoNumberOfEstablishmentsController.onPageLoad(CheckMode).url
    )
  }

  def eothoWhichRegion: Option[AnswerRow] = userAnswers.get(EothoWhichRegionPage) map { x =>
    AnswerRow(
      "eothoWhichRegion.checkYourAnswersLabel",
      s"eothoWhichRegion.$x",
      true,
      routes.EothoWhichRegionController.onPageLoad(CheckMode).url
    )
  }
}
