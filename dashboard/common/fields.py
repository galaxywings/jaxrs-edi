from django.db.models.fields import BinaryField

try:
    import pymysql
except ImportError:
    __special_binary_prefix = False
else:
    __special_binary_prefix = True

class XBinaryField(BinaryField):
    def get_placeholder(self, value, compiler, connection):
        # this might be a bug in django, quick fix for the time being
        # refer to https://github.com/PyMySQL/PyMySQL/issues/549
        if __special_binary_prefix:
            return '%s'
        else:
            return super().get_placeholder(value, compiler, connection)