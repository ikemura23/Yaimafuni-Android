# モジュール構成レビューと改善提案

## レビュー結果（優先度順）

### 1. `domain` と `data` の責務が逆転しています（重要）
`Repository` のインターフェースが `data` 側にあり、`androidApp` が `data` の型に直接依存しています。

- `data/src/main/kotlin/com/yaeyama/linerchecker/repository/TopStatusRepository.kt`
- `data/src/main/kotlin/com/yaeyama/linerchecker/repository/StatusDetailRepository.kt`
- `androidApp/src/main/java/com/yaeyama/linerchecker/ui/main/MainViewModel.kt:4`
- `androidApp/src/main/java/com/yaeyama/linerchecker/ui/dashboard/DashBoardViewModel.kt:5`

この構成だと、UI層がデータ層契約に縛られ、層分離のメリット（実装差し替え、テスト容易性）が弱くなります。

### 2. ViewModel の DI スコープが不適切です（重要）
`ViewModel` を `single` で登録し、さらに `KoinComponent` で自己注入している箇所があります。

- `androidApp/src/main/java/com/yaeyama/linerchecker/di/ViewModelModule.kt:15`
- `androidApp/src/main/java/com/yaeyama/linerchecker/di/ViewModelModule.kt:18`
- `androidApp/src/main/java/com/yaeyama/linerchecker/di/ViewModelModule.kt:27`
- `androidApp/src/main/java/com/yaeyama/linerchecker/ui/weather/WeatherViewModel.kt:19`
- `androidApp/src/main/java/com/yaeyama/linerchecker/ui/typhoon/list/TyphoonListViewModel.kt:21`

`ViewModel` は `viewModel { ... }` とコンストラクタ注入に統一した方が、ライフサイクルとテストの両面で安定します。

### 3. debug用 Fake 実装が実際には使われていません（中）
コメントでは Fake を返す想定ですが、実バインドは本番実装です。

- `data/src/debug/kotlin/com/yaeyama/linerchecker/di/DataModule.kt:18`
- `data/src/debug/kotlin/com/yaeyama/linerchecker/di/DataModule.kt:23`
- `data/src/debug/kotlin/com/yaeyama/linerchecker/repository/FakeTopStatusRepository.kt`
- `data/src/debug/kotlin/com/yaeyama/linerchecker/repository/FakeStatusDetailRepository.kt`
- `data/src/debug/kotlin/com/yaeyama/linerchecker/repository/FakeTyphoonRepositoryImpl.kt`

意図と実態のズレで、デバッグ挙動と期待が一致しません。

### 4. ユースケース配置が不自然で未整理コードが残っています（中）
`usecase` が `data` にあり、責務境界が曖昧です。未使用/中途クラスも存在します。

- `data/src/main/kotlin/com/yaeyama/linerchecker/usecase/GetStatusDetail.kt:12`
- `data/src/main/kotlin/com/yaeyama/linerchecker/ShipStatusDetailRepository.kt:5`

ユースケースは `domain`（または `application`）に寄せるのが自然です。

### 5. 命名・パッケージの一貫性に課題があります（軽微）
`tyhoon` などの typo、namespace/applicationId/package の揺れが見られます。

- `domain/src/main/java/com/yaeyama_liner_checker/domain/tyhoon/Typhoon.kt`
- `androidApp/build.gradle.kts:6`

長期運用で認知コストが上がります。

## 改善案（現実的な段階導入）

1. まずは3モジュールのまま責務を正す（最優先）
   - `Repository` インターフェースを `domain` へ移動
   - `data` は実装のみ保持
   - `androidApp` は `domain` 契約だけ参照

2. DI を整理
   - すべての ViewModel を `viewModel { ... }` に統一
   - `KoinComponent` + `by inject()` を ViewModel から排除し、コンストラクタ注入へ

3. debug/release バインドを明確化
   - `data/src/debug/.../DataModule.kt` で Fake 実装を実際に bind
   - `data/src/release/.../DataModule.kt` は本番実装 bind

4. ユースケース層を明示
   - `GetStatusDetail` を `domain`（または `app/usecase`）へ移動
   - `ShipStatusDetailRepository` の用途を明確化し、不要なら削除

5. その後に必要ならモジュール細分化
   - `:feature:dashboard`, `:feature:weather`, `:feature:typhoon`, `:feature:detail`
   - `:core:model`, `:core:common`, `:data:firebase`

まずは責務整理後に分割する方が安全です。
