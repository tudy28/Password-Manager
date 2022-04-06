import time

from django.http import JsonResponse
from django.shortcuts import render

# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.parsers import JSONParser

from passwordmanager.models import Account
from passwordmanager.serializers import AccountSerializer


@api_view(['POST'])
def add_account(request):
    data = request.data
    data['user_id'] = 2
    account_serializer = AccountSerializer(data=request.data)
    if account_serializer.is_valid():
        account = account_serializer.save()
        new_account = AccountSerializer(Account.objects.get(pk=account.id))
        print("Account with ID "+str(account.id)+" has been added...")
        return JsonResponse(new_account.data, status=status.HTTP_200_OK)
    else:
        print("Error while adding the account...")
        return JsonResponse({'message': account_serializer.errors}, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def user_accounts(request,user_id):
    if request.method == 'GET':
        accounts = Account.objects.filter(user_id=user_id)
        account_serializer = AccountSerializer(accounts, many=True)
        time.sleep(2)
        print("Accounts accessed with success...")
        return JsonResponse(account_serializer.data, safe=False)




@api_view(['DELETE','PUT','GET'])
def delete_update_find_account(request,account_id):
    if request.method == 'GET':
        account = Account.objects.get(id=account_id)
        account_serializer = AccountSerializer(account)
        print("Account with ID "+account_id+" accessed with success...")
        return JsonResponse(account_serializer.data,safe=False)
    if request.method == 'DELETE':
        try:
            deleted_account = Account.objects.get(pk=account_id)
            deleted_account_serializer = AccountSerializer(deleted_account)
            deleted_account.delete()
            print("The account with ID "+account_id+" has been deleted...")
            return JsonResponse(deleted_account_serializer.data, status=status.HTTP_200_OK)
        except Exception:
            print("Error while deleting the account with ID"+account_id+"...")
            return JsonResponse({'message': 'The account was not found!'}, status=status.HTTP_400_BAD_REQUEST)
    if (request.method == 'PUT'):
        try:
            data = JSONParser().parse(request)
            updated_account = Account.objects.get(pk=account_id)
            updated_account.username = data['username']
            updated_account.password = data['password']
            updated_account.application_name = data['application_name']
            updated_account.save()
            print("The account with ID "+account_id+" has been updated...")
            return JsonResponse(AccountSerializer(updated_account).data, safe=False, status=status.HTTP_201_CREATED)
        except Exception as e:
            print("Error while updating the account with ID"+account_id+"...")
            return JsonResponse({'message': 'The account was not found!'}, status=status.HTTP_400_BAD_REQUEST)