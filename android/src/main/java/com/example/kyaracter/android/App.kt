package com.example.kyaracter.android

import android.app.Application
import com.example.kyaracter.android.di.*
import org.kodein.di.*

class App : Application(), DIAware {
    override val di: DI by DI.lazy {
        import(repositoryModule)
        import(dataBaseModule)
        import(domainModule)
    }
}
