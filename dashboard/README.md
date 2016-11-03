# Wujie Dashboard

## Pre


```sh
# setup the db
mysql -u root

CREATE DATABASE `wujie` CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL ON `wujie`.* TO `wujie`@`localhost` IDENTIFIED BY 'wujie';
FLUSH PRIVILEGES;

# install python libs
pip install -r requirements.txt

# run it
python manage.py runserver 0.0.0.0:8090

# open browser to test
open http://0.0.0.0:8090/

```