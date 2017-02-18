from collections import OrderedDict

from rest_framework.decorators import detail_route
from rest_framework.response import Response
from reversion.models import Version

class HistoricalMixin(object):
    
    @staticmethod
    def historical_file_records(instance):
        '''
            return a list of [{
                'filename': 'xxx',
                'date_created': UTC,
                'username': 'xxx'
                'user_id': xxx
            },
            ...
            ]
        '''
        version_qs = Version.objects.get_for_object(instance) # @UndefinedVariable
        version_qs = version_qs.order_by('-revision__date_created')\
                    .select_related('revision', 'revision__user')
        
        filename_record_dict = OrderedDict()
        for version in version_qs:
            filename = version.field_dict["filename"]
            date_created = version.revision.date_created
            
            if filename in filename_record_dict:
                record = filename_record_dict.get(filename)
                if record['date_created'] < date_created:
                    # since not all the changes are filename relative
                    record['date_created'] = date_created
            else:
                filename_record_dict[filename] = {
                    'filename': filename,
                    'date_created': date_created,
                    'user_id': version.revision.user.id,
                    'username': version.revision.user.username,
                }
        return tuple(filename_record_dict.values())

    @detail_route(
        methods=('get', ),
        url_path='historical-files'
    )
    def historical_files(self, request, pk=None):
        instance = self.get_object()
        records = self.historical_file_records(instance)
        
        page = self.paginate_queryset(records)
        if page is not None:
            return self.get_paginated_response(page)

        return Response(records)