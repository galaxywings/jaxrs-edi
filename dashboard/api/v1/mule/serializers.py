
from generic_relations.relations import GenericRelatedField
from rest_framework.fields import SerializerMethodField
from rest_framework.relations import StringRelatedField
from rest_framework_bulk.serializers import BulkSerializerMixin, BulkListSerializer

from api.v1.service.serializers import FtpServiceSerializer, \
    GenericServiceSerializer, ProtocolServiceSerializer, TemplateServiceSerializer
from common.serializers import DynamicFieldsModelSerializer
from service.models import Ftp, GenericService, Protocol, Template
from task.models import Step, Process
from task.utils import get_contenttype_object_service_dict

class ServiceSerialzerMixin(object):
    def to_representation(self, instance):
        result = super().to_representation(instance)
        
        result.pop('id', None)
        result.pop('extra_schema', None)
        
        extra_params = result.pop('extra_params', None)
        
        if extra_params:
            for k, v in extra_params.items():
                # we keep property names which share the same name as `k` intact 
                result.setdefault(k, v)
        return result

class MuleFtpServiceSerializer(ServiceSerialzerMixin, FtpServiceSerializer):
    pass

class MuleGenericServiceSerializer(ServiceSerialzerMixin, GenericServiceSerializer):
    pass

class MuleProtocolServiceSerializer(ServiceSerialzerMixin, ProtocolServiceSerializer):
    pass

class MuleTemplateServiceSerializer(ServiceSerialzerMixin, TemplateServiceSerializer):
    pass

class StepSerializer(DynamicFieldsModelSerializer):
    service_code = SerializerMethodField()
    
    params = GenericRelatedField({
        Ftp: MuleFtpServiceSerializer(),
        GenericService: MuleGenericServiceSerializer(),
        Protocol: MuleProtocolServiceSerializer(),
        Template: MuleTemplateServiceSerializer(),

    }, source='content_object')
    
    class Meta:
        model = Step
        fields = ('id', 'service_code', 'params', 'seq', )
    
    def get_service_code(self, instance):
        contenttype_object_service_dict = get_contenttype_object_service_dict()
        object_service_dict = contenttype_object_service_dict.get(instance.content_type_id, {})
        service = object_service_dict.get(instance.object_id, None)
        return (service and service.extra_schema and service.extra_schema.code) or ''

class ProcessSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    steps = SerializerMethodField()
    sequence = SerializerMethodField()

    class Meta:
        model = Process
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    
    def get_steps(self, instance):
        # in order to ensure the order of steps inside
        # refer to https://groups.google.com/forum/#!topic/django-rest-framework/L9aXnwS4AQw
        step_qs = Step.objects.filter(process=instance).order_by('seq')
        s = StepSerializer(step_qs, many=True, read_only=True)
        return s.data

    def get_sequence(self, instance):
        return ','.join([step.get('service_code') for step in self.get_steps(instance)])
