from django.contrib.auth.models import User
from django.db import models


class Account(models.Model):
    username = models.CharField(max_length=150, null=False)
    password = models.CharField(max_length=150, null=False)
    application_name = models.CharField(max_length=150, null=False)
    user_id = models.ForeignKey(User, on_delete=models.CASCADE, null=False)
# Create your models here.
