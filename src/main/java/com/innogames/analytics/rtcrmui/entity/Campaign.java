package com.innogames.analytics.rtcrmui.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Campaign {

	private int campaignId;
	private boolean enabled;
	private String game;
	private String eventName;
	private String startDate;
	private String endDate;
	private String filter;

	public Campaign() {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(final int campaignId) {
		this.campaignId = campaignId;
	}

	public String getGame() {
		return game;
	}

	public void setGame(final String game) {
		this.game = game;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(final String eventName) {
		this.eventName = eventName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(final String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(final String endDate) {
		this.endDate = endDate;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(final String filter) {
		this.filter = filter;
	}

}
