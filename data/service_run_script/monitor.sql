use db_shiro;
#alter monitor name
drop table if exists hzdy_monitor;
create table hzdy_monitor(
    monitor_id int primary key auto_increment,
    start_date datetime,
    available_processors int,
    os varchar(100),
    host varchar(50),
    java_version varchar(50),
    jvm_version varchar(200),
    pid varchar(10),
    jvm_arguments varchar(350)
)default charset=utf8;

#add content
drop table if exists hzdy_wrong_warn_log;
create table hzdy_wrong_warn_log(
    wrong_warn_log_id int primary key auto_increment,

)default charset=utf8;
drop table if exists hzdy_email;
create table hzdy_email(
    email_id int primary key auto_increment,
    user_id int,
    receive_email varchar(30),
    is_delete int(1) default 0,
    is_subscribe int(1) default 0,
    constraint fk_hzdy_email_t_user foreigh key(user_id) references t_user(id)
)default charset=utf8;
drop table if exists hzdy_ip_handler;
create table hzdy_ip_handler(
    ip_handler_id int primary key auto_increment,
    ip_address varchar(20)
)default charset=utf8;
drop table if exists hzdy_system_logger;
create table hzdy_system_logger(
system_logger_id int primary key auto_increment ,
    log_name varchar(300),
    log_type varchar(300),
    log_content varchar(300),
    log_message varchar(300)
)default charset=utf8;
drop table if exists hzdy_web_logger;
create table hzdy_web_logger(
   web_logger_id  int primary key auto_increment,
   ip_address varchar(300),
   url varchar(300),
   HTTP_type varchar(300), 
   return_type varchar(300),
   browser_type varchar(300)
)default charset=utf8;
