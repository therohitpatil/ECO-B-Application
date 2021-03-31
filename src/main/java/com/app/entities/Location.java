package com.app.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {

	@Id
	private Integer locationId;
	
	@Column(length = 20)
	private String locationName;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "bicycleLocation")
	private List<Bicycle> bicycles = new ArrayList<>();

	public Location() {
		super();
		System.out.println("in locations default cnstr");
	}

	public Location(Integer locationId, String locationName, List<Bicycle> bicycles) {
		super();
		System.out.println("in locations parametarized cnstr");
		this.locationId = locationId;
		this.locationName = locationName;
		this.bicycles = bicycles;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public List<Bicycle> getBicycles() {
		return bicycles;
	}

	public void setBicycles(List<Bicycle> bicycles) {
		this.bicycles = bicycles;
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", locationName=" + locationName + ", bicycles=" + bicycles + "]";
	}
	
	
	
}
