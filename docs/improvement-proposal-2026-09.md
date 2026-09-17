# プロジェクト改善提案（2026-09）

> **本書の位置づけ**
> 本書は 2026-09 時点の**調査記録**であり、進捗を管理するタスクリストではありません。
> **実行は GitHub Issue で管理します。** 着手する単位（1 Issue = 1 PR 相当）だけを、着手の直前に Issue として切り出します。
> 本書が持つのは、各項目の根拠（file:line）、項目間の依存関係、優先度の判断材料です。Issue からは本書の該当セクションを参照してください。
>
> この形にしているのは、Issue #154（2025-09-16）が 100 個近いチェックボックスを 1 つの Issue に収めた結果ほとんど実行されなかったこと、および `module-architecture-review.md` が状態を持たないまま陳腐化したことを踏まえたためです。**全 46 件の実施を前提にしないでください**（付録 A の仕分けを参照）。

## この文書について

`androidApp` / `data` / `domain` / ビルド構成 / CI を横断調査し、改善点を優先度と理由付きで整理したものです。「何が」「なぜ問題で」「どう直すか」までを記述し、実施の判断材料を提供します。

### 背景

この数ヶ月でプロジェクトは大きく前進しました。

- AGP 9.0.0 / Gradle 9.1.0 / Kotlin 2.3.10 への追随
- Fragment・DataBinding・ViewPager2・Navigation Component の全廃と **Compose 100% 化**（XML レイアウトは 0 件）
- LiveData の全廃と Flow / StateFlow への統一
- Repository interface の `domain` への移設、ViewModel の DI スコープ是正、`mock` バリアントの新設

その結果、残っている負債の性質が変わりました。「古い技術が残っている」のではなく、**「移行はしたが後始末が終わっていない」** 種類の負債が層をなしています。本提案書はそこに焦点を当てます。

### 調査対象と規模

| 対象 | 規模 |
|---|---|
| `androidApp` | Kotlin/Java 57 ファイル / 3,201 行（300 行超のファイルは 0 件） |
| `data` | 13 ファイル / 668 行 |
| `domain` | 20 ファイル / 173 行 |
| テスト | **2 ファイル / 5 メソッド**（うち 1 つは IDE テンプレートの残骸） |

ファイル分割の粒度は良好で、最大でも 231 行です。構造上の大きな問題はありません。

### 優先度の考え方

| 優先度 | 基準 |
|---|---|
| **P0** | ストア更新が止まる / 本番配布物の健全性に関わる |
| **P1** | ユーザーに見える不具合が既に出ている |
| **P2** | P0・P1 の再発を防ぐ仕組みが無い |
| **P3** | 移行の後始末・保守性 |

---

## サマリ

| 優先度 | 件数 | 主な内容 |
|---|---|---|
| P0 | 6 | **DB の書き込みルール未確認**、targetSdk 36 未達、debug 鍵でのリリース署名、R8 完全無効、SDK バージョンの二重管理 |
| P1 | 11 | エラー処理が 4 系統で到達不能、ローディング UI 不在、時刻表がスクロール不能、リリースビルドでのログ出力 |
| P2 | 8 | data/domain のテスト 0 件、静的解析なし、CI が古く main 未検証 |
| P3 | 22 | M2 テーマ × M3 コンポーネントのねじれ、台風一覧の機能デグレ、未使用依存 6 件・未使用リソース 15 件超 |

---

# P0: 配布要件 — ストア更新が止まるリスク

## P0-1. Firebase Realtime Database のセキュリティルールが未確認（コード外）

**現状**
このアプリには **Firebase Auth のコードが 1 行もありません**（`FirebaseAuth` / `signInAnonymously` / `firebase-auth` の参照が全て 0 件）。したがって Realtime Database は未認証アクセスを許可する設定になっているはずです。

書き込みルールがどうなっているかは**リポジトリからは判断できず、Firebase Console でのみ確認できます**。

**なぜ問題か**
もしテストモードの既定（`".read": true, ".write": true`）のままなら、**第三者が運航情報を書き換えられます**。「欠航」を「通常運行」に改ざんされた場合、利用者が港へ向かって船が無い、あるいはその逆といった事態を招きます。運航情報アプリの性質上、本提案書のどの項目よりも影響が大きい可能性があります。

なお本件は Issue #154（2025-09-16）で既に「🔴 緊急対応」として挙げられていますが、未着手です。

**推奨対応**
1. Firebase Console → Realtime Database → ルール で現在の設定を確認する
2. 書き込みが開いていれば、読み取り専用に変更する

```json
{
  "rules": {
    ".read": true,
    ".write": false
  }
}
```

データの更新は運航会社側のバックエンド（Admin SDK / Console）から行う想定のはずなので、クライアントからの書き込みを塞いでも運用に影響しないか併せて確認してください。

**影響範囲**
Firebase Console（コード変更なし）

---

## P0-2. targetSdk 35 が Google Play の更新要件を満たさない

**現状**
`build-logic/convention/src/main/kotlin/yaeyama.android.application.gradle.kts:20` で `targetSdk = 35`（`compileSdk` は `:16` で 35）。

**なぜ問題か**
Google Play はアプリ更新の targetSdk 要件を毎年引き上げており、2026-08-31 以降は API 36 が要件です。現状のままでは**アップデートの提出がブロックされる見込み**です。AGP 9.0 は API 36 系に対応しているため、ツールチェーン側の障害はありません。

**推奨対応**
`compileSdk` / `targetSdk` を 36 へ。ただし P0-3 と同一の作業単位として扱うこと。

**影響範囲**
`build-logic/convention/src/main/kotlin/yaeyama.android.application.gradle.kts`、`gradle/libs.versions.toml`（P0-6 参照）

---

## P0-3. edge-to-edge 未対応のまま targetSdk を上げるとレイアウトが崩れる

**現状**
`enableEdgeToEdge()` の呼び出しが全 Activity で 0 件。`MainScaffold` / `YaimafuniScaffold` は `contentWindowInsets` を引数で通しているものの、`ui/portstatusdetail/PortStatusDetailScreen.kt:56` の `Column` のように inset を考慮していない画面があります。`ui/dashboard/DashBoardScreen.kt:69` には `.padding(paddingValues) // Edge to edge対応のためのpaddingを追加` とコメントがあり、対応が画面ごとにまちまちです。

**なぜ問題か**
targetSdk 35（Android 15）以降、edge-to-edge は強制されます。現在は targetSdk 35 なので既に有効化されているはずですが、Activity 側で `enableEdgeToEdge()` を呼んでおらず、画面ごとの inset 対応も不揃いのため、**システムバーとコンテンツが重なる箇所が残っています**。36 へ上げる際にさらに顕在化します。

**推奨対応**
全 Activity（`MainActivity` / `PortStatusDetailActivity` / `TyphoonDetailActivity`）で `enableEdgeToEdge()` を呼び、各画面の inset 対応を `Scaffold` の `paddingValues` 経由に統一する。`androidx.activity:activity-compose` は既に依存にあるため追加依存は不要。

**影響範囲**
3 Activity、`ui/common/YaimafuniScaffold.kt`、`ui/main/compose/MainScaffold.kt`、各 Screen

---

## P0-4. リリースビルドが debug キーストアで署名されている

**現状**
```kotlin
// build-logic/convention/src/main/kotlin/yaeyama.android.application.gradle.kts:29
release {
    signingConfig = signingConfigs.getByName("debug")
}
```
本番用の `signingConfigs.create("release")` も keystore ファイルも存在しません（`.gitignore` に `*.jks` はあるが実体なし、`local.properties` のキーは `sdk.dir` のみ）。

**なぜ問題か**
CI の `./gradlew assembleRelease` は debug 鍵で署名した APK を作っているだけで、**リリース検証として機能していません**。実際の配布形式である AAB（`bundleRelease`）は CI で一度も組まれていません。リリース作業が手元の暗黙知に依存している状態です。

**推奨対応**
`signingConfigs.create("release")` を追加し、keystore のパスとパスワードを環境変数または `local.properties` から読む。CI では `bundleRelease` をシークレット経由の署名情報で組む（署名情報が無い環境では未署名でビルドのみ検証する分岐でも可）。

**影響範囲**
`build-logic/convention/src/main/kotlin/yaeyama.android.application.gradle.kts`、`.github/workflows/android_build_check.yaml`

---

## P0-5. R8 が完全に無効

**現状**
`yaeyama.android.application.gradle.kts:27`（release）と `:32`（debug）の両方で `isMinifyEnabled = false`。`shrinkResources` の指定もなし。`androidApp/proguard-rules.pro` の実効ルールは `-dontwarn okio.**` と `-dontwarn javax.annotation.**` の 2 行のみで、okio は現在の依存グラフに現れないため残骸です。

**なぜ問題か**
コード縮小・難読化・リソース縮小がすべて無効で、`proguard-rules.pro` は事実上デッドファイルです。P3 で挙げる未使用リソース 15 件超・未使用依存 6 件がそのまま APK に同梱されます。

**推奨対応**
release で `isMinifyEnabled = true` / `isShrinkResources = true` を有効化する。

> **有効化時の注意**: `yaeyama.android.library.gradle.kts:19` が `proguardFiles(..., "proguard-rules.pro")` を宣言しているのに `data/proguard-rules.pro` が存在しません。AGP 9 では `android.proguard.failOnMissingFiles` の既定が `false` から `true` に変わったため、**minify を有効化した時点でビルドが失敗しうる**。先に空ファイルを置くか宣言から外すこと。
>
> Firebase Realtime Database が `DataSnapshot.getValue<T>()` でリフレクションデシリアライズを行っているため、`domain` のモデルクラスに対する keep ルールが必須です（P3-12 の DTO 層導入と合わせて検討するのが安全）。

**影響範囲**
`build-logic/convention/src/main/kotlin/*.gradle.kts`、`androidApp/proguard-rules.pro`、`data/proguard-rules.pro`（新規）

---

## P0-6. SDK バージョンが二重管理され、片方が死んでいる

**現状**
`gradle/libs.versions.toml:3-5` に `app-compileSdk = "35"` / `app-minSdk = "24"` / `app-targetSdk = "35"` が定義されていますが、**どこからも参照されていません**。実際に効いているのは convention plugin 内のハードコード（`yaeyama.android.application.gradle.kts:16,19,20`、`yaeyama.android.library.gradle.kts:9,12`）です。

**なぜ問題か**
「バージョンカタログを直したのに反映されない」という罠です。P0-2 の作業で最初に踏み抜く可能性が高い。

**推奨対応**
convention plugin から `libs` を参照する形に一本化する（`the<VersionCatalogsExtension>()` 経由）。難しければ catalog 側の 3 エントリを削除してハードコードに寄せ、真値の所在を 1 箇所にする。

**影響範囲**
`gradle/libs.versions.toml`、`build-logic/convention/src/main/kotlin/*.gradle.kts`

---

# P1: 機能バグ — ユーザーに見える不具合

P1-1 から P1-3 は独立した 3 つのバグではなく、**「エラーが起きてもユーザーに伝わらない」という単一の構造的問題**が 3 層に分かれて現れたものです。離島航路の運航情報アプリで「取得に失敗した」と「今日は運航情報が無い」が区別できないのは、意思決定に直結する実害があります。

## P1-1. `UiState.Loading` が `UiState` を継承しておらず emit 不可能

**現状**
```kotlin
// domain/src/main/java/com/yaeyama_liner_checker/domain/common/UiState.kt
sealed class UiState<out T> {
    object Loading                                    // ← : UiState<Nothing>() が無い
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val error: Throwable) : UiState<Nothing>()
}
```

**なぜ問題か**
`Loading` は単なるネストされた `object` で `UiState` のサブタイプではないため、`Flow<UiState<T>>` に emit できません。結果として `UiState` のサブクラスは Success / Error の 2 つだけになり、`ui/weather/WeatherViewModel.kt:26` の `else -> WeatherUiState.Loading` が**到達不能なデッドブランチ**になっています。domain 側の型定義のミスが UI の分岐まで腐らせている形です。

**推奨対応**
`object Loading : UiState<Nothing>()` に修正。あわせて `data class Success<T>` が外側の `out T` を隠して独自の `T` を宣言している点も整理する。

**影響範囲**
`domain/.../common/UiState.kt`、`ui/weather/WeatherViewModel.kt`

---

## P1-2. 天気の取得失敗が「空欄の天気」として表示される

**現状**
```kotlin
// ui/weather/WeatherViewModel.kt:24
is UiState.Error -> WeatherUiState.Success(WeatherInfo())
```
`WeatherUiState`（`ui/weather/WeatherUiState.kt:8-13`）には `Loading` と `Success` しか定義されていません。

**なぜ問題か**
`WeatherRepositoryImpl.kt:26-34` は `UiState.Error` を正しく流しているのに、ViewModel が**それを空データの成功に潰しています**。ユーザーには通信失敗が「全項目が空欄の天気画面」として見えます。

**推奨対応**
`WeatherUiState` に `Error` を追加し、`WeatherPage` にエラー表示とリトライ手段を実装する。

**影響範囲**
`ui/weather/WeatherUiState.kt`、`WeatherViewModel.kt`、`ui/weather/compose/WeatherPage.kt`

---

## P1-3. リポジトリが例外を空リストに変換し、下流のエラー処理を全滅させている

**現状**
```kotlin
// data/.../repository/TopStatusRepositoryImpl.kt:22-27
return dbRef.valueEvents.map { snapShot ->
    snapShot.getValue<TopPort>()?.toList() ?: listOf()   // null も空リスト扱い
}.catch {
    Timber.e(it, "fetchTopStatuses failed")
    emit(listOf())                                       // 例外を空リストへ変換
}
```
`data/.../repository/TyphoonRepositoryImpl.kt:24-27` も同型。

**なぜ問題か**
Flow が正常終了扱いになるため、下流に書かれたエラー処理が**すべて到達不能**です。

- `ui/dashboard/DashBoardViewModel.kt:59` の `isError.update { true }` — 発火しない
- `ui/typhoon/list/TyphoonListViewModel.kt:30` の `emit(TyphoonUiState.Error)` — 発火しない
- `ui/typhoon/list/compose/TyphoonListScreen.kt:57` の Error UI — 描画されない

つまり ViewModel と Screen にエラー処理が書かれているのに、**一度も動いたことがない**状態です。

**推奨対応**
リポジトリでは例外を握り潰さず流す。`getValue` が null の場合も「空」ではなく明示的な失敗として扱うか、空と失敗を型で区別する。P3-11（ドメイン例外型の導入）と合わせると扱いやすくなります。

**影響範囲**
`data/.../repository/TopStatusRepositoryImpl.kt`、`TyphoonRepositoryImpl.kt`

---

## P1-4. ダッシュボードにローディング・エラー UI が無い（state は正しく更新されている）

**現状**
`ui/dashboard/DashBoardViewModel.kt:52-60` は `isLoading` / `isError` を正しく更新し、`DashBoardUiState`（`DashBoardUiState.kt:6-7`）にも両方のフィールドがあります。しかし `ui/dashboard/DashBoardScreen.kt:64-80` は `uiState.portList` しか参照していません。

**なぜ問題か**
状態は流れているのに描画されていません。`ui/dashboard/component/DashBoardHeader.kt:38` に残るコメントが経緯を示しています。

```
// TODO: ProgressBarを配置する（dash_board_fragment.xml の id/port_progressbar を参照）
```

参照先の `dash_board_fragment.xml` は既に削除済みで、**Compose 移行時に落ちた機能がそのまま放置**されています。

**推奨対応**
`isLoading` / `isError` を描画する。エラー表示にはリトライ手段（`fetchPortList()` の再実行）を付ける。P1-3 を直さないとエラー分岐は発火しない点に注意。

**影響範囲**
`ui/dashboard/DashBoardScreen.kt`、`ui/dashboard/component/DashBoardHeader.kt`

---

## P1-5. 運行詳細の `isLoading` を true にする箇所がどこにも無い

**現状**
`ui/portstatusdetail/PortStatusDetailViewModel.kt:27` で `isLoading` を宣言していますが、`fetchDetail()`（`:58-84`）の中で更新しているのは `isError` だけです。`isLoading` は**常に false のまま** `uiState` に流れます。描画側の `ui/portstatusdetail/PortStatusDetailScreen.kt:39-45` も loading / error を参照していません。

**なぜ問題か**
運行詳細画面にローディング表示もエラー表示も存在しません。P1-4 と同根で、状態管理の配線が途中で切れています。また `:68-72` / `:77-82` の `catch` で `isLoading` を false に戻す処理も無く、後で true にし始めると今度は戻らなくなります。

**推奨対応**
`onStart` / `onEach` で `isLoading` を更新し、Screen 側で描画する。`:79` の TODO（時刻表のみ失敗した場合に `isError` を分離）もこのタイミングで扱う。

**影響範囲**
`ui/portstatusdetail/PortStatusDetailViewModel.kt`、`PortStatusDetailUiState.kt`、`PortStatusDetailScreen.kt`

---

## P1-6. 時刻表が長いと画面外で切れてスクロールできない

**現状**
```kotlin
// ui/portstatusdetail/PortStatusDetailScreen.kt:56
Column(
    modifier = modifier.padding(16.dp)     // verticalScroll が無い
) {
    PortMainStatus(...)
    Spacer(...)
    TimeTableList(timeTable = timeTable)
}
```
`ui/portstatusdetail/component/TimeTableList.kt:47` は `timeTable.row.forEach` を `Column` 内で回しています（`LazyColumn` 未使用）。

**なぜ問題か**
便数の多い航路では**時刻表の下部が画面外に出たまま到達できません**。運航情報アプリの中核機能が使えない状態です。`ui/dashboard/DashBoardScreen.kt:70` には `verticalScroll(rememberScrollState())` があり、画面間で対応が不揃いです。

**推奨対応**
`verticalScroll` を追加する。行数が増える見込みがあるなら `LazyColumn` 化も検討（ただし `TimeTableList` は `Card` 内のため、外側を `LazyColumn` にする設計変更が必要）。

**影響範囲**
`ui/portstatusdetail/PortStatusDetailScreen.kt`、`ui/portstatusdetail/component/TimeTableList.kt`

---

## P1-7. 台風バッジが recomposition のたびに Firebase を再購読する

**現状**
```kotlin
// ui/main/compose/MainScreen.kt:30
val typhoonCount by mainViewModel.existsTyphoon().collectAsState(initial = 0)
```
```kotlin
// ui/main/MainViewModel.kt:13
fun existsTyphoon(): Flow<Int> = typhoonRepository.fetchTyphoonList().map { it.size }
```

**なぜ問題か**
`existsTyphoon()` は呼ばれるたびに**新しい cold Flow インスタンス**を返します。`collectAsState` は Flow インスタンスをキーにするため、recomposition のたびに購読が破棄・再生成され、**Firebase のリスナー登録と取得がやり直されます**。本プロジェクトで最も明確な recomposition バグです。

加えて `TyphoonListViewModel` も同じ `typhoon` パスを購読しており、台風タブ表示中は二重にリスナーが張られます。

**推奨対応**
`MainViewModel` 側で `stateIn(viewModelScope, WhileSubscribed(5_000), 0)` して `StateFlow` として公開する。

**影響範囲**
`ui/main/MainViewModel.kt`、`ui/main/compose/MainScreen.kt`

---

## P1-8. リリースビルドでもログが出力され、recomposition ごとに時刻表を文字列化している

**現状**
```kotlin
// MainApplication.kt:25-26
// デバッグビルドのみログ出力
Timber.plant(Timber.DebugTree())
```
コメントとは裏腹に**無条件で plant** されています。さらに Composable 本体で Timber を呼んでいる箇所があります。

- `ui/portstatusdetail/component/PortMainStatus.kt:33-35` — `Timber.d` を 3 回
- `ui/portstatusdetail/component/TimeTableList.kt:37` — `Timber.d(timeTable.toString())`

**なぜ問題か**
本番ビルドで logcat に運航データが出続けます。加えて Composable 本体の副作用は recomposition のたびに実行されるため、`TimeTable` 全体の `toString()` が繰り返し走ります（コストも大きい）。

**推奨対応**
`if (BuildConfig.DEBUG) { Timber.plant(Timber.DebugTree()) }` に修正し、リリース用には Crashlytics へ送る `Tree` を植えるか何も植えない。Composable 内の `Timber.d` は削除する。

**影響範囲**
`MainApplication.kt`、`ui/portstatusdetail/component/PortMainStatus.kt`、`TimeTableList.kt`

---

## P1-9. 画面を離れても Firebase リスナーが接続され続ける

**現状**
購読の張り方が 2 系統に分裂しています。

| 方式 | 対象 | 挙動 |
|---|---|---|
| `stateIn(WhileSubscribed(5s))` で Flow を公開 | `TyphoonListViewModel.kt:27-35`、`WeatherViewModel.kt:21-34` | 購読者が消えて 5 秒後にリスナー解除（正しい） |
| `viewModelScope.launch { ... .collect { } }` | `DashBoardViewModel.kt:51`、`PortStatusDetailViewModel.kt:66,75` | **`onCleared()` までリスナーが生き続ける** |

後者では `uiState` 側に付けた `stateIn(WhileSubscribed(5_000))`（`DashBoardViewModel.kt:45`）が無意味になります。購読者が消えても `topStatusesJob` が無限 Flow を collect し続けるためです。

さらに収集側も `collectAsState` が 10 箇所で、`collectAsStateWithLifecycle` は 0 件です（`androidx.lifecycle:lifecycle-runtime-compose` が version catalog に未登録で、そもそも使えません）。

**なぜ問題か**
アプリがバックグラウンドにあっても Firebase Realtime Database への接続が維持され、通信量とバッテリーを消費します。低通信環境での利用を想定するアプリでは無視できません。

**推奨対応**
1. `lifecycle-runtime-compose` を catalog に追加し、全 Screen を `collectAsStateWithLifecycle` に統一
2. `DashBoardViewModel` / `PortStatusDetailViewModel` を `stateIn` 方式に寄せて 2 系統を 1 つにする（P3-8 の UiState パターン統一と同じ作業単位）

**影響範囲**
`gradle/libs.versions.toml`、`androidApp/build.gradle.kts`、全 ViewModel と全 Screen

---

## P1-10. 運行詳細の 2 タブが同一 ViewModel を共有し、切替時に他社のデータが残る

**現状**
`ui/portstatusdetail/compose/PortStatusDetailScreen.kt:98-105` で安栄観光タブと八重山観光フェリータブが同一の `PortStatusDetailViewModel` インスタンスを受け取ります。タブ切替時は `ui/portstatusdetail/PortStatusDetailScreen.kt:35` の `LaunchedEffect(company, portCode)` が再フェッチします。

**なぜ問題か**
取得が完了するまで**もう一方の会社の運航情報が表示されたまま**になります。P1-5（ローディング表示が無い）と重なるため、ユーザーには「切り替えたのに情報が変わらない」あるいは「他社の情報を自社の情報として読む」形で見えます。運航情報アプリでは誤読のリスクが高い挙動です。

**推奨対応**
タブごとに ViewModel を分ける（`viewModel { parametersOf(company) }`）か、切替時に state をクリアしてローディングを表示する。

**影響範囲**
`ui/portstatusdetail/compose/PortStatusDetailScreen.kt`、`PortStatusDetailViewModel.kt`、`di/ViewModelModule.kt`

---

## P1-11. 航路のビジネスルールが UI にハードコードされている

**現状**
```kotlin
// ui/portstatusdetail/compose/PortStatusDetailScreen.kt:44
if (portCode == "hateruma") { ... }
```
タブ index（`0` / `1`）から `Company` へのマジックナンバー変換（`:51-55`）も `tabTitles`（`:44-48`）と二重管理されています。

**なぜ問題か**
「波照間航路は安栄観光のみ」という**航路のドメイン知識が UI 層に埋まっています**。新航路の追加や運航会社の変更時に、UI のタブ構成とビジネスルールの両方を同時に直す必要があり、片方を忘れると壊れます。

同種の問題として `data/.../repository/TopStatusRepositoryImpl.kt:30-38` の `TopPort.toList()` が港の順序を手書きで固定しており、新航路の追加時にここも直す必要があります。

**推奨対応**
`Company` と港の対応を `domain` 側（`Company` enum の拡張や専用のモデル）に移す。

**影響範囲**
`ui/portstatusdetail/compose/PortStatusDetailScreen.kt`、`domain/.../statusdetail/Company.kt`、`data/.../TopStatusRepositoryImpl.kt`

---

# P2: 品質ゲート — 再発を防ぐ仕組みが無い

P1 で挙げた 11 件のうち、`UiState.Loading` の継承漏れ、エラーの空リスト変換、`isLoading` の更新漏れは、**いずれもユニットテストがあれば書いた時点で気づける**種類のものです。P2 は個別の不具合ではなく、同じ種類の負債が再び溜まらないようにするための投資です。

## P2-1. `data` / `domain` のテストが 0 件で、テストを書ける状態ですらない

**現状**
`data` と `domain` には `src/test` / `src/androidTest` ディレクトリ自体が存在せず、`build.gradle.kts` に `testImplementation` の宣言が 1 行もありません。

```kotlin
// domain/build.gradle.kts 全文
plugins { id("yaeyama.kotlin.library") }
dependencies { implementation(libs.coroutines.core) }
```

**なぜ問題か**
`CLAUDE.md` が開発コマンドとして挙げる `./gradlew :domain:test` / `:data:test` は**実質 no-op** です。さらに mockk / mockito / `kotlinx-coroutines-test` / turbine / truth のいずれも未導入で、Flow・suspend 関数・ViewModel のテストを書こうとしても**まず依存の追加から始める必要があります**。「テストが無い」ではなく「テストを書き始められない」状態です。

加えて `FirebaseDatabase` を具象型で DI している（`data/src/release/.../DataModule.kt:21`）ため、リポジトリのテストには抽象化かエミュレータが要ります（P3-12 参照）。

**推奨対応**
1. version catalog に `kotlinx-coroutines-test` / `turbine` / `mockk` を追加
2. `data` / `domain` に `testImplementation` を宣言
3. P1 で修正する箇所から順にテストを書く（回帰テストとして機能する）

**影響範囲**
`gradle/libs.versions.toml`、`data/build.gradle.kts`、`domain/build.gradle.kts`、`build-logic`（共通化する場合）

---

## P2-2. 既存の 2 件のテストにも整備が必要

**現状**
プロジェクト全体のテストはこの 2 ファイルだけです。

| パス | 内容 |
|---|---|
| `androidApp/src/test/java/com/yaeyama/linerchecker/ExampleUnitTest.java` | `assertEquals(4, 2 + 2)` の **IDE テンプレート残骸**（Java） |
| `androidApp/src/test/java/.../PortMainStatusKtTest.kt` | `getStatusBackgroundColor()` の色マッピング 4 分岐のみ |

`PortMainStatusKtTest.kt` は `assertEquals(actual, expected)` と引数順が逆で、失敗時のメッセージが expected / actual 逆表示になります。

また `androidTest` ソースセットが**どのモジュールにも存在しない**のに、`testInstrumentationRunner` が application / library 両方の規約プラグインに設定され、`ui-test-junit4` と `espresso-core` が `androidTestImplementation` されています。`CLAUDE.md:52` の `./gradlew connectedAndroidTest` は何も実行しません。

**推奨対応**
`ExampleUnitTest.java` を削除。`PortMainStatusKtTest.kt` の引数順を修正。`androidTest` を書かないなら関連依存と `testInstrumentationRunner` を整理し、書くならソースセットを作る（判断を明示する）。

**影響範囲**
`androidApp/src/test/`、`androidApp/build.gradle.kts`、`build-logic/convention/src/main/kotlin/*.gradle.kts`

---

## P2-3. 静的解析が Android Lint のみ

**現状**
`ktlint` / `detekt` / `Spotless` / `JaCoCo` / `Kover` の設定は**全ファイルで 0 件**です。`lint.xml` も `lint-baseline.xml` も無く、`android { lint { } }` ブロックもありません（`abortOnError` / `warningsAsErrors` / `checkDependencies` すべて未設定）。

代替として存在するのは次の 2 つですが、いずれも CI では機能しません。

- `.editorconfig` — 整形ルールはあるが**強制する仕組みが無い**。履歴に `style: reformat code`（`5f88761`、`686fc2e`）が複数あるのが手動運用の証拠
- `.idea/inspectionProfiles/Project_Default.xml` — Compose の Preview 系インスペクションを設定しているが **IDE 内でのみ有効**

**なぜ問題か**
P3 で挙げる未使用コード・未使用リソース・非推奨 API（`Icons.Default.ArrowBack` など）は、静的解析があれば溜まる前に検出できたものです。

**推奨対応**
`ktlint`（`.editorconfig` が既にあるので導入コストが低い）と Android Lint の設定強化（`lint.xml` + baseline）から始める。detekt は必要に応じて。

**影響範囲**
`gradle/libs.versions.toml`、`build-logic`、`.github/workflows/`

---

## P2-4. CI の Action が古い

**現状**
```yaml
# .github/workflows/android_build_check.yaml:11,14
- uses: actions/checkout@v1
- uses: actions/setup-java@v1
```

**なぜ問題か**
どちらも Node 12 世代で GitHub 側の非推奨ランタイムです。`setup-java@v1` は `distribution` を指定できないため、**使われる JDK の出自が不定**です。

**推奨対応**
`actions/checkout@v4` と `actions/setup-java@v4`（`distribution: temurin`）へ更新。

**影響範囲**
`.github/workflows/android_build_check.yaml`

---

## P2-5. CI に Gradle キャッシュが無く、JDK が 3 系統で食い違っている

**現状**
`gradle/actions/setup-gradle` も `actions/cache` も使っておらず、1 run あたり **約 7 分**かかっています。さらに JDK の指定が 3 箇所で異なります。

| 場所 | 値 |
|---|---|
| `gradle/gradle-daemon-jvm.properties` | JDK 21 / JetBrains Runtime |
| 全モジュールの `jvmToolchain` | 17 |
| CI の `setup-java` | 17 |

**なぜ問題か**
依存・Gradle 本体（9.1.0）に加えて、**daemon 用の JDK 21 を foojay から毎回ダウンロードしている**可能性が高く、7 分の一因です。CI が遅いと PR ごとの検証が億劫になり、品質ゲートとして機能しなくなります。

**推奨対応**
`gradle/actions/setup-gradle@v4` を追加してキャッシュを効かせる。daemon JVM を toolchain に揃えるか、CI の `setup-java` を 21 にして整合させる。

**影響範囲**
`.github/workflows/android_build_check.yaml`、`gradle/gradle-daemon-jvm.properties`

---

## P2-6. main ブランチの健全性が継続検証されていない

**現状**
トリガが `on: pull_request` のみで、`push: branches: [main]` がありません。`concurrency` / `timeout-minutes` / `permissions` の宣言も無く、lint やテストのレポートをアーティファクトとして保存していません。最終 CI 実行は **2026-06-12**（3 ヶ月前）です。

**なぜ問題か**
マージ後に壊れても検知されません。また同一 PR への連続 push でジョブが積み上がり、失敗原因はログを直接読むしかありません。

**推奨対応**
`push: branches: [main]` を追加。`concurrency` でキャンセル、`timeout-minutes` を設定、`actions/upload-artifact` で lint / test レポートを保存。

**影響範囲**
`.github/workflows/android_build_check.yaml`

---

## P2-7. 依存の自動更新が無い

**現状**
`.github/dependabot.yml` も Renovate の設定も存在しません。

**なぜ問題か**
P3-14 で挙げるバージョン停滞（coroutines 1.7.3、Koin 3.4.0、Compose 1.7.x、Firebase BOM 33.5.1、Coil 2.6.0）の直接の原因です。AGP / Kotlin は手動で最新に追随できているのに**ライブラリ側だけ 1〜3 年分置いていかれている**のは、更新を検知する仕組みが無いためです。

**推奨対応**
Dependabot（`package-ecosystem: gradle`）を週次で設定。version catalog にも対応しています。

**影響範囲**
`.github/dependabot.yml`（新規）

---

## P2-8. Gradle のビルド設定が最適化されていない

**現状**
```properties
# gradle.properties 全文
org.gradle.jvmargs=-Xmx2048M -Dkotlin.daemon.jvm.options\="-Xmx2048M"
kotlin.code.style=official
android.useAndroidX=true
android.nonTransitiveRClass=false
android.nonFinalResIds=false
```

**なぜ問題か**
- `org.gradle.caching` / `org.gradle.parallel` / `org.gradle.configuration-cache` の指定が**すべて無い**
- `-Xmx2048M` は Gradle 9 + AGP 9 + Compose には小さい（4〜6GB が実用値）
- `android.nonTransitiveRClass=false` / `android.nonFinalResIds=false` は AGP 8.0 以降の既定（`true`）を**明示的にレガシー側へ固定**している。推移的 R クラスはリソース数に比例して肥大し、インクリメンタルビルドを劣化させる。AGP 10 での削除候補でもある
- `android.useAndroidX=true` は AGP 9 で既定になったため冗長（無害）

**推奨対応**
caching / parallel / configuration-cache を有効化し、`-Xmx` を引き上げる。`nonTransitiveRClass` / `nonFinalResIds` は AGP の既定（true）へ戻す（`R` 参照の修正が必要になる可能性があるため単独のコミットで）。

**影響範囲**
`gradle.properties`

---

# P3: 整理 — 移行の後始末

## P3-1. Material 2 テーマに Material 3 コンポーネントを載せており、テーマが効いていない

**現状**
テーマ定義は Material 2 です。

- `ui/theme/Theme.kt:3` — `import androidx.compose.material.MaterialTheme`
- `ui/theme/Theme.kt:33-38` — `MaterialTheme(colors =, typography =, shapes =)` は M2 の API
- `ui/theme/Type.kt` / `ui/theme/Shape.kt` — いずれも M2（`androidx.compose.material.Typography` / `Shapes`）

一方、画面側の **22 ファイルが `androidx.compose.material3`** を使っています（M2 を参照しているのは theme 配下の 3 ファイルのみ）。

**なぜ問題か**
M3 コンポーネントは M3 の `MaterialTheme` から値を読むため、**`YaimafuniAndroidTheme` で設定した色・タイポグラフィ・シェイプは一切適用されず、黙って M3 のデフォルトが使われます**。テーマが事実上死んでいるのに、全 Activity と 20 以上の `@Preview` が律儀に `YaimafuniAndroidTheme { }` で包んでいる状態です。

さらに `ui/theme/Color.kt:5-8` の `Purple200` / `Purple500` / `Purple700` / `Teal200` は **Compose テンプレート生成時の紫がそのまま本番テーマの primary** として残っています。`DarkColorPalette`（`Theme.kt:8-12`）は未使用で、`Theme.kt:31` に `val colors = LightColorPalette // ダークモードは対応しない` と明記されています。

**推奨対応**
`YaimafuniAndroidTheme` を M3（`ColorScheme` / `Typography` / `Shapes`）へ移行する。あわせて実際に使われている色（`see_blue` など）をテーマのトークンに載せ替える（P3-20 と同じ作業単位）。ダークモード対応は別判断。

**影響範囲**
`ui/theme/Theme.kt`、`Type.kt`、`Shape.kt`、`Color.kt`、および色を直接指定している全 Composable

---

## P3-2. 台風一覧のリッチな実装が使われていない（機能デグレ）

**現状**
`ui/typhoon/list/TyphoonListItemComponent.kt` は **231 行あるプロジェクト最大のファイル**ですが、`:29` の `TyphoonListItemComponent` は同ファイル `:81` 以降の `@Preview` からしか呼ばれておらず、**実画面からの参照は 0 件**です。`ArrowImage`（`:215`）も同様。

実際に描画されているのは `ui/typhoon/list/compose/TyphoonListScreen.kt:129-148` で、内容は次のとおりです。

```kotlin
Card(onClick = { onItemClick(typhoon) }) {
    Text(text = typhoon.name, modifier = Modifier.padding(16.dp), ...)
}
```

**台風名のテキストだけ**です。画像・中心気圧・最大風速・進行方向の矢印を持つ実装が存在するのに、使われていません。

**なぜ問題か**
Compose 移行の過程で情報量が落ちた機能デグレと判断されます。台風情報は運航可否の判断に直結するため、一覧で気圧や風速が見えないのは実用上の後退です。

**推奨対応**
**リッチな実装を復活させる。** `TyphoonListContent`（`TyphoonListScreen.kt:129-148`）の `Card` を `TyphoonListItemComponent` の呼び出しに差し替え、`ArrowImage` も接続する。復活後に `TyphoonDetailScreen` とのラベル表記揺れ（P3-16）を揃える。

**影響範囲**
`ui/typhoon/list/compose/TyphoonListScreen.kt`、`ui/typhoon/list/TyphoonListItemComponent.kt`

---

## P3-3. Fragment 時代の依存が 6 件残っている

**現状**
`browser` 以外はコード参照 0 件です。

| 依存 | 宣言箇所 |
|---|---|
| `browser` | `androidApp/build.gradle.kts:33`（参照は `utils/CustomTabUtil.kt:5-6` のみ。その `CustomTabUtil` 自体がデッドコード＝P3-4） |
| `constraintlayout` | `:34` |
| `viewpager2` | `:35` |
| `navigation-fragment-ktx` / `navigation-ui-ktx` / `navigation-runtime-ktx` | `:37-39` |
| `compose-theme-adapter` | `:70` |
| `appcompat` | `:32`（`AppCompatActivity` は不使用。全 Activity が `ComponentActivity`） |

`compose-theme-adapter 1.2.1`（`com.google.android.material:compose-theme-adapter`）は**非推奨・アーカイブ済み**のライブラリで、`MdcTheme` の参照も 0 件です。

**なぜ問題か**
ビルド時間と APK サイズの無駄です。P0-5（R8 無効）と相まって、使われないコードがそのまま同梱されています。

**推奨対応**
削除する。

> **注意**: `res/values/styles.xml:3` の `AppTheme` が `Theme.MaterialComponents.Light.NoActionBar` を親にしており、この MaterialComponents テーマは現在 `compose-theme-adapter` が推移的に持ち込む `com.google.android.material:material` から来ています。`compose-theme-adapter` を外すなら、**`com.google.android.material:material` を明示的に追加するか、`AppTheme` 自体を Compose 向け（`android:Theme.Material.Light.NoActionBar` など）に置き換える**必要があります。

**影響範囲**
`androidApp/build.gradle.kts`、`gradle/libs.versions.toml`、`androidApp/src/main/res/values/styles.xml`

---

## P3-4. デッドコードが 6 箇所

**現状**

| 対象 | 状況 |
|---|---|
| `ui/typhoon/list/TyphoonListViewModel.kt:25` `getTyphoonList()` | 参照 0（呼ぶと 2 本目の Firebase リスナーが張られる潜在バグでもある） |
| `utils/CustomTabUtil.kt:13` | 参照 0（`browser` 依存も道連れで不要） |
| `ext/BundleExt.kt:7` `getSerialize` | 参照 0 |
| `data/.../usecase/GetStatusDetail.kt` | 参照 0。不要な `suspend`、実時間 Flow 2 本に `zip`（`combine` が妥当） |
| `androidApp/src/debug/java/api/DummyRepository.kt` | 参照 0。唯一のメンバ `makeDummyData` が `private` で外部から呼べない。パッケージ名 `api` も規約外 |
| `ui/navigation/` | **ファイル 0 件の空パッケージ** |

**なぜ問題か**
`GetStatusDetail` は `docs/module-architecture-review.md` の指摘 4（UseCase が `data` にある層違反）が未解消のまま dead code 化したものです。読む人に「使われているのかどうか」を毎回確認させるコストが発生します。

**推奨対応**
削除する。`GetStatusDetail` については、UseCase 層を持つ方針なら `domain` へ移して使う、持たない方針なら削除する、という判断を先に行う。

**影響範囲**
上記 6 箇所

---

## P3-5. 未使用リソースが 15 件超

**現状**

| リソース | 備考 |
|---|---|
| `res/menu/bottom_nav_menu.xml` | 削除済み Fragment の ID（`dashBoardFragment` 等）を指している |
| `res/anim/slide_in_right.xml`, `slide_out_left.xml` | Fragment transition 用 |
| `res/color/bottom_item_icon_tint_selector.xml` | Compose 側は `MainBottomNavigation.kt:45-51` でハードコード |
| `res/drawable/ic_arrow_back.xml` | Compose の Icons に置換済み |
| `res/drawable-*/top_background.9.png` | **NinePatch が 5 密度分まるごと未使用** |
| `res/values/dimens.xml` | 唯一のエントリ `actionbar_text_size` が未使用＝**ファイルごと削除可** |
| `res/values/themes.xml` | 唯一のエントリ `AppTheme.AppBarOverlay` が未使用＝**ファイルごと削除可** |
| `color/badge_color`、`color/enable_icon_color`、`color/disable_icon_color` | 後 2 者は未使用セレクタ経由で推移的に未使用 |
| `string/title_activity_port_status_detail_compose` | 参照 0 |
| `string/typhoon_list_empty` | 参照 0。なのに `TyphoonListScreen.kt:115` で**同じ文字列がハードコード**されている |

**なぜ問題か**
P0-5 で `shrinkResources` が無効なため、すべて APK に同梱されます。`typhoon_list_empty` のケースは、リソースが存在するのに使われずハードコードが増えるという悪循環の実例です。

**推奨対応**
削除する。P0-5 で `shrinkResources` を有効化すれば今後の検出も容易になる。

**影響範囲**
`androidApp/src/main/res/`

---

## P3-6. デバッグ用実装が本番ソースセットに置かれている

**現状**

| ファイル | 内容 |
|---|---|
| `data/src/main/kotlin/.../repository/DebugWeatherRepositoryImpl.kt` | 54 行のダミー天気データ。`src/main` にあるため **release APK に同梱**される。`mock` からのみ参照。不要な `KoinComponent` 実装（`:12`）も残存 |
| `androidApp/src/main/java/.../ui/dashboard/FakeDashBoardDataProvider.kt` | Preview 専用のフェイクデータ 51 行。`dummyPort1` と `dummyPort2` は港名 2 文字違いのコピペ |

同様に `data/src/mock/` の Fake 3 種（`FakeTopStatusRepository.kt:12`、`FakeStatusDetailRepository.kt:21`、`FakeTyphoonRepositoryImpl.kt:13`）にも不要な `KoinComponent` 実装が残っています（コンストラクタ注入への統一時の取りこぼし）。`FakeStatusDetailRepository.kt:3` には未使用 import `android.util.Log` もあります。

**推奨対応**
`DebugWeatherRepositoryImpl` を `data/src/mock/` へ移動。`FakeDashBoardDataProvider` を `src/debug` へ移すか `@PreviewParameter` の `PreviewParameterProvider` にする。Fake 群から `KoinComponent` を外す。

**影響範囲**
`data/src/main/`、`data/src/mock/`、`androidApp/src/main/java/.../ui/dashboard/`

---

## P3-7. 同種の UI コードが複数箇所にコピペされている

**現状**

| 重複 | 箇所 |
|---|---|
| `NavigationBarItemDefaults.colors(...)` が **3 回完全に同一** | `ui/main/compose/MainBottomNavigation.kt:45-51`, `:68-74`, `:101-107` |
| 透明 TopAppBar が 3 ファイルでほぼ同一 | `ui/dashboard/component/DashBoardAppBar.kt:22-33`、`ui/weather/compose/WeatherTopAppBar.kt:20-30`、`ui/typhoon/list/TyphoonListTopAppBar.kt:18-29` |
| Scaffold ラッパーが 2 つ、引数リストまで同一 | `ui/common/YaimafuniScaffold.kt:18-41` と `ui/main/compose/MainScaffold.kt:28-63`（後者は `Box` + 太陽画像を足しただけ） |
| `ErrorContent` が同名・同構造で private 重複 | `ui/typhoon/list/compose/TyphoonListScreen.kt:95-106` と `ui/typhoon/detail/compose/TyphoonDetailScreen.kt:172-187` |
| ステータスバッジ描画 | `ui/dashboard/component/DashBoardRowItem.kt:41-51` と `ui/portstatusdetail/component/PortMainStatus.kt:55-65` |
| debug / mock の `AppModule.kt` が **1 文字違わず同一** | `androidApp/src/debug/java/.../di/AppModule.kt` と `androidApp/src/mock/java/.../di/AppModule.kt`（どちらも `FakeReviewManager`） |
| debug / release の `DataModule.kt` がコメント 1 行以外同一 | `data/src/debug/.../di/DataModule.kt` と `data/src/release/.../di/DataModule.kt` |

`MainScaffold.kt:24` には「MainScreen 専用」と書かれているのに、詳細画面 2 つ（`TyphoonDetailScreen.kt:48`、`PortStatusDetailScreen.kt:57`）が使っており、**`YaimafuniScaffold` との使い分けが崩壊**しています。

**推奨対応**
共通コンポーネントを `ui/common/` に集約する。特に Scaffold ラッパーは 1 つに統合し、太陽画像の有無をパラメータにする。ソースセット間で同一の DI モジュールは統合を検討する。

**影響範囲**
`ui/common/`、`ui/main/compose/`、各 TopAppBar、`di/`

---

## P3-8. UiState の設計パターンが 3 系統に分裂している

**現状**

| パターン | 対象 |
|---|---|
| A: 複数 `MutableStateFlow` を `combine` + boolean フラグの data class | `DashBoardViewModel.kt:27-47`、`PortStatusDetailViewModel.kt:27-56` |
| B: cold Flow を `map`/`onStart`/`catch` して `stateIn` + sealed interface | `WeatherViewModel.kt:21-34`、`TyphoonListViewModel.kt:27-35` |
| C: StateFlow 化せず cold Flow を関数で直返し | `MainViewModel.kt:13-17`、`TyphoonListViewModel.kt:25`（後者はデッドコード） |

収集側も `by ... collectAsState()` / `State<T>` 変数 + `.value` 直参照（`DashBoardScreen.kt:42`）/ `.value` を 4 回展開（`PortStatusDetailScreen.kt:41-44`）と不揃いです。

**なぜ問題か**
パターン A は boolean フラグの組み合わせで「ローディング中かつエラー」のような不正な状態を表現できてしまい、実際 P1-4 / P1-5 の描画漏れはこのパターンの画面で起きています。P1-9 のリスナー寿命の問題もパターン A に固有です。

**推奨対応**
sealed interface（パターン B）に統一する。P1-9 の `stateIn` 化と同じ作業単位で進めるのが効率的。

**影響範囲**
全 ViewModel、全 Screen

---

## P3-9. 命名とディレクトリ構造が不統一

**現状**

- **同名 Composable が 2 ファイル**: `ui/portstatusdetail/PortStatusDetailScreen.kt:29` と `ui/portstatusdetail/compose/PortStatusDetailScreen.kt:34`。後者が前者を `:117` で呼ぶ構造で、import とパッケージを見ないと追えない。前者の `:27` に `// TODO: コンポーネント名がScreenではないのでリネームしたい` と自己申告あり
- `compose/` サブパッケージの有無が機能ごとに混在（`weather/compose/` はあるが `dashboard/` は直下）。`component/` と `compose/` の使い分けも不明瞭
- `data` は `src/main/kotlin`、`domain` は `src/main/java`（中身は全て Kotlin）
- domain のパッケージが `com.yaeyama_liner_checker`（アンダースコア）で、他は `com.yaeyama.linerchecker`

**推奨対応**
`PortStatusDetailScreen` のリネームを優先（TODO が既にある）。パッケージ構成の方針を決めて揃える。domain のパッケージ名変更は影響が広いため単独のコミットで。

**影響範囲**
`androidApp/src/main/java/.../ui/`、`domain/src/`、`data/src/`

---

## P3-10. `tyhoon` ディレクトリの typo が残っている

**現状**
`domain/src/main/java/com/yaeyama_liner_checker/domain/tyhoon/Typhoon.kt`。パッケージ宣言は `typhoon` に修正済み（b3f90bd）ですが、**ディレクトリ名だけ typo のまま**です。

**なぜ問題か**
パッケージ宣言とディレクトリ名が一致しておらず、ファイル検索で見つけにくくなります。修正が中途半端な状態です。

**推奨対応**
`git mv` でディレクトリをリネームする。

**影響範囲**
`domain/src/main/java/com/yaeyama_liner_checker/domain/tyhoon/`

---

## P3-11. 日本語文字列の大半が Kotlin にハードコードされている

**現状**
`strings.xml` は 12 エントリのみで、`stringResource` の使用は 17 箇所。一方、Kotlin 内の日本語リテラルは 67 箇所あります（Preview のダミー含む）。

| 箇所 | 内容 |
|---|---|
| `ui/typhoon/detail/compose/TyphoonDetailScreen.kt:111-136` | `"更新日"` `"大きさ"` `"強さ"` `"存在地域"` `"中心気圧"` `"中心最大風速"` |
| `TyphoonDetailScreen.kt:51,151,181` | `"台風詳細"` / `"Webブラウザでみる"` / `"台風データの読み込みに失敗しました"` |
| `ui/weather/compose/WeatherListItemCardContent.kt:30-63` | `"天気："` `"最高："` `"最低："` `"風："` `"波："` と `"℃"` |
| `ui/typhoon/list/compose/TyphoonListScreen.kt:101,115` | `"エラーが発生しました"` / `"台風は発生していません。"`（後者は `string/typhoon_list_empty` が存在するのに未使用） |
| `ui/dashboard/component/DashBoardRow.kt:34,39` | `"安栄観光"` `"八観フェ"` — `string/tab_annei` `tab_ykf` が存在するのに未使用、**しかも表記が違う**（`"八重山観光\nフェリー"` vs `"八観フェ"`） |
| `ui/common/compose/BackNavigationTopAppBar.kt:40` | `"戻る"`（contentDescription） |
| `ui/portstatusdetail/component/TimeTableList.kt:68,96-99` | **本番コードのデフォルト引数に Preview 用ダミー**（`"石垣島"` `"大原港"` `"通常運行"`）が埋め込まれている |

`TyphoonListItemComponent.kt:158-190` にも `"大きさ : "` `"強さ : "` 等があり、`TyphoonDetailScreen` と**ラベル文言が重複かつ表記揺れ**（`"中心最大風速"` vs `"中心の最大風速 : "`）しています。P3-2 で一覧を復活させると表記揺れが実画面に出ます。

**なぜ問題か**
文言変更が複数ファイルに散らばります。`DashBoardRow` のケースは、リソースがあるのに使われず別表記がハードコードされているという最悪の形です。多言語対応（`res/values-*/` は 0 件）も現状では不可能です。

**推奨対応**
`strings.xml` に集約する。`TimeTableList` のデフォルト引数からダミーを外し、Preview 側に移す。P3-2 の作業時に台風のラベル表記を統一する。

**影響範囲**
`androidApp/src/main/res/values/strings.xml`、上記各ファイル

---

## P3-12. Firebase 連携の抽象化が不足し、テストが書けない

**現状**

- **コールバック変換が 2 系統**: 共通 extension `data/.../ext/FirebaseExt.kt:16-31` は `callbackFlow` + `awaitClose(removeEventListener)` で正しく実装されていますが、`StatusDetailRepositoryImpl.kt:26-41` と `:47-62` は extension を使わず**ほぼ同一の `callbackFlow` を 2 回インラインでコピペ**しています
- **DTO 層が無い**: `DataSnapshot.getValue<T>()` が **domain の data class に直接デシリアライズ**されます（`TopStatusRepositoryImpl.kt:23`、`TyphoonRepositoryImpl.kt:23`、`WeatherRepositoryImpl.kt:28`、`StatusDetailRepositoryImpl.kt:29,50`）。Mapper クラスは存在せず、唯一の変換は `TopStatusRepositoryImpl.kt:30-38` の `TopPort.toList()`
- **具象型を DI**: `data/src/release/.../DataModule.kt:21` で `single { Firebase.database(Firebase.app) }` と `FirebaseDatabase` を具象型で登録

**なぜ問題か**
domain のモデルが Firebase のスキーマに直結しているため、**DB のキー名を変えると domain が壊れます**。逆に domain のフィールド名を変えるとデシリアライズが静かに失敗します（P3-19 の `hight` / `windBlow` が直せない理由でもあります）。また domain 側の data class が全フィールドにデフォルト値を持ち nullable が 0 件なのは Firebase の no-arg コンストラクタ要件への対応ですが、その代償として**「取得失敗」と「空文字」が区別できません**（P1-3 の根っこ）。

具象型 DI のため、リポジトリのユニットテストには Firebase Emulator か具象型のモックが必要で、P2-1 のテスト導入の障害になります。

**推奨対応**
1. `StatusDetailRepositoryImpl` を `FirebaseExt.valueEvents` + `map` に統一（すぐできる）
2. data 層に DTO を置き、domain モデルへの Mapper を挟む（中期）
3. Firebase アクセスをインターフェース越しにしてテスト可能にする（中期）

**影響範囲**
`data/src/main/kotlin/`、`domain/src/main/java/`、`data/src/*/kotlin/.../di/DataModule.kt`

---

## P3-13. データ層に UI メッセージがハードコードされ、ドメイン例外型が無い

**現状**
```kotlin
// data/.../StatusDetailRepositoryImpl.kt:30
close(IllegalStateException("運行情報データが取得できませんでした"))
```
同様に `:51`、`WeatherRepositoryImpl.kt:30`（`Exception("天気データが取得できませんでした")`）。`Result` 型 / `runCatching` / `try-catch` は data・domain を通じて **1 箇所もありません**。

**なぜ問題か**
ユーザー向け文言が data 層にあるためローカライズできず、層の責務としても逆転しています。また生の `Exception` / `IllegalStateException` しか投げていないため、呼び出し側で「通信エラー」「データ不正」「データ無し」を区別できません。P1-3 でエラーを正しく流すようにすると、この区別が必要になります。

**推奨対応**
`domain` に例外型（`NetworkError` / `EmptyData` など）を定義し、data 層はそれを投げる。文言は UI 層の `strings.xml` に置く。

**影響範囲**
`domain/.../common/`（新規）、`data/src/main/kotlin/.../repository/`、各 Screen

---

## P3-14. 依存ライブラリのバージョンが停滞している

**現状**
AGP 9.0.0 / Gradle 9.1.0 / Kotlin 2.3.10 と最新のビルド環境に対し、ライブラリ側が置いていかれています。

| ライブラリ | 現在 | 備考 |
|---|---|---|
| `kotlinx-coroutines` | 1.7.3 | Kotlin 1.9 世代。Kotlin 2.3.10 に対して極端に古い |
| Koin | 3.4.0 | 2023 年系。4.x が現行 |
| Compose | 1.7.5 / 1.7.7 / material3 1.3.1 | **BOM 未使用でバージョンが不揃い** |
| Firebase BOM | 33.5.1 | 1 年以上前。かつ `-ktx` アーティファクト（`firebase-database-ktx`、`firebase-analytics-ktx`）は**非推奨**（KTX は本体へ統合済み） |
| Coil | 2.6.0 | 3 系が現行 |
| espresso-core | **3.1.0（2018 年）** | `androidApp/build.gradle.kts:81` に catalog 外でハードコード。`com.android.support` の exclude も AndroidX 時代には無意味 |

あわせて次の残骸があります。

- `gradle/libs.versions.toml:14` `compose-compiler-version = "1.5.14"` — **参照 0 件**。Kotlin 2.x の Compose Compiler Gradle Plugin 方式に移行済みのため完全な死に設定
- `build.gradle.kts:1-8` のルート `allprojects { repositories }` — Gradle 7 以降は `settings.gradle.kts` の `dependencyResolutionManagement` が推奨。`maven("https://maven.google.com")` は `google()` と重複、`jitpack.io` は使用実績なし
- `androidApp/proguard-rules.pro` の `-dontwarn okio.**` — 対応依存が見当たらない。ヘッダーコメントに旧開発者の絶対パスも残存
- `libs.versions.toml` に `[plugins]` セクションが無く、プラグインは build-logic の `dependencies` に入れる旧方式

**推奨対応**
Compose BOM の導入を優先（バージョン不揃いの解消も同時に済む）。coroutines と Firebase `-ktx` の置き換えを次に。Koin 4 と Coil 3 はメジャー更新なので個別に。P2-7 の Dependabot を入れてから進めると追随しやすい。

**影響範囲**
`gradle/libs.versions.toml`、各 `build.gradle.kts`、`build.gradle.kts`（ルート）、`settings.gradle.kts`

---

## P3-15. AndroidManifest に死んだ intent-filter と未使用パーミッションがある

**現状**
```xml
<!-- androidApp/src/main/AndroidManifest.xml:34-36 -->
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
</intent-filter>
```
`category` も `data` も無く、`MainActivity` は `exported="true"` です。また `:7` の `ACCESS_NETWORK_STATE` はアプリコードから未使用（`ConnectivityManager` の参照 0 件）。`allowBackup` / `dataExtractionRules` / `localeConfig` / `enableOnBackInvokedCallback` はいずれも未指定で、**Predictive Back に未対応**です。

**なぜ問題か**
この intent-filter はディープリンクとして機能せず、意図が読み取れないまま `exported="true"` と併存しています。`ACCESS_NETWORK_STATE` は Firebase 由来の可能性がありますが、自前宣言なら棚卸し対象です。

**推奨対応**
死んだ intent-filter を削除。`ACCESS_NETWORK_STATE` の要否を確認。Predictive Back 対応は P0-2（targetSdk 36）と同じ作業単位で検討する。

**影響範囲**
`androidApp/src/main/AndroidManifest.xml`

---

## P3-16. `google-services.json` が 5 箇所に重複し、1 つは完全に未使用

**現状**
同一内容（同じ md5、`project_id: yaima-funi`）のファイルが 5 箇所にあります。

- `androidApp/google-services.json`
- `androidApp/src/main/google-services.json`
- `androidApp/src/debug/google-services.json`
- `androidApp/src/mock/google-services.json`
- `data/google-services.json`

`data/google-services.json` は `yaeyama.android.library` が `com.google.gms.google-services` プラグインを適用していないため**完全に未使用**ですが、CI と `docs/google-services-secret-setup.md` が必須ファイル扱いで復元しています。

**なぜ問題か**
CI の復元ステップ（`.github/workflows/android_build_check.yaml:26-33`）は `androidApp/src/mock/google-services.json` を作らないため、**CI で `assembleMock` を実行すると失敗します**。現在 CI が mock を組んでいないため露見していないだけです。

**推奨対応**
必要な配置を整理して重複を削減し、CI の復元ステップと `docs/google-services-secret-setup.md` を実態に合わせる。mock を CI で検証するなら復元先に追加する。

**影響範囲**
`androidApp/`、`data/`、`.github/workflows/android_build_check.yaml`、`docs/google-services-secret-setup.md`

---

## P3-17. 画面状態が保存されず、ナビゲーション基盤が無い

**現状**
`rememberSaveable` の使用は **0 件**です。`ui/main/compose/MainScreen.kt:28` の `selectedTab` は `remember { mutableStateOf(...) }` のため、プロセス再生成で失われます。タブ切替は `:41-68` の `when` 分岐で、バックスタックも状態保存もありません（タブを離れると Composable が破棄され、戻るたびに `DashBoardScreen.kt:30` の `LaunchedEffect(Unit)` が再フェッチします）。

画面遷移は素の Activity + Intent で、パラメータは生の `putExtra` 文字列キー（`PortStatusDetailActivity.kt:17-18`、`TyphoonDetailActivity.kt:16`）。Navigation Compose は未導入です。`ui/dashboard/DashBoardScreen.kt:35-53` は結果が不要なのに `rememberLauncherForActivityResult` を使っています。

**なぜ問題か**
Navigation Component を外した後、代替のナビゲーション基盤を入れないまま Activity + Intent に戻った形です。型安全性が失われ、状態保存も手当てされていません。

**推奨対応**
Navigation Compose の導入か、Activity を `MainActivity` に統合する方針を決める。短期的には `rememberSaveable` への置き換えだけでも状態消失は防げます。

**影響範囲**
`ui/main/compose/MainScreen.kt`、3 Activity、`ui/dashboard/DashBoardScreen.kt`

---

## P3-18. `modifier` の扱いが Compose の慣習から外れている

**現状**

- `ui/main/compose/MainScaffold.kt:41,53` — 引数の `modifier` を外側 `Box` と内側 `Scaffold` の**両方に適用**（padding / size が二重適用される）
- `ui/typhoon/list/compose/TyphoonListScreen.kt:39,43` — `viewModel: TyphoonListViewModel? = null` と nullable デフォルトにし、`viewModel?.let { }` で包んでいる。**null なら画面が丸ごと無描画**になる。Preview のために本番シグネチャを歪めた形
- `MainScreen.kt:26`、`YaimafuniScaffold`、`TyphoonListTopAppBar`、`PreviewBox` — `modifier` 引数を受け取っているが適用していない

**推奨対応**
`modifier` は最外側の 1 要素にのみ適用する。Preview は `@PreviewParameter` かステートレスな内部 Composable を使い、本番シグネチャから nullable を外す（`WeatherScreen.kt` のようにステートフル / ステートレスを分ける形が既にプロジェクト内にあります）。

**影響範囲**
`ui/main/compose/MainScaffold.kt`、`ui/typhoon/list/compose/TyphoonListScreen.kt`、`ui/common/`

---

## P3-19. 非推奨 API の使用

**現状**
`ui/common/compose/BackNavigationTopAppBar.kt:4,39` の `Icons.Default.ArrowBack` は非推奨です。

**推奨対応**
`Icons.AutoMirrored.Filled.ArrowBack` へ置換する。Manifest が `supportsRtl="true"` なので RTL 対応も兼ねます。

**影響範囲**
`ui/common/compose/BackNavigationTopAppBar.kt`

---

## P3-20. 色定義が 3 箇所で管理されている

**現状**
色が Compose の `ui/theme/Color.kt`、`res/values/colors.xml`、各 Composable のハードコードに散在しています。

| 重複 | 状況 |
|---|---|
| `Color.kt:20` `SeeBlue = 0xFF007AB7` ⇔ `colors.xml` `see_blue #007AB7` | **`SeeBlue` は参照 0**。実際に使われているのは `MainBottomNavigation.kt:28` の `colorResource(R.color.see_blue)` |
| `Color.kt:16` `TableHeaderColor = 0xFF303F9F` ⇔ `colors.xml` `colorPrimaryDark #303F9F` | 同じ値の二重定義 |

Compose 内で `colorResource`（XML 由来）を使う箇所が 6 ファイル、`Color.White` の直書きが 15 箇所以上あります。セマンティックトークン化されているのは `StatusColor`（`Color.kt:10-14`）のみです。

**推奨対応**
P3-1 の M3 移行と同じ作業単位で、色をテーマのトークンに集約する。XML 側は Manifest テーマに必要な最小限だけ残す。

**影響範囲**
`ui/theme/Color.kt`、`res/values/colors.xml`、色を直接指定している全 Composable

---

## P3-21. ステータスコードの typo 対応が UI 層に漏れている

**現状**
```kotlin
// ui/common/StatusBackgroundColorHelper.kt:9-14
fun Status.getStatusBackgroundColor() = when (this.code) {
    "nomal", "normal" -> StatusColor.Normal
    "cation" -> StatusColor.Cation
    "cancel" -> StatusColor.Cancel
    else -> StatusColor.Cation          // 未知ステータスを警告色にフォールバック
}
```

**なぜ問題か**
Firebase から来る値の表記揺れ（`nomal` / `normal`）を UI 層が吸収しており、マッピングの責務が層をまたいでいます。`else` で未知のステータスを警告色にする silent fallback も、新しいステータスコードが追加されたときに気づけません。

> **注意**: `nomal` / `cation` は **Firebase から来る外部契約値**で、`domain/.../weather/Temperature.hight` や `Table.windBlow` も Firebase のキー名に紐づいています。**単純なリネームはデシリアライズを壊します。** マッピングを data 層に寄せる（P3-12 の DTO 層導入）か、`@PropertyName` を使って Kotlin 側の名前だけ直すこと。

**推奨対応**
コード → 状態の変換を data 層または domain の enum に移し、未知の値はログを残したうえで明示的に扱う。

**影響範囲**
`ui/common/StatusBackgroundColorHelper.kt`、`domain/.../statusdetail/Status.kt`、`data/src/main/kotlin/`

---

## P3-22. オフライン対応が未実装

**現状**
Firebase の `setPersistenceEnabled` / `keepSynced` の呼び出しが **0 件**、Room / DataStore への依存もありません（`data/build.gradle.kts` は koin-core / firebase-database / coroutines / timber / `:domain` のみ）。`SharedPreferences` は In-App Review の起動回数カウントにのみ使われています（`MainActivity.kt:75`）。

**なぜ問題か**
`CLAUDE.md` が要件として掲げる「低通信環境での動作」「オフライン対応」が実態と乖離しています。離島航路の利用者が港や船上の電波が弱い場所で使う前提なら、検討する価値があります。

**推奨対応**
まず `FirebaseDatabase.setPersistenceEnabled(true)` を検討する（`data/src/release/.../DataModule.kt:21` の登録時に 1 行）。これだけで直近データのオフライン表示が効きます。本格的なキャッシュ戦略は別途設計が必要です。

**影響範囲**
`data/src/*/kotlin/.../di/DataModule.kt`

---


---

# ドキュメントの整合

コード以上に乖離が進んでいるのがドキュメントです。`CLAUDE.md` は AI に読ませる前提の文書なので、**古い記述はそのまま誤った実装方針として作用します**。

## `CLAUDE.md`

| 箇所 | 記述 | 実態 |
|---|---|---|
| `:21` | 「Jetpack Compose - モダンUIツールキット（従来のViewと混在）」 | **Compose 100%**。XML レイアウトは 0 件 |
| `:24` | 「Navigation Component - Safe Argsを使用したFragment基盤のナビゲーション」 | Navigation Component も Fragment も**全廃済み**。遷移は Activity + Intent |
| `:25` | 「Data Binding & View Binding - UIバインディングパターン」 | どちらも**未使用** |
| `:168` | 「UI設計 - Jetpack Compose/従来Viewでの実装方針」 | 同上 |
| `:104-105` | 「Kotlin: 1.9.24 / Compose Compiler: 1.5.14」 | **Kotlin 2.3.10**。Compose Compiler はプラグイン方式に移行済み。AGP 9.0.0 / Gradle 9.1.0 への言及も無い |
| `:96` 付近 | ビルドバリアントの説明が Debug / Release のみ | **`mock` ビルドタイプ**が androidApp・data の両方に実在（`FakeReviewManager` と Fake Repository を使う） |
| `:119` | 本番 `google-services.json` は `androidApp/src/main/` | 実際は `androidApp/`（モジュールルート）が主。5 箇所に重複（P3-16） |
| `:32` 付近 | 「オフライン対応」「低通信環境での動作」 | **未実装**（P3-22 参照） |

また `CLAUDE.md` のコミット規約（`:184-192`）と `.cursor/rules/git-commit.mdc` で**同じルールが二重管理**されています。

**推奨対応**: `.cursor/rules/` を単一の情報源とし、`CLAUDE.md` からは参照だけにする（既に `:194` で「`.cursor/rules/git-commit.mdc` に従うこと」と書かれているので、重複部分を削るだけで済みます）。

## `docs/module-architecture-review.md`

指摘 1〜3 は解消済みです。

| 指摘 | 状態 |
|---|---|
| 1. Repository interface が `data` にある | **解消済み**（703086e で `domain/repository/` へ移設） |
| 2. ViewModel が `single` + `KoinComponent` 自己注入 | **解消済み**（2b86a08 で `viewModel { }` + コンストラクタ注入へ）。ただし Fake 群に `KoinComponent` の残骸あり（P3-6） |
| 3. debug の Fake が実際には使われていない | **解消済み**（511d905 で `mock` バリアントを新設し Fake を `data/src/mock/` へ移動） |
| 4. UseCase が `data` にある | **未解消**。さらにデッドコード化（P3-4） |
| 5. `tyhoon` の typo | **一部解消**。パッケージ宣言は修正済みだがディレクトリ名は typo のまま（P3-10） |

Fake のパスも `data/src/debug/` と記載されていますが、実際は `data/src/mock/` です。

**推奨対応**: 冒頭に「2026-09 時点で内容が古い。後継は本提案書」の注記を追記する（本提案書の作成時に対応済み）。

---

# 実装順序の依存関係

個別に着手すると壊れる組み合わせがあります。

```
P0-1 (Security Rules 確認) ── 独立。他のどれにも依存せず、最初に単独で実施できる

P0-6 (SDK の二重管理を解消)
  └─> P0-2 (targetSdk 36) ─┬─> P0-3 (edge-to-edge)   ← 同一の作業単位
                            └─> P3-15 (Predictive Back)

P3-12 (DTO 層) ──> P0-5 (R8 有効化)   ← Firebase のリフレクションに keep ルールが必要
                    ↑
              data/proguard-rules.pro の作成が前提（AGP 9 の failOnMissingFiles）

P1-1 (UiState.Loading) ─> P1-2 (Weather のエラー表示)
P1-3 (リポジトリのエラー握り潰し) ─┬─> P1-4 (ダッシュボードのエラー UI)
                                   └─> P1-5 (運行詳細のエラー UI)
                                   ※ P1-3 を直さないとエラー分岐は発火しない

P1-9 (collectAsStateWithLifecycle + stateIn 化) ≈ P3-8 (UiState パターン統一)   ← 同一の作業単位

P3-1 (M3 移行) ≈ P3-20 (色の集約)   ← 同一の作業単位
P3-3 (未使用依存の削除) ── compose-theme-adapter を外すなら material を明示追加

P2-1 (テスト基盤) ── P1 の修正と並走させると回帰テストとして機能する
P2-7 (Dependabot) ─> P3-14 (バージョン更新)   ← 先に入れると追随が楽
```

## 推奨する着手順

| 段階 | 内容 | 理由 |
|---|---|---|
| 0 | **P0-1 セキュリティルールの確認** | Console を見るだけで数分。最悪ケースの影響が全項目中で最大なので、他に着手する前に潰す |
| 1 | **既存 Issue の棚卸し** + **仕分け**（後述の付録） | 全体像を固定する。ここを飛ばすと Issue #154 と同じ構造になる |
| 2 | **P2-4〜P2-6 CI 修復** | 単一ファイルの変更で小さくリスクが低い。以降すべての変更の検証が楽になる |
| 3 | **P0-6 → P0-2 + P0-3** | ストア更新の再開。外部期限があるため優先度が固定される |
| 4 | **P2-1 テスト基盤** + **P1-1〜P1-3** | 回帰テストが効くようになる。Issue #122（2023年から未解決）がここで解消する |
| 5 | **P1-4〜P1-10** | 残りの機能バグ。テストを書きながら進める |
| 6 | **P0-4 署名** | 現行のリリース手順を確認したうえで整備する |
| 7 | **P3-3, P3-4, P3-5, P3-6 の削除系** | 低リスク。R8 有効化前に消しておくとビルド確認が単純になる |
| 8 | **P0-5 R8** + **P3-12 DTO 層** | keep ルールの検討が必要なため後段 |
| 9 | **P3-2 台風一覧の復活** + **P3-1 M3 移行** + **P3-20 色** | UI 系のまとまった改修 |
| 10 | **P2-7 Dependabot** → **P3-14 バージョン更新** | 継続的な追随体制へ |

各段階は 1 PR 相当を想定しています（直近の PR は +20〜180 行で推移しているため、その粒度に合わせています）。

---

# 付録 A: 推奨する仕分け

**46 件すべてをやる前提にしないこと**が、この提案書が Issue #154 と同じ結末をたどらないための条件です。以下は「たたき台」で、最終判断は開発者が行います。

| 区分 | 件数 | 意味 |
|---|---|---|
| **やる** | 38 | 費用対効果が明確 |
| **見送り** | 3 | 個人開発の規模ではコストに見合わない |
| **保留** | 5 | 方針決定を伴うため、決めてから着手 |

（番号付きの 46 項目に対する内訳。項目内の細かい判断は各表の補足に記載）

## やる（38件）

| 項目 | 補足 |
|---|---|
| P0-1〜P0-6 | 全件。P0-4（署名）のみ現行手順の確認が前提 |
| P1-1〜P1-10 | 全件。P1-1〜P1-3 は修正が小さく効果が大きいので最初に |
| P2-1, P2-2 | テスト基盤と既存テストの整備 |
| P2-3 | **ktlint のみ**。`.editorconfig` が既にあるので導入コストが低い。detekt は見送り |
| P2-4〜P2-8 | CI とビルド設定。P2-8 の `nonTransitiveRClass` は別コミットに分ける |
| P3-1, P3-20 | M3 移行と色の集約（同一の作業単位） |
| P3-2 | 台風一覧のリッチ実装の復活 |
| P3-3〜P3-6 | 削除系。低リスクで効果が見えやすい |
| P3-8 | UiState パターン統一（P1-9 と同時） |
| P3-10, P3-15, P3-16, P3-18, P3-19 | いずれも小さい |
| P3-13 | ドメイン例外型。P1-3 と同時が自然 |
| P3-14 | Compose BOM を優先。Koin 4 / Coil 3 は個別に |

## 見送り（3件）

| 項目 | 理由 |
|---|---|
| P3-7 重複コードの共通化 | 3 箇所程度の重複は許容範囲。**ただし Scaffold ラッパー2種の統合だけは、使い分けが崩壊しているので直す** |
| P3-9 命名・構造の統一 | 影響範囲に対して得るものが少ない。**ただし `PortStatusDetailScreen` の同名2ファイル問題（TODO 済み）だけは直す** |
| P3-11 日本語文字列のリソース化 | 多言語対応の予定が無ければ費用対効果が低い。**ただし `TimeTableList.kt:68,96-99` の「本番コードのデフォルト引数に Preview 用ダミー」だけは実害があるので直す** |

あわせて、P2-3 の静的解析は **ktlint のみ採用し detekt は見送る**方針とします（項目自体は「やる」）。

## 保留 — 方針決定が必要（5件）

| 項目 | 決めること |
|---|---|
| P1-11 航路ルールのハードコード | 動作はしている。`Company` の設計をどこまで一般化するか |
| P3-12 DTO 層の導入 | domain を Firebase スキーマから切り離すか。P0-5（R8）の keep ルールと絡む |
| P3-17 ナビゲーション基盤 | Navigation Compose を入れるか、Activity を統合するか、現状維持か。**`rememberSaveable` への置き換えだけは先行して実施可** |
| P3-21 ステータスコードのマッピング | P3-12 とセット |
| P3-22 オフライン対応 | 本格的なキャッシュ戦略は別設計。**`setPersistenceEnabled(true)` の 1 行だけ先行して試す価値はある** |

## 項目内で別途決めること

上の 46 項目とは別に、実施時に方針を決める必要があるものです。

| 判断 | 関連項目 | 決めること |
|---|---|---|
| リリース署名の方式 | P0-4 | Play App Signing の現状と、アップロード鍵の管理方法。**P0-4 に着手する前に確認が必要** |
| domain のパッケージ名（`com.yaeyama_liner_checker`） | P3-9 | 変更の影響が広い。やるなら単独コミットで |
| ダークモード対応 | P3-1 | 現状は明示的に非対応（`Theme.kt:31`）。M3 移行時に方針を決める |

---

# 付録 B: 既存 Issue との対応

調査時点で open な Issue は 20 件あり、うち確認できた範囲で次の対応が必要です。

## クローズ候補（対応済み・陳腐化）

| Issue | 状態 |
|---|---|
| #134 Compose Compiler Gradle プラグインに移行する | **対応済み**（Kotlin 2.3.10 + `org.jetbrains.kotlin.plugin.compose`） |
| #127 DataBindingExt.kt を削除する | **対応済み**（DataBinding 全廃。該当ファイルも存在しない） |
| #129 Fragment Compose Artifact を使う | **陳腐化**（Fragment 全廃により前提が消滅） |

## 本提案書へ統合すべきもの

| Issue | 対応する項目 |
|---|---|
| #154 🔴 緊急対応タスク（2025-09-16） | 大部分が本提案書と重複。**ただし「Firebase Security Rules の実装」は本提案書に無かった項目**で、P0-1 として取り込んだ |
| #122 通信エラーの UI を改善したい（2023-07-25） | P1-2 / P1-3 / P1-4。**3 年間解決しなかった原因が P1-3**（リポジトリが例外を空リストに変換していたため、UI 側をいくら直しても発火しなかった） |
| #148 Compose Bom を使う | P3-14 |

Issue #154 は 100 個近いチェックボックスを 1 つの Issue に収めた 12 週間計画で、結果としてほとんど実行されませんでした。粒度が「タスク」ではなく「テーマ」だったこと、全件実施を前提にしていたことが原因と考えられます。本提案書では付録 A の仕分けと 1 PR 単位の分割でこれを避けます。

---

## 参考: 調査で確認した「問題ない」点

改善点ばかり並べましたが、以下は良好な状態です。

- **ファイル分割の粒度** — 300 行超のファイルは 0 件（最大 231 行、しかもそれはデッドコード）
- **Firebase リスナーの解除** — `FirebaseExt.valueEvents` は `awaitClose { removeEventListener }` で正しく実装されており、古典的なリークは無い（問題は収集側の寿命管理＝P1-9）
- **Gradle wrapper の検証** — `distributionSha256Sum` が設定されている
- **DI** — ViewModel は `viewModel { }` + コンストラクタ注入に統一済み。Repository interface も `domain` にあり、mock バリアントで差し替えが機能している
- **LiveData の全廃** — Flow / StateFlow に完全移行済み
- **`PortStatusDetailViewModel`** — `statusDetailJob?.cancel()` による多重起動ガードがあり、重複購読は防げている
