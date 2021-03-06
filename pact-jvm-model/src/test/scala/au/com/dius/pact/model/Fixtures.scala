package au.com.dius.pact.model

import org.json4s.JsonAST.{JBool, JObject}
import org.json4s.jackson.JsonMethods.compact

object Fixtures {
  val provider = Provider("test_provider")
  val consumer = Consumer("test_consumer")

  val request = Request(HttpMethod.Get, "/", "q=p&q=p2&r=s", Map("testreqheader" -> "testreqheadervalue"),
    compact(JObject("test" -> JBool(true))), null)

  val response = Response(200,
    Map("testreqheader" -> "testreqheaderval"),
    compact(JObject("responsetest" -> JBool(true))), null)

  val requestWithMatchers = Request(HttpMethod.Get, "/", "q=p&q=p2&r=s", Map("testreqheader" -> "testreqheadervalue"),
    compact(JObject("test" -> JBool(true))), Map("$.body.test" -> Map("match" -> "type")))

  val responseWithMatchers = Response(200,
    Map("testreqheader" -> "testreqheaderval"),
    compact(JObject("responsetest" -> JBool(true))), Map("$.body.responsetest" -> Map("match" -> "type")))

  val interaction = Interaction(
    description = "test interaction",
    providerState = Some("test state"),
    request = request,
    response = response
  )

  val interactionsWithMatchers = List(Interaction(
    description = "test interaction with matchers",
    providerState = Some("test state"),
    request = requestWithMatchers,
    response = responseWithMatchers
  ))

  val interactionsWithNoBodies = List(Interaction(
    description = "test interaction with no bodies",
    providerState = Some("test state"),
    request = request.copy(body = None),
    response = response.copy(body = None)
  ))

  val interactions = List(interaction)

  val pact: Pact = Pact(
    provider = provider,
    consumer = consumer,
    interactions = interactions
  )

  val pactWithMatchers: Pact = Pact(
    provider = provider,
    consumer = consumer,
    interactions = interactionsWithMatchers
  )

  val pactWithNoBodies: Pact = Pact(
    provider = provider,
    consumer = consumer,
    interactions = interactionsWithNoBodies
  )

  val pactDecodedQuery = Pact(provider = provider,
    consumer = consumer,
    interactions = List(interaction.copy(
      request = request.copy(query = Some(
        Map("datetime" -> List("2011-12-03T10:15:30+01:00"), "description" -> List("hello world!"))))
    )))
}
