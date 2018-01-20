package com.coding.trade.service;

import com.coding.trade.type.CcyPair;

import java.time.LocalDate;

public interface HolidayService {

    boolean isHoliday(CcyPair ccyPair, LocalDate date);

    boolean isWeekEnd(CcyPair ccyPair, LocalDate date);
}
