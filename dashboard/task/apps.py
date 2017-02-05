from django.apps import AppConfig

# This is the first tricky part. 
# There mere existence of this file also does nothing. 
# We’ll point to this in the next step. 
# This imports polls.signals in the ready method. 
# By extending the AppConfig class from Django, 
# we’re telling Django to import polls.signals when the Polls app is loaded.
# refer to http://www.koopman.me/2015/01/django-signals-example/
class TaskConfig(AppConfig):
	name = 'task'

	def ready(self):
		
		import task.signals  # @UnusedImport