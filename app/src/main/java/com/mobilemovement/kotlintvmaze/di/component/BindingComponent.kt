package com.mobilemovement.kotlintvmaze.di.component

import androidx.databinding.DataBindingComponent
import com.mobilemovement.kotlintvmaze.binding.ImageBindingAdapter
import com.mobilemovement.kotlintvmaze.di.module.BindingModule
import com.mobilemovement.kotlintvmaze.di.scope.BindingScope
import dagger.Component

@BindingScope
@Component(dependencies = [AppComponent::class], modules = [BindingModule::class])
interface BindingComponent : DataBindingComponent {

    override fun getImageBindingAdapter(): ImageBindingAdapter
}
