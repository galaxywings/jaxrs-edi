# Yum
sudo yum install epel-release
sudo yum update -y

# JDK
sudo yum install -y java-1.8.0-openjdk.x86_64 wget jemalloc socat telnet

# Mule
wget https://repository-master.mulesoft.org/nexus/content/repositories/releases/org/mule/distributions/mule-standalone/3.8.1/mule-standalone-3.8.1.tar.gz
tar zxf mule-standalone-3.8.1.tar.gz

# MySQL
sudo rpm -ivh https://dev.mysql.com/get/mysql57-community-release-el7-9.noarch.rpm
sudo yum install -y mysql-server

# RabbitMQ
yum install -y erlang
wget https://www.rabbitmq.com/releases/rabbitmq-server/v3.6.6/rabbitmq-server-3.6.6-1.el7.noarch.rpm
sudo rpm -ivh rabbitmq-server-3.6.6-1.el7.noarch.rpm
sudo systemctl start rabbitmq-server
sudo systemctl enable rabbitmq-server
sudo rabbitmq-plugins enable rabbitmq_management

# Redis
sudo rpm -ivh http://195.220.108.108/linux/remi/enterprise/7/remi/x86_64/redis-3.2.6-1.el7.remi.x86_64.rpm
sudo systemctl enable redis.service
sudo systemctl start redis.service
