drop table if exists hzdy_web_logger;
drop table if exists hzdy_system_logger;

create table hzdy_web_logger(
     web_logger_id int primary key auto_increment,
	 ip_address varchar(300),
	 url varchar(300),
	 HTTP_type varchar(300),
	 return_type varchar(20),
	 browser_type varchar(300)
)default charset=utf8;
create table hzdy_system_logger(
     system_logger_id int primary key auto_increment,
	 log_name varchar(100),
	 log_type varchar(200),
	 log_content varchar(200),
	 log_message varchar(200)
)default charset=utf8;