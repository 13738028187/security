cd /opt/apache-flume-1.7.0-bin/bin
sudo ./flume-ng agent -c conf -f ../conf/flume-conf.properties  -n a1   -Dflume.root.logger=INFO,console

