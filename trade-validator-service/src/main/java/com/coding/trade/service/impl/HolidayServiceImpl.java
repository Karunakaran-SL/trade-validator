package com.coding.trade.service.impl;

import com.coding.trade.service.HolidayService;
import com.coding.trade.type.CcyPair;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;

@Service
public class HolidayServiceImpl implements HolidayService{

    //TODO static map with currency and map date
    private LocalDate[] holidayList = new LocalDate[]{LocalDate.parse("2016-08-18")};

    //TODO Assumption is for Europe. Based on currenty this value needs to change.
    private DayOfWeek[] weekEnd = new DayOfWeek[]{DayOfWeek.SATURDAY,DayOfWeek.SUNDAY};

    @Override
    public boolean isHoliday(CcyPair ccyPair, LocalDate date) {
        return Arrays.asList(holidayList).contains(date);
    }

    @Override
    public boolean isWeekEnd(CcyPair ccyPair, LocalDate date) {
        return Arrays.asList(weekEnd).contains(date.getDayOfWeek());
    }
}
