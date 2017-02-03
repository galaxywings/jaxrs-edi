from ftplib import FTP, FTP_TLS, all_errors

from rest_framework import status
from rest_framework.exceptions import APIException


class FtpError(APIException):
    status_code = status.HTTP_400_BAD_REQUEST

def exec_ftp_cmds(func, ftp_service, *args, **kwargs):
    try:
        do_exec_ftp_cmds(func, ftp_service, *args, **kwargs)
    except all_errors as e:
        raise FtpError(detail=str(e))

def do_exec_ftp_cmds(func, ftp_service, *args, **kwargs):
    FTP_CLASS = FTP_TLS if ftp_service.ssl else FTP
    with FTP_CLASS() as ftp:
        ftp.connect(ftp_service.host, ftp_service.port, 10)
        ftp.login(ftp_service.username, ftp_service.password)
        if ftp_service.ssl: 
            ftp.port_p()
        return func(ftp, ftp_service.path, *args, **kwargs)

def test_ftp_connection(ftp, path):
    # we do need anything, just login successfully means everythin
    pass

def create_ftp_transfer_folders(ftp, path):
    chdir(ftp, path)
    existing_folders = set(ftp.nlst())
    target_folders = set(('IN', 'OUT', 'PRE'))
    for folder in (target_folders - existing_folders):
        ftp.mkd(folder)

# refer to 
# http://stackoverflow.com/questions/10644608/create-missing-directories-in-ftplib-storbinary#answer-15561383
def chdir(ftp, directory):
    ch_dir_rec(ftp,directory.lstrip('/').split('/')) 

# Check if directory exists (in current location)
def directory_exists(ftp, directory):
    filelist = []
    ftp.retrlines('LIST',filelist.append)
    for f in filelist:
        if f.split()[-1] == directory and f.upper().startswith('D'):
            return True
    return False

def ch_dir_rec(ftp, descending_path_split):
    if len(descending_path_split) == 0:
        return

    next_level_directory = descending_path_split.pop(0)

    if not directory_exists(ftp,next_level_directory):
        ftp.mkd(next_level_directory)
    ftp.cwd(next_level_directory)
    ch_dir_rec(ftp,descending_path_split)
