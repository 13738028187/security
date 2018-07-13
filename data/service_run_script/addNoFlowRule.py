# coding=utf-8
import telnetlib
import sys

ip = sys.argv[1]
username = sys.argv[2]
password = sys.argv[3]
type = sys.argv[4]
originIP = sys.argv[5]
goalIP = sys.argv[6]
goalADSL = sys.argv[7]
protocol = sys.argv[8]

tn = telnetlib.Telnet(ip)

tn.read_until("login: ")
tn.write(username + "\n")
if password:
    tn.read_until("Password: ")
    tn.write(password + "\n")

tn.write("echo '" + password + "' | sudo -S iptables -I OUTPUT -p" + protocol + " --dport " + goalADSL + " -s " + originIP + " -d " + goalIP + "\n")

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