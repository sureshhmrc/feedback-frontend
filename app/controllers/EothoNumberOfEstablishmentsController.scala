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

package controllers

import config.FrontendAppConfig
import connectors.DataCacheConnector
import controllers.actions._
import forms.EothoNumberOfEstablishmentsFormProvider
import javax.inject.Inject
import models.{Enumerable, Mode, UserAnswers}
import navigation.EothoNavigator
import pages.EothoNumberOfEstablishmentsPage
import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.MessagesControllerComponents
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import views.html.eothoNumberOfEstablishments

import scala.concurrent.{ExecutionContext, Future}

class EothoNumberOfEstablishmentsController @Inject()(
  appConfig: FrontendAppConfig,
  override val messagesApi: MessagesApi,
  dataCacheConnector: DataCacheConnector,
  navigator: EothoNavigator,
  identify: IdentifierAction,
  getData: DataRetrievalAction,
  requireData: DataRequiredAction,
  formProvider: EothoNumberOfEstablishmentsFormProvider,
  val controllerComponents: MessagesControllerComponents
)(implicit ec: ExecutionContext)
    extends FrontendBaseController with I18nSupport with Enumerable.Implicits {

  val form = formProvider()

  def onPageLoad(mode: Mode) = (identify andThen getData) { implicit request =>
    val preparedForm =
      request.userAnswers.getOrElse(UserAnswers(request.internalId)).get(EothoNumberOfEstablishmentsPage) match {
        case None        => form
        case Some(value) => form.fill(value)
      }

    Ok(eothoNumberOfEstablishments(appConfig, preparedForm, mode))
  }

  def onSubmit(mode: Mode) = (identify andThen getData).async { implicit request =>
    form
      .bindFromRequest()
      .fold(
        (formWithErrors: Form[_]) =>
          Future.successful(BadRequest(eothoNumberOfEstablishments(appConfig, formWithErrors, mode))),
        (value) => {
          val updatedAnswers =
            request.userAnswers.getOrElse(UserAnswers(request.internalId)).set(EothoNumberOfEstablishmentsPage, value)

          dataCacheConnector
            .save(updatedAnswers.cacheMap)
            .map(
              _ => Redirect(navigator.nextPage(EothoNumberOfEstablishmentsPage, mode, updatedAnswers))
            )
        }
      )
  }
}
