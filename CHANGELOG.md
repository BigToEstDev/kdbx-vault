# Changelog

All notable changes to KDBX Vault are documented in this file - one version for Android and desktop
(`version.json`).

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html). Release candidates are `-rc.N`.

## [Unreleased]

## [1.0.0-rc.2] - 2026-11-15

The first release candidate under the name KDBX Vault, with the core
[1.0.0](https://github.com/BigToEstDev/kdbx-core/releases/tag/v1.0.0). It replaces `1.0.0-rc.1` and does not update
it: the app has a new id (`io.github.bigtoestdev.kdbxvault`), so `1.0.0-rc.1` is to be removed and this one installed.

### Changed

- The name of the app - KDBX Vault (was KeyVault); the repository - `kdbx-vault`, the core - `kdbx-core`.

### Added

- Create a database, or open an existing one with a password, a key file, or both.
- Entries and groups: custom fields, attachments, history, recycle bin.
- Search, password generator and password strength check.
- Unlock with biometrics on Android; the database locks after a period of inactivity.
- Local sync without a network: merge a copy of the database in either direction, import another database, save or
  share a copy.
- The screen "About": the version of the app and of the core, the license, contacts and the source code.

## [1.0.0-rc.1] - 2026-11-14 [YANKED]

Withdrawn: published under the old name KeyVault. Replaced by `1.0.0-rc.2`.

[Unreleased]: https://github.com/BigToEstDev/kdbx-vault/compare/v1.0.0-rc.2...HEAD
[1.0.0-rc.2]: https://github.com/BigToEstDev/kdbx-vault/releases/tag/v1.0.0-rc.2
