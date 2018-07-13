use db_shiro;
drop table if exists hzdy_email;
create table hzdy_email(
    email_id int primary key auto_increment,
    user_id int,
    receive_email varchar(30),
    is_delete int(1) default 0,
    is_subscribe int(1) default 0,
    constraint fk_hzdy_email_t_user foreign key (user_id) references t_user(id)
)default charset=utf8;
drop table if exists hzdy_device_info;
create table hzdy_device_info(
    device_info_id int primary key auto_increment,
    name varchar(50),
    ip_address varchar(50),
    type int,
    net_location varchar(50),
    protocol varchar(50),
    event int,
    status int,
    resource int
)default charset=utf8;

