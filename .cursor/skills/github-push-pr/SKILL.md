---
name: github-push-pr
description: 現在ブランチを GitHub に push し、gh で Pull Request を作成する。差分から PR タイトルと本文を生成し、本文は .github/PULL_REQUEST_TEMPLATE.md の見出し構成に沿わせる。ユーザーが push、PR 作成、gh pr create、ブランチをプッシュして PR、Pull Request を開いて と依頼したときに使う。
---

# GitHub に push して Pull Request を作成する

## 前提

- `gh` がインストール済みで `gh auth status` が通ること。
- `origin` が GitHub リモートであること。
- 作業ブランチが `main` / `master` 以外であること（デフォルトブランチ直 push の PR は作らない）。

## 手順

### 1. 状態確認

- `git status`（ブランチ名・未コミットの有無）。
- 未プッシュコミット: `git log @{u}..HEAD --oneline 2>/dev/null` または初回ブランチなら `git log origin/$(base)..HEAD`。
- **既存 PR**: `gh pr view --json number,url 2>/dev/null`。開いていれば URL を伝え、新規作成は不要（ユーザーが更新を求めた場合のみ `gh pr edit` 等）。

### 2. ベースブランチ

デフォルトブランチを取得する（例）:

```bash
gh repo view --json defaultBranchRef --jq .defaultBranchRef.name
```

以降を `BASE` と呼ぶ（多くは `main`）。

### 3. 差分の把握（タイトル・本文の材料）

リモートを最新に近づける:

```bash
git fetch origin
```

比較対象は **マージベース**（PR と同じ見え方）:

```bash
git diff "origin/$BASE...HEAD" --stat
git diff "origin/$BASE...HEAD"
git log "origin/$BASE..HEAD" --oneline
```

差分が大きいときは stat とログを優先し、必要なファイルだけ追加で読む。

### 4. push

```bash
git push -u origin HEAD
```

上流未設定のブランチでは `-u` を付ける。既に上流がある場合は `git push` でよい。

### 5. PR タイトル

形式・種別は **必ず** `.cursor/rules/pr-title.mdc` に従う（未読なら読んでからタイトルを決める）。

### 6. PR 本文

**必ず** リポジトリの `.github/PULL_REQUEST_TEMPLATE.md` と**同じ見出し順**で書く。テンプレ更新時は当該ファイルを読んで整合させる。

埋める内容の指針:

| セクション | 書き方 |
|------------|--------|
| **何を変更しましたか？（概要・3行以内）** | 箇条書き 3 行以内。ファイル列挙や細部は避け、レビュア向け要約。 |
| **なぜ変更しましたか？** | 目的・背景。 |
| **どのように変更しましたか？** | 手順・設計・主要な変更点。差分と対応づくように。 |
| **関連するIssue** | 番号があれば `Closes #123` 等。なければ「なし」または空。 |
| **影響範囲** | モジュール・画面・API・ユーザー影響。 |
| **スクリーンショット** | UI 変更時は添付を促す文言。変更なしなら「UI の変更なし」等。 |
| **備考** | デプロイ注意、フォローアップ、レビュー観点。不要なら「特になし」。 |

複数行本文はエスケープ事故を避けるため **`gh pr create --body-file`** を推奨:

1. 一時ファイルにテンプレ構成どおりの Markdown を書く。
2. `gh pr create --title "type: …" --body-file /path/to/body.md --base "$BASE"`

ドラフトにしたい場合: `--draft`。

### 7. 実行後

- 作成された PR の URL をユーザーに伝える。
- `git push` / `gh pr create` が失敗したらメッセージを要約し、認証・競合・既存 PR など原因を切り分ける。

## 注意

- ユーザーが明示しない限り `--force` は使わない。
- 秘密情報・本番キーが差分に含まれていないか確認する。
- `gh` が使えない環境では GitHub MCP で同等操作が可能か確認するが、**push は通常ローカルの git** が必要。

## 参考

- PR タイトル: [.cursor/rules/pr-title.mdc](../../rules/pr-title.mdc)
- 本文の見出し・欄の意味: [.github/PULL_REQUEST_TEMPLATE.md](../../../.github/PULL_REQUEST_TEMPLATE.md)
