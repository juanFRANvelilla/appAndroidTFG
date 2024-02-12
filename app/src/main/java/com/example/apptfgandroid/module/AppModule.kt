package com.example.apptfgandroid.module

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.apptfgandroid.appViewModel.AppViewModel
import com.example.apptfgandroid.data.Preferences.JWToken.PreferenceToken
import com.example.apptfgandroid.data.Preferences.JWToken.PreferenceTokenImpl
import com.example.apptfgandroid.dataSource.DataSourceLogin
import com.example.apptfgandroid.dataSource.DataSourceManageContacts
import com.example.apptfgandroid.models.UserDTO
import com.example.apptfgandroid.module.Qualifier.*
import com.example.apptfgandroid.repository.RepositoryLogin
import com.example.apptfgandroid.repository.RepositoryManageContacts
import com.example.apptfgandroid.repository.preferences.JWTokenRepository
import com.example.apptfgandroid.repository.preferences.JWTokenRepositoryImpl
import com.example.apptfgandroid.ui.screens.Login.LoginViewModel
import com.example.apptfgandroid.ui.screens.ManageContacts.ManageContactsViewModel
import com.example.apptfgandroid.useCase.UseCaseLogin
import com.example.apptfgandroid.useCase.UseCaseManageContact
import com.example.apptfgandroid.useCase.preferences.GetToken
import com.example.apptfgandroid.useCase.preferences.SaveToken
import com.example.tfgapp.service.ApiService
import com.example.tfgapp.service.RetrofitService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single(named(JWToken)) { MutableLiveData<String>().apply { value = "" } }
    viewModel { AppViewModel(get(named(JWToken))) }

    //dependencias para el view model de manageContacts
    single<DataSourceManageContacts> { DataSourceManageContacts(get()) }
    single<RepositoryManageContacts>{ RepositoryManageContacts(get()) }
    single<UseCaseManageContact>{ UseCaseManageContact(get()) }

    viewModel { ManageContactsViewModel(get()) }


    //dependencias para el view model de login
    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.128:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<DataSourceLogin> { DataSourceLogin(get(), get()) }
    single<RepositoryLogin> { RepositoryLogin(get()) }
    single<UseCaseLogin> { UseCaseLogin(get()) }

    viewModel { LoginViewModel(get()) }






    single<PreferenceToken> { PreferenceTokenImpl(androidContext()) }
    single<JWTokenRepository> { JWTokenRepositoryImpl(get()) }
    single { GetToken(get()) }
    single { SaveToken(get()) }

}

enum class Qualifier{
    JWToken,
}



