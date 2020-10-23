package br.com.gointerop.hapi.fhir.starter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.api.ServerValidationModeEnum;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.test.utilities.JettyUtil;
import ca.uhn.fhir.util.BundleUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Person;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Subscription;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.gointerop.hapi.fhir.starter.HapiProperties;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static ca.uhn.fhir.util.TestUtil.waitForSize;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleServerR4IT {

    private static final org.slf4j.Logger ourLog = org.slf4j.LoggerFactory.getLogger(ExampleServerR4IT.class);
    private static IGenericClient ourClient;
    private static FhirContext ourCtx;
    private static int ourPort;
    private static Server ourServer;

    static {
        HapiProperties.forceReload();
        HapiProperties.setProperty(HapiProperties.DATASOURCE_URL, "jdbc:h2:mem:dbr4");
        HapiProperties.setProperty(HapiProperties.FHIR_VERSION, "R4");
        HapiProperties.setProperty(HapiProperties.SUBSCRIPTION_WEBSOCKET_ENABLED, "true");
        HapiProperties.setProperty(HapiProperties.EMPI_ENABLED, "true");
        ourCtx = FhirContext.forR4();
    }

    @Test
    public void testCreateAndRead() {
        ourLog.info("Base URL is: " + HapiProperties.getServerAddress());
        String methodName = "testCreateResourceConditional";

        Patient pt = new Patient();
        pt.setActive(true);
        pt.getBirthDateElement().setValueAsString("2020-01-01");
        pt.addIdentifier().setSystem("http://foo").setValue("12345");
        pt.addName().setFamily(methodName);
        IIdType id = ourClient.create().resource(pt).execute().getId();

        Patient pt2 = ourClient.read().resource(Patient.class).withId(id).execute();
        assertEquals(methodName, pt2.getName().get(0).getFamily());

        // Test EMPI

        // Wait until the EMPI message has been processed
        await().until(() -> getPeople().size() > 0);
        List<Person> persons = getPeople();

        // Verify a Person was created that links to our Patient
        Optional<String> personLinkToCreatedPatient = persons.stream()
          .map(Person::getLink)
          .flatMap(Collection::stream)
          .map(Person.PersonLinkComponent::getTarget)
          .map(Reference::getReference)
          .filter(pid -> id.toUnqualifiedVersionless().getValue().equals(pid))
          .findAny();
        assertTrue(personLinkToCreatedPatient.isPresent());
    }

  private List<Person> getPeople() {
    Bundle bundle = ourClient.search().forResource(Person.class).cacheControl(new CacheControlDirective().setNoCache(true)).returnBundle(Bundle.class).execute();
    return BundleUtil.toListOfResourcesOfType(ourCtx, bundle, Person.class);
  }

  @Test
    public void testWebsocketSubscription() throws Exception {
        /*
         * Create subscription
         */
        Subscription subscription = new Subscription();
        subscription.setReason("Monitor new neonatal function (note, age will be determined by the monitor)");
        subscription.setStatus(Subscription.SubscriptionStatus.REQUESTED);
        subscription.setCriteria("Observation?status=final");

        Subscription.SubscriptionChannelComponent channel = new Subscription.SubscriptionChannelComponent();
        channel.setType(Subscription.SubscriptionChannelType.WEBSOCKET);
        channel.setPayload("application/json");
        subscription.setChannel(channel);

        MethodOutcome methodOutcome = ourClient.create().resource(subscription).execute();
        IIdType mySubscriptionId = methodOutcome.getId();

        // Wait for the subscription to be activated
        await().until(() -> activeSubscriptionCount() == 3);

        /*
         * Attach websocket
         */

        WebSocketClient myWebSocketClient = new WebSocketClient();
        SocketImplementation mySocketImplementation = new SocketImplementation(mySubscriptionId.getIdPart(), EncodingEnum.JSON);

        myWebSocketClient.start();
        URI echoUri = new URI("ws://localhost:" + ourPort + "/hapi-fhir-jpaserver/websocket");
        ClientUpgradeRequest request = new ClientUpgradeRequest();
        ourLog.info("Connecting to : {}", echoUri);
        Future<Session> connection = myWebSocketClient.connect(mySocketImplementation, echoUri, request);
        Session session = connection.get(2, TimeUnit.SECONDS);

        ourLog.info("Connected to WS: {}", session.isOpen());

        /*
         * Create a matching resource
         */
        Observation obs = new Observation();
        obs.setStatus(Observation.ObservationStatus.FINAL);
        ourClient.create().resource(obs).execute();

        // Give some time for the subscription to deliver
        Thread.sleep(2000);

        /*
         * Ensure that we receive a ping on the websocket
         */
        waitForSize(1, () -> mySocketImplementation.myPingCount);

        /*
         * Clean up
         */
        ourClient.delete().resourceById(mySubscriptionId).execute();
    }

  private int activeSubscriptionCount() {
    return ourClient.search().forResource(Subscription.class).where(Subscription.STATUS.exactly().code("active")).cacheControl(new CacheControlDirective().setNoCache(true)).returnBundle(Bundle.class).execute().getEntry().size();
  }

  @AfterAll
    public static void afterClass() throws Exception {
        ourServer.stop();
    }

    @BeforeAll
    public static void beforeClass() throws Exception {
        String path = Paths.get("").toAbsolutePath().toString();

        ourLog.info("Project base path is: {}", path);

        ourServer = new Server(0);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/hapi-fhir-jpaserver");
        webAppContext.setDisplayName("HAPI FHIR");
        webAppContext.setDescriptor(path + "/src/main/webapp/WEB-INF/web.xml");
        webAppContext.setResourceBase(path + "/target/hapi-fhir-jpaserver-starter");
        webAppContext.setParentLoaderPriority(true);

        ourServer.setHandler(webAppContext);
        ourServer.start();

        ourPort = JettyUtil.getPortForStartedServer(ourServer);

        ourCtx.getRestfulClientFactory().setServerValidationMode(ServerValidationModeEnum.NEVER);
        ourCtx.getRestfulClientFactory().setSocketTimeout(1200 * 1000);
        String ourServerBase = HapiProperties.getServerAddress();
        ourServerBase = "http://localhost:" + ourPort + "/hapi-fhir-jpaserver/fhir/";

        ourClient = ourCtx.newRestfulGenericClient(ourServerBase);
        ourClient.registerInterceptor(new LoggingInterceptor(true));
    }

    public static void main(String[] theArgs) throws Exception {
        ourPort = 8080;
        beforeClass();
    }
}
