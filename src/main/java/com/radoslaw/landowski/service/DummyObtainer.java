package com.radoslaw.landowski.service;

import com.radoslaw.landowski.model.HolidayInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Dummy service that generates dummy HolidayInfo.
 */
@Service("DummyObtainer")
@Qualifier("DummyObtainer")
public class DummyObtainer implements HolidayInfoObtainer {
    @Override
    public HolidayInfo get(String firstCountryName, String secondCountryName, Date date) {
        return new HolidayInfo(new Date(), "PL", "EU");
    }
}
