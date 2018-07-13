package com.hzdy.mongo.utils;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
public class MongoTimestampConverter implements Converter<Date,Timestamp> {
    @Override
    public Timestamp convert(Date date) {
        if(null != date){
            return new Timestamp(date.getTime());
        }
        return null;
    }
}
