from collections import OrderedDict

from rest_framework.decorators import detail_route
from rest_framework.response import Response
from reversion.models import Version

class HistoricalMixin(object):
    
    @staticmethod
    def historical_file_records(instance):
        '''
            return a list of [{
                'version_id': xxx,
                'filename': 'xxx',
                'date_created': UTC,
                'user_id': xxx,
                'username': 'xxx',
            },
            ...
            ]
        '''
        version_qs = Version.objects.get_for_object(instance) # @UndefinedVariable
        version_qs = version_qs.order_by('-revision__date_created')\
                    .select_related('revision', 'revision__user')
        
        filename_records_dict = OrderedDict()
        for version in version_qs:
            filename = version.field_dict["filename"]
            date_created = version.revision.date_created
            if filename not in filename_records_dict:
                filename_records_dict[filename] = []
            records = filename_records_dict.get(filename)
            records.append({
                'version_id': version.id,
                'filename': filename,
                'date_created': date_created,
                'user_id': version.revision.user.id,
                'username': version.revision.user.username,
            })
            
        return filename_records_dict

    @detail_route(
        methods=('get', ),
        url_path='historical-files'
    )
    def historical_files(self, request, pk=None):
        instance = self.get_object()
        filename_records_dict = self.historical_file_records(instance)
        records = [
            records[0]
            for records in filename_records_dict.values()
        ]
        page = self.paginate_queryset(records)
        if page is not None:
            return self.get_paginated_response(page)

        return Response(records)
    
    @detail_route(
        methods=('get', ),
        url_path='historical-files/verbose'
    )
    def historical_files_verbose(self, request, pk=None):
        instance = self.get_object()
        filename_records_dict = self.historical_file_records(instance)
        
        page = self.paginate_queryset(list(filename_records_dict.items()))
        if page is not None:
            return self.get_paginated_response(OrderedDict(page))

        return Response(filename_records_dict)