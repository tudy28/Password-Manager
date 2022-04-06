package com.example.passwordmanager.accounts.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.passwordmanager.PasswordManagerApplication
import com.example.passwordmanager.accounts.domain.Account
import com.example.passwordmanager.accounts.repo.AccountDao
import com.example.passwordmanager.accounts.repo.AccountRepository
import com.example.passwordmanager.accounts.repo.AccountRepositoryImpl
import com.example.passwordmanager.accounts.repo.AccountRoomDatabase
import com.example.passwordmanager.accounts.service.AccountService
import com.example.passwordmanager.accounts.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://172.30.114.186:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAccountService(retrofit: Retrofit): AccountService {
        return retrofit.create(AccountService::class.java)
    }

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AccountRoomDatabase::class.java,
        "account_database"
    ).allowMainThreadQueries().build()// The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: AccountRoomDatabase) = db.accountDao() // The reason we can implement a Dao for the database


    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {

        @Binds
        @Singleton
        fun provideAccountRepository(repo: AccountRepositoryImpl): AccountRepository



        @Binds
        @Singleton
        fun provideGetAccountsUseCase(uc: GetAccountsUseCaseImpl): GetAccountsUseCase

        @Binds
        @Singleton
        fun provideGetAccountsUseCaseRemote(uc: GetAccountsUseCaseRemoteImpl): GetAccountsUseCaseRemote


        @Binds
        @Singleton
        fun provideGetOneAccountUseCase(uc: GetOneAccountUseCaseImpl): GetOneAccountUseCase

        @Binds
        @Singleton
        fun provideGetOneAccountUseCaseRemote(uc: GetOneAccountUseCaseRemoteImpl): GetOneAccountUseCaseRemote



        @Binds
        @Singleton
        fun provideAddAccountUseCase(uc: AddAccountUseCaseImpl): AddAccountUseCase

        @Binds
        @Singleton
        fun provideAddAccountUseCaseRemote(uc: AddAccountUseCaseImplRemote): AddAccountUseCaseRemote


        @Binds
        @Singleton
        fun provideDeleteAccountUseCase(uc: DeleteAccountUseCaseImpl): DeleteAccountUseCase

        @Binds
        @Singleton
        fun provideDeleteAccountUseCaseRemote(uc: DeleteAccountUseCaseRemoteImpl): DeleteAccountUseCaseRemote


        @Binds
        @Singleton
        fun provideUpdateAccountUseCase(uc: UpdateAccountUseCaseImpl): UpdateAccountUseCase

        @Binds
        @Singleton
        fun provideUpdateAccountUseCaseRemote(uc: UpdateAccountUseCaseImplRemote): UpdateAccountUseCaseRemote


        @Binds
        @Singleton
        fun provideCacheAccountsUseCase(uc: CacheAccountsUseCaseImpl): CacheAccountsUseCase

        @Binds
        @Singleton
        fun provideSyncAccountsUseCase(uc: SyncAccountsUseCaseImpl): SyncAccountsUseCase


    }

}