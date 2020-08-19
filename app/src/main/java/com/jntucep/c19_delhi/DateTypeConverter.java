package com.jntucep.c19_delhi;

import java.util.Date;

import androidx.room.TypeConverter;


public class DateTypeConverter {

    @TypeConverter
    public Date LongtoDateConverter(Long date){
        return new Date(date);
    }

    @TypeConverter
    public Long DatetoLongConverter(Date date){
        return date.getTime();
    }

}
