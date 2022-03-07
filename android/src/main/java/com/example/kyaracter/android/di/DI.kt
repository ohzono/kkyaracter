package com.example.kyaracter.android.di

import com.example.data.repository.KyaraRepository
import com.example.data.repository.KyaraRepositoryImpl
import com.example.domain.playing.DeletePlayingDataUseCase
import com.example.domain.playing.LoadPlayingDataUseCase
import com.example.domain.playing.SavePlayingDataUseCase
import com.example.infra.db.KyaraDataBase
import com.example.infra.db.KyaraDataBaseImpl
import org.kodein.di.*

val repositoryModule = DI.Module("repository") {
    bind<KyaraRepository>() with provider { KyaraRepositoryImpl(dataBase = instance()) }
}
val dataBaseModule = DI.Module("database") {
    bind<KyaraDataBase>() with singleton { KyaraDataBaseImpl() }
}
val domainModule = DI.Module("domain") {
    bind<LoadPlayingDataUseCase>() with provider { LoadPlayingDataUseCase(repository = instance()) }
    bind<SavePlayingDataUseCase>() with provider { SavePlayingDataUseCase(repository = instance()) }
    bind<DeletePlayingDataUseCase>() with provider { DeletePlayingDataUseCase(repository = instance()) }
}
