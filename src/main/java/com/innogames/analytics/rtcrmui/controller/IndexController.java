package com.innogames.analytics.rtcrmui.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innogames.analytics.rtcrmui.entity.Campaign;
import com.innogames.analytics.rtcrmui.entity.TrackingEvent;
import com.innogames.analytics.rtcrmui.kafka.StringProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private StringProducer stringProducer;

	@Autowired
	private SimpMessagingTemplate template;

	@GetMapping("/")
	public String home(final Model model) {
		model.addAttribute("module", "index");
		return "index";
	}

	@PostMapping("/event")
	public RedirectView sendEvent(@RequestBody final String eventJson) {
		try {
			final TrackingEvent trackingEvent = objectMapper.readValue(eventJson, TrackingEvent.class);
			stringProducer.send("events-valid", objectMapper.writeValueAsString(trackingEvent));
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		return new RedirectView("/", false);
	}

	@PostMapping("/campaign")
	public RedirectView sendCampaign(@RequestBody final String campaignJson) {
		try {
			final Campaign campaign = objectMapper.readValue(campaignJson, Campaign.class);
			stringProducer.send("crm-campaigns", objectMapper.writeValueAsString(campaign));
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		return new RedirectView("/", false);
	}

	@KafkaListener(topics = "crm-triggers")
	public void listenGroupFoo(final String message) {
		template.convertAndSend("/topic/triggers", message);
	}

}
