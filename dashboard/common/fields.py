from django.db.models.fields import BinaryField

__special_binary_prefix = True
try:
    import pymysql
except ImportError:
    __special_binary_prefix = False

def need_special_binary_prefix():
    return __special_binary_prefix

class XBinaryField(BinaryField):
    
    def get_placeholder(self, value, compiler, connection):
        # this might be a bug in django, quick fix for the time being
        # refer to https://github.com/PyMySQL/PyMySQL/issues/549
        if need_special_binary_prefix():
            return '%s'
        else:
            return super().get_placeholder(value, compiler, connection)