#!/usr/bin/env python3
# -*- coding: utf-8 -*-

'archive logs for EDI transfer'

__author__ = 'Rocky Wang'

import os
import shutil
from datetime import datetime, timedelta

os.chdir('C:\\archives_test')
current_dir = os.path.abspath('.')
now_day = datetime.now()
has_file = True
archive_day = now_day - timedelta(hours=1)
n = 1
while n < 1000:
    archive_str = datetime.strftime(archive_day, '%Y - %m - %d - %H')
    file_name = 'systemout.log .  ' + archive_str
    if os.path.exists(os.path.join(current_dir, file_name)):
        archive_dir = datetime.strftime(archive_day, '%Y%m')
        if not os.path.exists(os.path.join(current_dir, archive_dir)):
            os.mkdir(archive_dir)
        shutil.move(os.path.join(current_dir, file_name), os.path.join(current_dir, archive_dir))
        print(file_name + ' is archived')
    else:
        # has_file = False
        print(file_name + ' doesn\'t exit')
    archive_day = archive_day - timedelta(hours=1)
    n += 1
print('Archiving Ends')
