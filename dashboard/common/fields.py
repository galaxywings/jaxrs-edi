from django.db.models.fields import BinaryField


class XBinaryField(BinaryField):
    def get_placeholder(self, value, compiler, connection):
        # this might be a bug in django, quick fix for the time being
        # refer to https://code.djangoproject.com/ticket/27816
        return '%s'