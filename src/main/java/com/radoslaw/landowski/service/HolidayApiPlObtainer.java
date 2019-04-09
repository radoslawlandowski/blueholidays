package com.radoslaw.landowski.service;

import com.radoslaw.landowski.model.HolidayInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service that uses holidayapi.pl as holiday info source.
 */
@Service("HolidayApiPlObtainer")
@Qualifier("HolidayApiPlObtainer")
public class HolidayApiPlObtainer implements HolidayInfoObtainer {
    @Override
    public HolidayInfo get(String firstCountryName, String secondCountryName, Date date) {
        return new HolidayInfo(new Date(), "HOLIDAYAPI", "EU");
    }
}
