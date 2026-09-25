# コードレビューガイドライン

Yaimafuni-Android のコードを書く・レビューするときの基準です。
迷ったら [Kotlin コーディング規約](https://kotlinlang.org/docs/coding-conventions.html) と
[Compose API ガイドライン](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md) に従います。

## 命名規則

### 変数・引数

- 1 文字の変数名（`p`、`v` など）は使わない。何を表すかが分かる名前にする
  - NG: `ports.forEach { p -> ... }` → OK: `ports.forEach { port -> ... }`
- 暗黙の `it` は、ラムダが 1 行で対象が自明なときだけ使う
  - 複数行のラムダ、ネストしたラムダ、型が分かりにくいときは名前を付ける
- 例外を受け取る変数は慣例として `e` でよい（`catch { e -> ... }` / `catch (e: Exception)`）
- コレクションは複数形にする（`typhoons`、`dailyWeathers`）。`data` / `values` / `list` / `dummy` のような中身の分からない名前は避ける
- 表しているものと名前をずらさない（会社名を渡す引数を `portName` にしない、など）
- ハンガリアン記法・接頭辞（`m_`、`mFoo`）は使わない

### クラス・関数

| 対象 | 規則 | 例 |
|------|------|-----|
| Repository インターフェース | `XxxRepository`（domain） | `TopStatusRepository` |
| Repository 実装 | `XxxRepositoryImpl`（data） | `TopStatusRepositoryImpl` |
| Fake（mock ビルド用） | `FakeXxxRepository`（data/src/mock） | `FakeTopStatusRepository` |
| Repository のメソッド | `fetchXxx()`、`Flow` を返す | `fetchTopStatuses()` |
| UseCase | 動詞 + 名詞、`operator fun invoke()` | `GetTopStatuses` |
| ViewModel | `XxxViewModel`、公開する状態は `uiState: StateFlow<...>` | `DashBoardViewModel` |
| 再試行 | `retry()` | |
| 画面の Composable | 状態を受け取るものは `XxxScreen`、ViewModel を受け取るものは `XxxScreenRoot` または `XxxScreen` | `DashBoardScreenRoot` |
| Preview | `XxxPreview`（`private`） | `DashBoardScreenPreview` |

### パッケージ

- すべて `com.yaeyama.linerchecker` 配下。domain は `com.yaeyama.linerchecker.domain`
- パッケージ名は小文字のみ（アンダースコアを使わない。例: `timetable`）

### リソース

- `snake_case`。画面・機能の接頭辞を付ける（`dashboard_fetch_failed`、`weather_not_found`）
- 画面共通のものは接頭辞なし（`retry`、`error_retry_hint`）

### 変更できない名前

Firebase Realtime Database のキーと一致させる必要があるため、以下は綴りが不自然でも変更しないこと。
変更するとデシリアライズで値が取れなくなります。

- `Temperature.hight`（最高気温）
- `Table.windBlow`（風向き）
- ステータスコードの文字列 `"cation"` / `"nomal"`（`OperationStatus` で吸収している）

## レビュー観点チェックリスト

### アーキテクチャ

- [ ] 依存の向きが `androidApp → domain ← data` になっているか（domain が Android / Firebase / Koin に依存していないか）
- [ ] ビジネスルール（ステータスの判定、航路の運航会社、並び順など）が UI ではなく domain にあるか
- [ ] ViewModel が Repository ではなく UseCase に依存しているか

### 状態管理・エラー処理

- [ ] ViewModel の状態は `LoadState` と `stateInWhileSubscribed` を使った宣言的な形になっているか
- [ ] Repository は失敗時に `DataException`（`DataNotFoundException` / `DataFetchException` / `DataParseException`）で Flow を終了しているか
- [ ] 例外を握りつぶしていないか。`stateIn` の上流で未捕捉の例外が起きないか
- [ ] エラー時に利用者が復帰できる（再試行できる）か

### UI

- [ ] 表示文言は `strings.xml` にあるか（ハードコードしていないか）
- [ ] ローディング・エラーは共通コンポーネント（`LoadingContent` / `ErrorContent`）を使っているか
- [ ] 画像・アイコンに適切な `contentDescription` があるか（装飾目的なら `null`）
- [ ] 構成変更で失われると困る UI 状態は `rememberSaveable` になっているか

### テスト

- [ ] ロジックの変更にユニットテストがあるか（ViewModel は MockK + Turbine、Repository は Firebase をモック）
- [ ] UI の状態ごとの表示を変えたら Compose UI テスト（Robolectric）を更新したか
- [ ] Koin の定義を追加・変更したら `KoinModuleTest` が通るか

### リリース

- [ ] Firebase でデシリアライズする domain モデルのパッケージを変えた場合、`proguard-rules.pro` の keep ルールを更新したか
- [ ] デバッグ用のコード・ダミーデータが `src/main` に入っていないか（mock / debug ソースセットに置く）
