package com.bra.modules.reserve.web.form;

import com.bra.modules.reserve.entity.ReserveVenue;
import com.bra.modules.sys.entity.User;

import java.util.Date;

/**
 * Created by xiaobin on 16/1/28.
 */
public class SaleVenueLog {

    private User user;

    private Date startDate;

    private Date endDate;

    private ReserveVenue venue;

    private String dsf;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

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

    public String getDsf() {
        return dsf;
    }

    public void setDsf(String dsf) {
        this.dsf = dsf;
    }
}
