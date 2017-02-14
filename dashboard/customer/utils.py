
from memoize import memoize

from customer.models import Customer

@memoize()
def get_id_customer_dict():
    '''
        prepare a dict of <customer_id, customer>, 
    '''
    result = {
        c.id: c for c in Customer.objects.all()
    }
    return result