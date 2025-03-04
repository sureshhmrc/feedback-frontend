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

package controllers

import play.api.mvc.Result
import play.api.test.Helpers._
import views.html.sessionExpired

import scala.concurrent.Future

class SessionExpiredControllerSpec extends ControllerSpecBase {

  val sessionExpired = inject[sessionExpired]

  "SessionExpired Controller" must {
    def result: Future[Result] =
      new SessionExpiredController(frontendAppConfig, mcc, sessionExpired).onPageLoad()(fakeRequest)

    "return 200 for a GET" in {
      status(result) mustBe OK
    }

    "return the correct view for a GET" in {
      contentAsString(result) mustBe sessionExpired(frontendAppConfig)(fakeRequest, messages).toString
    }
  }
}
