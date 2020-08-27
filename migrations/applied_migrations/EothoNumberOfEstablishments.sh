#!/bin/bash

echo "Applying migration EothoNumberOfEstablishments"

echo "Adding routes to conf/app.routes"

echo "" >> ../conf/app.routes
echo "GET        /eothoNumberOfEstablishments               controllers.EothoNumberOfEstablishmentsController.onPageLoad(mode: Mode = NormalMode)" >> ../conf/app.routes
echo "POST       /eothoNumberOfEstablishments               controllers.EothoNumberOfEstablishmentsController.onSubmit(mode: Mode = NormalMode)" >> ../conf/app.routes

echo "GET        /changeEothoNumberOfEstablishments                  controllers.EothoNumberOfEstablishmentsController.onPageLoad(mode: Mode = CheckMode)" >> ../conf/app.routes
echo "POST       /changeEothoNumberOfEstablishments                  controllers.EothoNumberOfEstablishmentsController.onSubmit(mode: Mode = CheckMode)" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "eothoNumberOfEstablishments.title = eothoNumberOfEstablishments" >> ../conf/messages.en
echo "eothoNumberOfEstablishments.heading = eothoNumberOfEstablishments" >> ../conf/messages.en
echo "eothoNumberOfEstablishments.fewerThan25 = FewerThan25" >> ../conf/messages.en
echo "eothoNumberOfEstablishments.moreThan25 = MoreThan25" >> ../conf/messages.en
echo "eothoNumberOfEstablishments.checkYourAnswersLabel = eothoNumberOfEstablishments" >> ../conf/messages.en
echo "eothoNumberOfEstablishments.error.required = Select eothoNumberOfEstablishments" >> ../conf/messages.en

echo "Adding to UserAnswersEntryGenerators"
awk '/trait UserAnswersEntryGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryEothoNumberOfEstablishmentsUserAnswersEntry: Arbitrary[(EothoNumberOfEstablishmentsPage.type, JsValue)] =";\
    print "    Arbitrary {";\
    print "      for {";\
    print "        page  <- arbitrary[EothoNumberOfEstablishmentsPage.type]";\
    print "        value <- arbitrary[EothoNumberOfEstablishments].map(Json.toJson(_))";\
    print "      } yield (page, value)";\
    print "    }";\
    next }1' ../test/generators/UserAnswersEntryGenerators.scala > tmp && mv tmp ../test/generators/UserAnswersEntryGenerators.scala

echo "Adding to PageGenerators"
awk '/trait PageGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryEothoNumberOfEstablishmentsPage: Arbitrary[EothoNumberOfEstablishmentsPage.type] =";\
    print "    Arbitrary(EothoNumberOfEstablishmentsPage)";\
    next }1' ../test/generators/PageGenerators.scala > tmp && mv tmp ../test/generators/PageGenerators.scala

echo "Adding to ModelGenerators"
awk '/trait ModelGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryEothoNumberOfEstablishments: Arbitrary[EothoNumberOfEstablishments] =";\
    print "    Arbitrary {";\
    print "      Gen.oneOf(EothoNumberOfEstablishments.values.toSeq)";\
    print "    }";\
    next }1' ../test/generators/ModelGenerators.scala > tmp && mv tmp ../test/generators/ModelGenerators.scala

echo "Adding to CacheMapGenerator"
awk '/val generators/ {\
    print;\
    print "    arbitrary[(EothoNumberOfEstablishmentsPage.type, JsValue)] ::";\
    next }1' ../test/generators/CacheMapGenerator.scala > tmp && mv tmp ../test/generators/CacheMapGenerator.scala

echo "Adding helper method to CheckYourAnswersHelper"
awk '/class/ {\
     print;\
     print "";\
     print "  def eothoNumberOfEstablishments: Option[AnswerRow] = userAnswers.get(EothoNumberOfEstablishmentsPage) map {";\
     print "    x => AnswerRow(\"eothoNumberOfEstablishments.checkYourAnswersLabel\", s\"eothoNumberOfEstablishments.$x\", true, routes.EothoNumberOfEstablishmentsController.onPageLoad(CheckMode).url)";\
     print "  }";\
     next }1' ../app/utils/CheckYourAnswersHelper.scala > tmp && mv tmp ../app/utils/CheckYourAnswersHelper.scala

echo "Migration EothoNumberOfEstablishments completed"
