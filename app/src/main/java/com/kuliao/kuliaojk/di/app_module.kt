package com.kuliao.kuliaojk.di

import com.kuliao.kuliaojk.vm.CartViewModel
import com.kuliao.kuliaojk.vm.HomeViewModel
import com.kuliao.kuliaojk.vm.MyViewModel
import com.kuliao.kuliaojk.vm.SortViewModel
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
}


val appModule = listOf(viewModelModule)