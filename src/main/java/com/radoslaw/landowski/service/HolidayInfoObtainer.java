package com.radoslaw.landowski.service;

import com.radoslaw.landowski.model.HolidayInfo;

import java.util.Date;

public interface HolidayInfoObtainer {
    HolidayInfo get(String firstCountryName, String secondCountryName, Date date);
}
