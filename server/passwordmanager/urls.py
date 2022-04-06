from django.urls import path

from passwordmanager import views

urlpatterns = [
    path('api/accounts', views.add_account),
    path('api/users/<str:user_id>/accounts', views.user_accounts),
    path('api/accounts/<str:account_id>',views.delete_update_find_account),

]