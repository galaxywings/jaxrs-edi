#!/usr/bin/env python
"""
对于 csv 格式的要求
    1. 列名必须包含英文, 作为变量名字
    2. RECORD ID 行会被忽略
    3. 格式要求
        3.1 seg code, , seg mame
        3.2 number, field name, , date type (lengh), , M/C, ,,
    4. 以记录00/01/../99开头的会作为 segment
    5. 以1/2/3/4开头的行作为列名
    6. 以空列开头的行会被忽略

需要手工处理的情况:
    1. 格式不对, 导致 xmltag=""为空
        1.1 seg name/列名/数据类型的位置不对
        1.2 列名只包含中文
"""
import csv
import re
import sys

if len(sys.argv) == 1:
    print("Usage: " + sys.argv[0] + " path_to_csv_file")
    exit(0)

FILE_PATH = sys.argv[1]
# FILE_PATH = 'edi_iftmbf.csv'

IDX_HEAD_NAME = 2

chinese_word = r'[\u4e00-\u9fff]+'
char_to_be_removed = r'[,&()\./]'
header = r'记录(\d+)'
max_length = r'\((\d+)\)'
date_format = r'YYMMDD'

data_types = {
    'BigDecimal': r'V',
    'String': r'X',
    'Integer': r'9',
}

date_length_format = {
    '14': 'YYYYMMDDHHMMSS',
    '12': 'YYYYMMDDHHMM',
    '8': 'YYYYMMDD',
}

seg_format = '<medi:segment segcode="{}" xmltag="{}">'
filed_format = '<medi:field xmltag="{}" dataType="{}" required={} maxLength={} {} />'

seg_trailing = '</medi:segment>'

def process_name(col):
    new_col = re.sub(r'\s+', ' ', re.sub(char_to_be_removed, '', re.sub(chinese_word, '', col)))
    return  new_col.strip().lower().replace(' ', '-')

def is_seg_header(col):
    match = re.search(header,col)
    if match:
        return match.group(1)
    else:
        return None

def m_or_c(flag):
    if flag in ('M','m'):
        return '"true"'
    else:
        return '"false"'

def get_length(col):
    match = re.search(max_length,col)
    if match:
        return match.group(1)

def is_date_format(col):
    return re.search(date_format, col)

def get_data_type(col):
    for key, value in data_types.items():
        if re.search(value, col):
            return key


first_seg_code = True

result_xml = []

with open(FILE_PATH, newline='') as f:
    reader = csv.reader(f)
    for row in reader:
        if row[0] not in ('','序号') and not re.match('RECORD ID', row[1]):
            seg_code = is_seg_header(row[0])
            if seg_code:
                if not first_seg_code:
                    result_xml.append(seg_trailing)
                result_xml.append(seg_format.format(seg_code,process_name( row[IDX_HEAD_NAME] )))
                if first_seg_code:
                    first_seg_code = False

            else:
                # if it's date format
                # print(row[3])
                l = get_length(row[3])
                name = process_name(row[1])
                if is_date_format(row[4]):
                    result_xml.append(filed_format.format(name, 'Date', m_or_c(row[5]), l, 'dataTypeParameters="format={}"'.format(date_length_format.get(l))))
                else:
                    t = get_data_type(row[3])
                    result_xml.append(filed_format.format(name, t, m_or_c(row[5]), l, ''))

result_xml.append(seg_trailing)

print('\n'.join(result_xml))
