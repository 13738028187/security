# coding=utf-8
import telnetlib
import sys

ip = sys.argv[1]
username = sys.argv[2]
password = sys.argv[3]
type = sys.argv[4]
ips = sys.argv[5]
ipStatus = sys.argv[6]

tn = telnetlib.Telnet(ip)

tn.read_until("login: ")
tn.write(username + "\n")
if password:
    tn.read_until("Password: ")
    tn.write(password + "\n")

# 拦截IP
if ipStatus == '0':
	ips = tuple(ips.split(";"))
	for ip in ips:
		tn.write(
            "echo '" + password + "' | sudo -S iptables -I INPUT -s " + ip + " -j DROP\n")

# 放行IP
if ipStatus == '1':
    ips = tuple(ips.split(";"))
    for ip in ips:
    	tn.write(
            "echo '" + password + "' | sudo -S iptables -I INPUT -s " + ip + " -j ACCEPT\n")

# CentOS下的命令
if type == 'CentOS':
    tn.write("echo '" + password + "' | sudo -S service iptables save\n")
    tn.write("echo '" + password + "' | sudo -S service iptables restart\n")

# Ubuntu下的命令
if type == 'Ubuntu':
    tn.write("echo '" + password + "' | sudo -S iptables-save > /etc/iptables.up.rules\n")
    tn.write("echo '" + password + "' | sudo -S ufw reload\n")

tn.write("exit\n")

print tn.read_all()
