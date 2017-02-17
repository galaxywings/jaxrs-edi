
from generic_relations.relations import GenericRelatedField
from rest_framework_bulk.serializers import BulkSerializerMixin, BulkListSerializer

from api.v1.service.serializers import FtpServiceSerializer, \
    GenericServiceSerializer, ProtocolServiceSerializer,\
    TemplateServiceSerializer
from common.serializers import DynamicFieldsModelSerializer
from service.models import Ftp, GenericService, Protocol, Template
from task.models import Step, Process


class StepSerializer(DynamicFieldsModelSerializer):
    class Meta:
        model = Step
        fields = '__all__'

class StepServiceSerializer(DynamicFieldsModelSerializer):
    content_object = GenericRelatedField({
        Ftp: FtpServiceSerializer(),
        GenericService: GenericServiceSerializer(),
        Protocol: ProtocolServiceSerializer(),
        Template: TemplateServiceSerializer(),
    })
    
    class Meta:
        model = Step
        fields = '__all__'

class ProcessSerializer(BulkSerializerMixin, DynamicFieldsModelSerializer):
    class Meta:
        model = Process
        list_serializer_class = BulkListSerializer
        fields = '__all__'
    