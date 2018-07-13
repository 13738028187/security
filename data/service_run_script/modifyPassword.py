import telnetlib
import sys

tn = telnetlib.Telnet(sys.argv[1])

tn.read_until("login: ")
tn.write(sys.argv[2] + "\n")
if sys.argv[3]:
    tn.read_until("Password: ")
    tn.write(sys.argv[3] + "\n")

tn.write("(echo '" + sys.argv[3] + "';sleep 1;echo '" + sys.argv[4] + "';sleep 1;echo '" + sys.argv[4] + "') | passwd > /dev/null\n")

tn.write("exit\n")

print tn.read_all()