/*
 * Copyright 2019 HM Revenue & Customs
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

package controllers

import config.FrontendAppConfig
import forms.{OtherQuestionsEmployeeExpensesBetaFormProvider, OtherQuestionsFormProvider}
import javax.inject.Inject
import models.{FeedbackId, Origin, OtherQuestions, OtherQuestionsEmployeeExpensesBeta}
import navigation.Navigator
import pages.GenericQuestionsPage
import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.Action
import services.AuditService
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import views.html.otherQuestionsEmployeeExpensesBeta

import scala.concurrent.ExecutionContext

class OtherQuestionsEmployeeExpensesBetaController @Inject()(appConfig: FrontendAppConfig,
                                                             override val messagesApi: MessagesApi,
                                                             navigator: Navigator,
                                                             formProvider: OtherQuestionsEmployeeExpensesBetaFormProvider,
                                                             auditService: AuditService
                                        )(implicit ec: ExecutionContext) extends FrontendController with I18nSupport {

  val form: Form[OtherQuestionsEmployeeExpensesBeta] = formProvider()

  def submitCall(origin: Origin) = routes.OtherQuestionsEmployeeExpensesBetaController.onSubmit(origin)

  def onPageLoad(origin: Origin) = Action {
    implicit request =>
      Ok(otherQuestionsEmployeeExpensesBeta(appConfig, form, submitCall(origin)))
  }

  def onSubmit(origin: Origin) = Action {
    implicit request =>
      form.bindFromRequest().fold(
        formWithErrors =>
          BadRequest(otherQuestionsEmployeeExpensesBeta(appConfig, formWithErrors, submitCall(origin))),
        value => {
          auditService.otherEmployeeExpensesBetaAudit(origin, FeedbackId.fromSession, value)
          Redirect(navigator.nextPage(GenericQuestionsPage)(origin))
        }
      )
  }
}
