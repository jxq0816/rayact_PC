package com.bra.modules.reserve.entity.form;

import com.bra.common.persistence.SaasEntity;
import com.bra.modules.reserve.entity.ReserveProject;
import com.bra.modules.reserve.entity.ReserveVenue;

import java.util.Date;

/**
 * Created by DDT on 2016/4/13.
 */
public class SearchForm extends SaasEntity<SearchForm> {

    public SearchForm() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    private Date startDate;
    private Date endDate;

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    private String venueId;
    private ReserveVenue venue;
    private ReserveProject project;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ReserveVenue getVenue() {
        return venue;
    }

    public void setVenue(ReserveVenue venue) {
        this.venue = venue;
    }

    public ReserveProject getProject() {
        return project;
    }

    public void setProject(ReserveProject project) {
        this.project = project;
    }
}
