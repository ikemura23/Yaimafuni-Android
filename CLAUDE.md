# CLAUDE.md

このファイルは、このリポジトリでコードを扱う際のClaude Code (claude.ai/code) への指針を提供します。

## Claude AIの基本指針
- **言語**: AIの返答は日本語で行うこと

## プロジェクト概要

Yaimafuni-Androidは、沖縄県八重山諸島の船舶運航情報を提供するAndroidアプリケーションです。このアプリは[Google Play](https://play.google.com/store/apps/details?id=com.banbara.yaeyama.liner.checker&hl=ja&gl=US)で公開されています。

## アーキテクチャ

このプロジェクトはクリーンアーキテクチャの原則に従ったマルチモジュールAndroidアーキテクチャを採用しています：

- **`:androidApp`** - UIコンポーネント、Activity、Fragment、Jetpack Composeスクリーンを含むメインのAndroidアプリケーションモジュール
- **`:data`** - リポジトリ、Firebase連携、データソースを含むデータレイヤーモジュール
- **`:domain`** - ビジネスモデルとエンティティを含むドメインレイヤーモジュール（純KotlinまたはJava）

### 主要技術
- **Jetpack Compose** - モダンUIツールキット（従来のViewと混在）
- **Firebase** - Realtime Database、Analytics、Crashlytics
- **Koin** - 依存性注入フレームワーク
- **Navigation Component** - Safe Argsを使用したFragment基盤のナビゲーション
- **Data Binding & View Binding** - UIバインディングパターン
- **Coroutines** - 非同期プログラミング
- **Timber** - ログ出力

### パッケージ構造
メインアプリケーションコードは`com.yaeyama.linerchecker`配下に整理されています：
- `ui/` - 機能別に整理されたUIコンポーネント（dashboard、portstatusdetail、typhoon、weather）
- `di/` - 依存性注入モジュール
- `utils/` - ユーティリティクラス

## 一般的な開発コマンド

### ビルドとテスト
```bash
# 全モジュールのビルド
./gradlew build

# デバッグAPKのビルド
./gradlew assembleDebug

# リリースバンドル（AAB）のビルド
./gradlew bundleRelease

# ユニットテストの実行
./gradlew test

# 接続テストの実行（デバイス/エミュレータが必要）
./gradlew connectedAndroidTest

# クリーンビルド
./gradlew clean
```

### インストールとデバッグ
```bash
# デバッグビルドのインストール
./gradlew installDebug

# リリースビルドのインストール
./gradlew installRelease

# 全ビルドのアンインストール
./gradlew uninstallAll
```

### コード品質
```bash
# Lint解析の実行
./gradlew lint

# Lint問題の自動修正
./gradlew lintFix

# 依存関係の確認
./gradlew dependencies
```

### 特定モジュールのテスト
```bash
# 特定モジュールのテスト
./gradlew :domain:test
./gradlew :data:test
./gradlew :androidApp:test

# デバッグビルドのみのテスト実行
./gradlew testDebugUnitTest
```

## 開発設定

### ビルドバリアント
- **Debug** - `.debug`サフィックス付きの開発ビルド、Firebase Crashlytics無効、追加デバッグツール
- **Release** - 難読化無効の本番ビルド、デバッグキーストアで署名

### 依存関係管理
依存関係は`gradle/libs.versions.toml`でバージョンカタログを使用して一元管理されています。主要バージョン制約：
- Compile SDK: 35
- Min SDK: 24
- Target SDK: 35
- Kotlin: 1.9.24
- Compose Compiler: 1.5.14
- JVM Toolchain: 17

### 依存性注入
アプリケーションは以下で定義されたモジュールでKoinを使用しています：
- `androidApp/src/*/java/com/yaeyama/linerchecker/di/AppModule.kt` - アプリレベルの依存関係
- `data/src/*/kotlin/com/yaeyama/linerchecker/di/DataModule.kt` - データレイヤーの依存関係
- `androidApp/src/main/java/com/yaeyama/linerchecker/di/ViewModelModule.kt` - ViewModelの依存関係

デバッグ/リリースビルドに対して、それぞれのソースセットで異なる実装が提供されています。

## Firebase設定

このプロジェクトはFirebaseサービスと統合しており、`google-services.json`ファイルが必要です：
- 本番: `androidApp/src/main/google-services.json`
- デバッグ: `androidApp/src/debug/google-services.json`
- データモジュール: `data/google-services.json`

Firebase Crashlyticsはデバッグビルドでは無効になっており、開発時はマッピングファイルのアップロードも無効化されています。

## AI主導開発ワークフロー

新機能開発、リファクタリング、技術的改善を行う際のAI自動化ワークフローです。

### 基本フロー
```
1. 開発者アイデア入力 → 2. AI要件定義生成 → 3. 開発者レビュー
   ↓
4. AI設計ドキュメント生成 → 5. 開発者レビュー → 6. AI実装計画生成
   ↓
7. 開発者レビュー → 8. 実装開始
```

### 1. アイデア入力段階
開発者がプロンプトで以下を入力：
- **新機能**: やりたいこと、解決したい課題、想定するユーザー体験
- **リファクタリング**: 改善したいコード、技術的負債、パフォーマンス問題
- **技術改善**: ライブラリ更新、アーキテクチャ改善、コード品質向上
- 技術的な制約や要望

### 2. AI自動要件定義 (.ai/requirements.md)
AIが作業種別に応じて自動生成する要件定義：

**新機能の場合:**
- **目的と背景** - 船舶利用者にとってのメリット、運航会社の課題解決
- **対象ユーザー** - 観光客、地元住民、ビジネス利用者の具体的なニーズ
- **機能要件** - 対象航路（石垣-竹富、石垣-西表等）、データソース（Firebase）
- **UI/UX要件** - 運航状況の視認性、天候情報との連携、オフライン対応

**リファクタリングの場合:**
- **現状の問題** - 技術的負債、コードの複雑性、保守性の問題
- **改善目標** - コード品質向上、パフォーマンス改善、可読性向上
- **影響範囲** - 対象モジュール（domain/data/androidApp）、関連クラス
- **互換性** - 既存機能への影響、APIの変更有無

**共通項目:**
- **パフォーマンス** - リアルタイム更新、低通信環境での動作
- **受け入れ条件** - 動作確認方法、品質基準

### 3. AI自動設計 (.ai/design.md)
要件レビュー後、AIが自動生成する技術設計：
- **アーキテクチャ設計** - 既存のマルチモジュール構成への統合方法
- **データモデル設計** - Firebase Realtime Databaseスキーマ、Kotlinエンティティ
- **UI設計** - Jetpack Compose/従来Viewでの実装方針
- **依存性注入** - Koinモジュールへの追加方法
- **技術選択** - プロジェクトの既存技術スタック活用

### 4. AI自動実装計画 (.ai/tasks.md)
設計レビュー後、AIが自動生成する実装タスク：
- **タスク分解** - domain/data/androidAppモジュール別のタスク
- **優先順位と依存関係** - クリーンアーキテクチャに基づく実装順序
- **具体的な変更ファイル** - 修正・追加するファイルパスとクラス
- **テスト戦略** - ユニットテスト、UIテストの方針
- **検証方法** - ./gradlew build, lint, testによる品質確認

### 5. 実装実行
レビュー完了後、AIが実装を自動実行：
- ドメインモデル → データレイヤー → UIレイヤーの順で実装
- 各段階でビルド・テスト実行による品質確認
- 既存コードとの整合性チェック
- **tasks.mdチェックボックス更新**: 各タスク完了時に該当チェックボックスを✅に更新
- **適切な粒度でのGitコミット**: 機能単位・モジュール単位で論理的にコミット

#### コミット戦略
- **ドメインレイヤー完了時**: `feat: [機能名]のドメインモデルとユースケースを追加`
- **データレイヤー完了時**: `feat: [機能名]のリポジトリとデータソースを追加`  
- **UIレイヤー完了時**: `feat: [機能名]のUIコンポーネントを追加`
- **リファクタリング単位**: `refactor: [対象クラス/機能]の[改善内容]を改善`
- **テスト追加時**: `test: [対象機能]のユニットテストを追加`
- **設定・依存関係**: `chore: [ライブラリ名]の設定を更新`

#### コミットメッセージプレフィックスガイド
- `feat:` - 新機能の追加
- `fix:` - バグ修正
- `docs:` - ドキュメントのみの変更
- `style:` - コードの意味に影響しない変更（空白、フォーマット、セミコロンなど）
- `refactor:` - バグ修正や機能追加ではないコードの変更
- `test:` - テストの追加や修正
- `chore:` - ビルドプロセスや補助ツールの変更
- `ci:` - CI/CD設定の変更
- `revert:` - 以前のコミットの取り消し

各コミットは動作可能な状態を保ち、ビルドエラーが発生しないことを確認してからコミットします。

### 6. 完了後の処理
実装完了後に実行する最終確認と自動PR作成：

#### 最終テスト・品質確認
- **最終テスト実行**: `./gradlew build`, `./gradlew lint`, `./gradlew test`
- **動作確認**: 実機/エミュレータでの機能テスト (エンジニアが実施するためAIは実行しない)
- **ドキュメント更新**: 必要に応じてREADMEやコメントの更新

#### 自動Pull Request作成
全ての品質確認が完了したら、AIが自動でPull Requestを作成：

**PRタイトル生成ルール:**
- **新機能**: `feat: [機能概要]を追加`
- **リファクタリング**: `refactor: [対象範囲]を改善`
- **バグ修正**: `fix: [問題の概要]を修正`
- **技術改善**: `chore: [改善内容]を更新`

**PR説明の自動生成:**
.github/PULL_REQUEST_TEMPLATE.mdに準拠

**実行コマンド例:**
```bash
# 現在のブランチの変更をプッシュ
git push origin [feature-branch-name]

# Pull Request作成
gh pr create --title "[自動生成タイトル]" --body "[自動生成説明]"
```

- **コードレビュー**: 開発者による最終確認とマージ判断

### エラー対応フロー
実装中にエラーが発生した場合：
1. **ビルドエラー**: 依存関係、構文エラーを修正してリトライ
2. **テストエラー**: 既存テストとの競合を解決
3. **Lintエラー**: コードスタイルを既存規約に合わせる
4. **実行時エラー**: デバッグログを確認して原因調査
5. **修正不可能**: 要件・設計を見直してタスクを再分解

### レビューポイント
各段階で開発者が確認すべき事項：
- **要件レビュー**: ビジネス要求との整合性、ユーザー体験の妥当性
- **設計レビュー**: 技術選択の妥当性、既存アーキテクチャとの整合性
- **タスクレビュー**: 実装範囲の妥当性、工数の現実性
- **実装レビュー**: コード品質、テストカバレッジ、パフォーマンス
