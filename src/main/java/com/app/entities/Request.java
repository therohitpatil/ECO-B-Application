package com.app.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "requests")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer requestId;

	@NotNull
	private String userId;

	@NotNull
	@Column(length = 30)
	private String startingPoint;

	@NotNull
	@Column(length = 30)
	private String endingPoint;

	private Integer bicycleAlotted;

	@Column(nullable = true)
	private boolean requestStatus;// accepted/rejected

	private boolean rideStatus;// completed/not completed

	private String requestTime;// time when request is placed

	private String bookedTime;// time when request is accepted

	private String finishTime;// time when ride ended

	public Request() {
		super();
		System.out.println("in request default cnstr");
	}

	public Request(Integer requestId, @NotNull String userId, @NotNull String startingPoint,
			@NotNull String endingPoint, Integer bicycleAlotted, boolean requestStatus, boolean rideStatus,
			String requestTime, String bookedTime, String finishTime) {
		super();
		System.out.println("in request parametarized cnstr");
		this.requestId = requestId;
		this.userId = userId;
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
		this.bicycleAlotted = bicycleAlotted;
		this.requestStatus = requestStatus;
		this.rideStatus = rideStatus;
		this.requestTime = requestTime;
		this.bookedTime = bookedTime;
		this.finishTime = finishTime;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartingPoint() {
		return startingPoint;
	}

	public void setStartingPoint(String startingPoint) {
		this.startingPoint = startingPoint;
	}

	public String getEndingPoint() {
		return endingPoint;
	}

	public void setEndingPoint(String endingPoint) {
		this.endingPoint = endingPoint;
	}

	public Integer getBicycleAlotted() {
		return bicycleAlotted;
	}

	public void setBicycleAlotted(Integer bicycleAlotted) {
		this.bicycleAlotted = bicycleAlotted;
	}

	public boolean isRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(boolean requestStatus) {
		this.requestStatus = requestStatus;
	}

	public boolean isRideStatus() {
		return rideStatus;
	}

	public void setRideStatus(boolean rideStatus) {
		this.rideStatus = rideStatus;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(String bookedTime) {
		this.bookedTime = bookedTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "Request [requestId=" + requestId + ", userId=" + userId + ", startingPoint=" + startingPoint
				+ ", endingPoint=" + endingPoint + ", bicycleAlotted=" + bicycleAlotted + ", requestStatus="
				+ requestStatus + ", rideStatus=" + rideStatus + ", requestTime=" + requestTime + ", bookedTime="
				+ bookedTime + ", finishTime=" + finishTime + "]";
	}

	
	
}
