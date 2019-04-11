package com.radoslaw.landowski.service.obtainers;

import com.radoslaw.landowski.exceptions.HolidayObtainingException;
import com.radoslaw.landowski.model.HolidayInfo;

import java.time.LocalDate;

public interface HolidayInfoObtainer {
    HolidayInfo get(String firstCountryCode, String secondCountryCode, LocalDate date) throws HolidayObtainingException;
}
