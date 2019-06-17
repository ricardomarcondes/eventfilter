package com.event.model;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.event.util.ZonedDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonPropertyOrder({ "client-address", "client-guid","request-time","service-guid","retries-request","packets-requested","packets-serviced","max-hole-size"})
public class Event {

    @JsonProperty("client-address")
    private InetAddress clientAddress;

    @JsonProperty("client-guid")
    private UUID clientGuid;

    @JsonProperty("request-time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    @JsonDeserialize(using=ZonedDateTimeDeserializer.class)
    private ZonedDateTime requestTime;
    
    @JsonProperty("service-guid")
    private UUID serviceGuid;

    @JsonProperty("retries-request")
    private int retriesRequest;

    @JsonProperty("packets-requested")
    private int packetsRequested;

    @JsonProperty("packets-serviced")
    private int packetsServiced;

    @JsonProperty("max-hole-size")
    private int maxHoleSize;

    public Event() {
    }

    public Event(InetAddress clientAddress, UUID clientGuid, ZonedDateTime requestTime, UUID serviceGuid, int retriesRequest, int packetsRequested, int packetsServiced, int maxHoleSize) {
        this.clientAddress = clientAddress;
        this.clientGuid = clientGuid;
        this.requestTime = requestTime;
        this.serviceGuid = serviceGuid;
        this.retriesRequest = retriesRequest;
        this.packetsRequested = packetsRequested;
        this.packetsServiced = packetsServiced;
        this.maxHoleSize = maxHoleSize;
    }

    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(InetAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public UUID getClientGuid() {
        return clientGuid;
    }

    public void setClientGuid(UUID clientGuid) {
        this.clientGuid = clientGuid;
    }

    public ZonedDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(ZonedDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public UUID getServiceGuid() {
        return serviceGuid;
    }

    public void setServiceGuid(UUID serviceGuid) {
        this.serviceGuid = serviceGuid;
    }

    public int getRetriesRequest() {
        return retriesRequest;
    }

    public void setRetriesRequest(int retriesRequest) {
        this.retriesRequest = retriesRequest;
    }

    public int getPacketsRequested() {
        return packetsRequested;
    }

    public void setPacketsRequested(int packetsRequested) {
        this.packetsRequested = packetsRequested;
    }

    public int getPacketsServiced() {
        return packetsServiced;
    }

    public void setPacketsServiced(int packetsServiced) {
        this.packetsServiced = packetsServiced;
    }

    public int getMaxHoleSize() {
        return maxHoleSize;
    }

    public void setMaxHoleSize(int maxHoleSize) {
        this.maxHoleSize = maxHoleSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (retriesRequest != event.retriesRequest) return false;
        if (packetsRequested != event.packetsRequested) return false;
        if (packetsServiced != event.packetsServiced) return false;
        if (maxHoleSize != event.maxHoleSize) return false;
        if (clientAddress != null ? !clientAddress.equals(event.clientAddress) : event.clientAddress != null)
            return false;
        if (clientGuid != null ? !clientGuid.equals(event.clientGuid) : event.clientGuid != null) return false;
        if (requestTime != null ? !requestTime.equals(event.requestTime) : event.requestTime != null) return false;
        return serviceGuid != null ? serviceGuid.equals(event.serviceGuid) : event.serviceGuid == null;
    }

    @Override
    public int hashCode() {
        int result = clientAddress != null ? clientAddress.hashCode() : 0;
        result = 31 * result + (clientGuid != null ? clientGuid.hashCode() : 0);
        result = 31 * result + (requestTime != null ? requestTime.hashCode() : 0);
        result = 31 * result + (serviceGuid != null ? serviceGuid.hashCode() : 0);
        result = 31 * result + retriesRequest;
        result = 31 * result + packetsRequested;
        result = 31 * result + packetsServiced;
        result = 31 * result + maxHoleSize;
        return result;
    }

	@Override
	public String toString() {
		return "Event [clientAddress=" + clientAddress + ", clientGuid=" + clientGuid + ", requestTime=" + requestTime
				+ ", serviceGuid=" + serviceGuid + ", retriesRequest=" + retriesRequest + ", packetsRequested="
				+ packetsRequested + ", packetsServiced=" + packetsServiced + ", maxHoleSize=" + maxHoleSize + "]";
	}
    
    
}
