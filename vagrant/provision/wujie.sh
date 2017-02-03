cd /vagrant/redis-connector
mvn install

cd /vagrant/mule
./gradlew deployLocally
