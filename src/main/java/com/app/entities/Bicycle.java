package com.app.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "bicycles")
public class Bicycle {

	@Id
	private Integer bicycleId;

	@ManyToOne
	@JsonBackReference
	private Location location;

	private boolean bicycleStatus;//booked/not booked
	
	@ManyToOne
	private Location bicycleLocation; 

	public Bicycle() {
		super();
		System.out.println("in bicycles default cnstr");
	}

	public Bicycle(Integer bicycleId, Location location,boolean bicycleStatus) {
		super();
		System.out.println("in bicycles parametarized cnstr");
		this.bicycleId = bicycleId;
		this.location = location;
		this.bicycleStatus = bicycleStatus;
	}

	public Integer getBicycleId() {
		return bicycleId;
	}

	public void setBicycleId(Integer bicycleId) {
		this.bicycleId = bicycleId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isBicycleStatus() {
		return bicycleStatus;
	}

	public void setBicycleStatus(boolean bicycleStatus) {
		this.bicycleStatus = bicycleStatus;
	}
		
	public Location getBicycleLocation() {
		return bicycleLocation;
	}

	public void setBicycleLocation(Location bicycleLocation) {
		this.bicycleLocation = bicycleLocation;
	}

	@Override
	public String toString() {
		return "Bicycle [bicycleId=" + bicycleId + ", location=" + location + ", bicycleStatus="
				+ bicycleStatus + ", bicycleLocation=" + bicycleLocation + "]";
	}
	
}
