from django.contrib.auth import get_user_model
from rest_framework import serializers
from rest_framework.fields import SerializerMethodField


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = get_user_model()
        exclude = ('password', )

class UserGroupSerializer(serializers.ModelSerializer):
    groups = SerializerMethodField()
    
    class Meta:
        model = get_user_model()
        exclude = ('password', )
        
    def get_groups(self, obj):
        # empty list for the time being
        return []

