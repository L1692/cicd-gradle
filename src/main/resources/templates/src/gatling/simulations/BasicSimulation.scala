import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://99.80.28.74:9000/sonar") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Scenario Name") // A scenario is a chain of requests and pauses
    .exec(http("request_1")
    .get("/"))
    .pause(7) // Note that Gatling has recorder real time pauses
    .exec(http("request_2")
    .get("/dashboard?id=cicdapp%3Ahello-world"))
    .pause(2)
    .exec(http("request_3")
      .get("/computers/6"))
    .pause(3)
    .exec(http("request_4")
      .get("/"))
    .pause(2)
    .exec(http("request_5")
      .get("/project/issues?id=cicdapp%3Ahello-world&resolved=false"))
    .pause(670 milliseconds)
    .exec(http("request_6")
      .get("/component_measures?id=cicdapp%3Ahello-world"))
    .pause(629 milliseconds)
    .exec(http("request_7")
      .get("/code?id=cicdapp%3Ahello-world"))
    .pause(734 milliseconds)
    .exec(http("request_8")
      .get("/project/activity?id=cicdapp%3Ahello-world"))

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}