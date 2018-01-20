package com.coding.trade.service.impl;

import com.coding.trade.service.HolidayService;
import com.coding.trade.type.CcyPair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class HolidayServiceImpl implements HolidayService{

    //TODO static map with currency and map date
    LocalDate[] holidayList = new LocalDate[]{LocalDate.parse("2016-08-18")};

    @Override
    public boolean isHoliday(CcyPair ccyPair, LocalDate date) {
        return Arrays.asList(holidayList).contains(date);
    }
}
