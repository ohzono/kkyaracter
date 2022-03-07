# Kyaracter

This application is "Kyaracter", a mini game for Playing & Learning a character with sound, but now in development.

## Skills
- Kotlin Multiplatform Mobile
- Single Activity
- Navigation Component
- MVVM + UseCase
- SharedFlow
- Coroutine
- Multiplatform-Settings
- kotlinx.serialization
- MediaPlayer

## アーキテクチャ

```mermaid
graph LR
    subgraph アーキテクチャ
        repo[Repository]
        model[Model]
        db[DataBase]
        uc[UseCase]
        vm[ViewModel]
        v[View]
        db-->model
        uc-->repo
        repo-->db
        vm-->uc
        v-->vm

        subgraph Domain
            uc
        end


        subgraph Infra
            repo
            db
        end

        subgraph Presentation
            v
            vm
        end
    end
```