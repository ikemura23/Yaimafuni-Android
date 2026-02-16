# GitHub Actionsで`google-services.json`をSecretsから生成する手順

## 目的
`androidApp/google-services.json`をGit管理せずに、GitHub Actions実行時だけ復元してビルドできるようにします。

## 前提
- CI: GitHub Actions
- ビルド時に必要なファイル:
- `androidApp/google-services.json`
- `androidApp/src/debug/google-services.json`
- `data/google-services.json`

google-service.json の場所を確認するコマンド
```bash
 rg --files -g 'google-services.json'
```

## 1. `google-services.json`をBase64化してSecretに登録する
ローカルで次のコマンドを実行し、出力された文字列を控えます。

```bash
base64 -i ./google-services.json
```


GitHubリポジトリで次を開き、Secretを追加します。
- `Settings` > `Secrets and variables` > `Actions` > `New repository secret`
- Name: `GOOGLE_SERVICES_JSON_B64`
- Secret: Base64化した文字列

## 2. GitHub Actionsで`google-services.json`を復元する
ビルド実行前に、Workflowへ以下のステップを追加します。
必要な3ファイルを同じSecret値から復元します。

```yaml
- name: Restore google-services.json
  env:
    GOOGLE_SERVICES_JSON_B64: ${{ secrets.GOOGLE_SERVICES_JSON_B64 }}
  run: |
    echo "$GOOGLE_SERVICES_JSON_B64" | base64 --decode > androidApp/google-services.json
    echo "$GOOGLE_SERVICES_JSON_B64" | base64 --decode > androidApp/src/debug/google-services.json
    echo "$GOOGLE_SERVICES_JSON_B64" | base64 --decode > data/google-services.json
```

## 3. `.gitignore`を確認する
`google-services.json`を誤ってコミットしないように、以下を含めます。

```gitignore
**/google-services.json
```

## 4. 動作確認
Workflow上で`Restore google-services.json`の後にビルドを実行し、
`File google-services.json is missing` が解消されることを確認します。

動作確認ログ （ PR #160 ）
https://github.com/ikemura23/Yaimafuni-Android/actions/runs/22051171130/job/63709511890?pr=160
