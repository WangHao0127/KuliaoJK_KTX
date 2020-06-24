package com.kuliao.kuliaojk.di

import com.kuliao.kuliaojk.api.UserApi
import com.kuliao.kuliaojk.db.userDao
import com.kuliao.kuliaojk.http.UserService
import com.kuliao.kuliaojk.reposotory.UserRepository
import com.kuliao.kuliaojk.vm.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Author: WangHao
 * Created On: 2020/06/22  15:51
 * Description:
 */
val viewModelModule = module {
    viewModel {
        HomeViewModel()
    }
    viewModel {
        SortViewModel()
    }
    viewModel {
        CartViewModel()
    }
    viewModel {
        MyViewModel()
    }
    viewModel {
        UserViewModel(get())
    }
}

val reposModule = module {
    //factory 每次注入时都重新创建一个新的对象
    factory {
        UserRepository(get(), get())
    }

}

val remoteModule = module {
    //single 单列注入

    single<UserApi> { UserService }
}

val localModule = module {
    single { userDao }
}
val appModule = listOf(viewModelModule, reposModule, remoteModule, localModule)