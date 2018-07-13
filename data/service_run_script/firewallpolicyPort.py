# coding=utf-8
import telnetlib
import sys

ip = sys.argv[1]
username = sys.argv[2]
password = sys.argv[3]
type = sys.argv[4]
ips = sys.argv[5]
ports = sys.argv[6]
portStatus = sys.argv[7]

tn = telnetlib.Telnet(ip)

tn.read_until("login: ")
tn.write(username + "\n")
if password:
    tn.read_until("Password: ")
    tn.write(password + "\n")

# 关闭端口
if portStatus == '0':
    ports = tuple(ports.split(";"))
    if ips != 'null':
        ips = tuple(ips.split(";"))
        for ip in ips:
            for port in ports:
                tn.write(
                    "echo '" + password + "' | sudo -S iptables -I INPUT -s " + ip + " -p tcp --dport " + port + " -j DROP\n")
    else:
        for port in ports:
            tn.write(
                "echo '" + password + "' | sudo -S iptables -I INPUT -p tcp --dport " + port + " -j DROP\n")

# 开放端口
if portStatus == '1':
    ports = tuple(ports.split(";"))
    if ips != 'null':
        ips = tuple(ips.split(";"))
        for ip in ips:
            for port in ports:
                tn.write(
                    "echo '" + password + "' | sudo -S iptables -I INPUT -s " + ip + " -p tcp --dport " + port + " -j ACCEPT\n")
    else:
        for port in ports:
            tn.write(
                "echo '" + password + "' | sudo -S iptables -I INPUT -p tcp --dport " + port + " -j ACCEPT\n")

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
