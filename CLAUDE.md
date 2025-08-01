# CLAUDE.md

このファイルは、このリポジトリでコードを扱う際のClaude Code (claude.ai/code) への指針を提供します。

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