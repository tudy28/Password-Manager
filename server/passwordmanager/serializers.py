from rest_framework import serializers

from passwordmanager.models import Account


class AccountSerializer(serializers.ModelSerializer):
    class Meta:
            model = Account
            fields = (
                "id",
                "username",
                "password",
                "application_name",
                "user_id"
            )