package br.com.gointerop.hapi.fhir.starter;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.gointerop.hapi.fhir.empi.EmpiConfig;
import ca.uhn.fhir.context.FhirVersionEnum;
import ca.uhn.fhir.jpa.empi.config.EmpiConsumerConfig;
import ca.uhn.fhir.jpa.empi.config.EmpiSubmitterConfig;
import ca.uhn.fhir.jpa.subscription.channel.config.SubscriptionChannelConfig;
import ca.uhn.fhir.jpa.subscription.match.config.SubscriptionProcessorConfig;
import ca.uhn.fhir.jpa.subscription.match.config.WebsocketDispatcherConfig;
import ca.uhn.fhir.jpa.subscription.submit.config.SubscriptionSubmitterConfig;

public class ApplicationContext extends AnnotationConfigWebApplicationContext {

	public ApplicationContext() {
		FhirVersionEnum fhirVersion = HapiProperties.getFhirVersion();
		if (fhirVersion == FhirVersionEnum.DSTU2) {
			register(FhirServerConfigDstu2.class, FhirServerConfigCommon.class);
		} else if (fhirVersion == FhirVersionEnum.DSTU3) {
			register(FhirServerConfigDstu3.class, FhirServerConfigCommon.class);
		} else if (fhirVersion == FhirVersionEnum.R4) {
			register(FhirServerConfigR4.class, FhirServerConfigCommon.class);
		} else if (fhirVersion == FhirVersionEnum.R5) {
			register(FhirServerConfigR5.class, FhirServerConfigCommon.class);
		} else {
			throw new IllegalStateException();
		}

		if (HapiProperties.getSubscriptionWebsocketEnabled()) {
			register(WebsocketDispatcherConfig.class);
		}

		if (HapiProperties.getSubscriptionEmailEnabled() || HapiProperties.getSubscriptionRestHookEnabled()
				|| HapiProperties.getSubscriptionWebsocketEnabled()) {
			register(SubscriptionSubmitterConfig.class);
			register(SubscriptionProcessorConfig.class);
			register(SubscriptionChannelConfig.class);
		}

		if (HapiProperties.getEmpiEnabled()) {
			register(EmpiSubmitterConfig.class);
			register(EmpiConsumerConfig.class);
			register(EmpiConfig.class);
		}

	}

}
