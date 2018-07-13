package com.hzdy.logger.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
* 
* what
* 
* @author kirohuji
* @version 0.1
*/
@Document
public class Syslog {
	private int severity;
	private String severity_label;
	private int priority;
    private String ip;
    private String logsource;
    private String type;
    private String timestamp;
    private int facility;
    private String facility_label;
    private String program;
    private String message;
}
