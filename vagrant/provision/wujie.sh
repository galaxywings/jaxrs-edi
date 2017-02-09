cd /vagrant/redis-connector
mvn install

cd /vagrant/mule
./gradlew deployLocally

/home/vagrant/mule-standalone-3.8.1/bin/mule start
