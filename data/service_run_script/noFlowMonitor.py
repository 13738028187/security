# coding=utf-8
import telnetlib
import sys

ip = sys.argv[1]
username = sys.argv[2]
password = sys.argv[3]

tn = telnetlib.Telnet(ip)

tn.read_until("login: ")
tn.write(username + "\n")
if password:
    tn.read_until("Password: ")
    tn.write(password + "\n")

tn.write("echo '" + password + "' | sudo -S iptables -nvx -L\n")

tn.write("exit\n")

print tn.read_all()