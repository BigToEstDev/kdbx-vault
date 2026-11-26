# Changelog

All notable changes to KDBX Vault are documented in this file - one version for Android and desktop
(`version.json`).

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to
[Semantic Versioning](https://semver.org/spec/v2.0.0.html). Release candidates are `-rc.N`.

## [Unreleased]

### Added

- Move an entry to another group right from the list - "Move to…" in the menu of its card, without editing it.
- Move a group with everything in it - its groups and entries - "Move to…" in the menu of its card.
- Search in the choice of a group: type a part of its name or path - in the entry form, on restoring from the recycle
  bin and on moving.

### Changed

- The core [1.0.2](https://github.com/BigToEstDev/kdbx-core/releases/tag/v1.0.2).
- About: "Licenses of the libraries" shows a download icon - it downloads a file, not opens a page.

### Security

- Android: the data of the app is no longer included in cloud backups. Moving to a new phone (Android 12+) takes
  only the settings - not the last database nor the password for biometric unlock.

### Fixed

- Saving an entry hides the password and the protected fields shown in clear - they no longer stay on the screen,
  colored, while the entry is written.
- A group can no longer be moved into a group of its own, into the recycle bin, nor can the root or the recycle bin
  be moved: such a move broke the tree of the database.
- The path of a group showed quotes around its separator: `group" › "group`.

## [1.0.0] - 2026-11-17

The first stable release of KDBX Vault - a free, open-source password manager compatible with KeePass (KDBX), for
Android and desktop (Windows, macOS, Linux), with the core
[1.0.1](https://github.com/BigToEstDev/kdbx-core/releases/tag/v1.0.1). Nothing changed since `1.0.0-rc.4`: it updates
it in place. `1.0.0-rc.1` (KeyVault) is a different app - remove it and install this one.

### Added

- Create a database, or open an existing one with a password, a key file, or both.
- Entries and groups: custom fields, attachments, history, recycle bin.
- Search, password generator and password strength check.
- Unlock with biometrics on Android; the database locks after a period of inactivity.
- Local sync without a network: merge a copy of the database in either direction, import another database, save or
  share a copy.
- The screen "About": the version of the app and of the core, the license, contacts, the site, the privacy policy,
  the source code of the app and of the core, the licenses of the libraries.
- The licenses of the libraries and crates the app ships - a file of every release
  (`kdbx-vault-<version>-THIRD_PARTY_LICENSES.txt`).
- A release build writes no texts of exceptions into the system log (logcat): they may hold the path or the name of
  a database.

## [1.0.0-rc.4] - 2026-11-17

The first release candidate under the name KDBX Vault, with the core
[1.0.1](https://github.com/BigToEstDev/kdbx-core/releases/tag/v1.0.1). It replaces `1.0.0-rc.1` and does not update
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
- The screen "About": the version of the app and of the core, the license, contacts, the site, the privacy policy,
  the source code of the app and of the core, the licenses of the libraries.
- The licenses of the libraries and crates the app ships - a file of every release
  (`kdbx-vault-<version>-THIRD_PARTY_LICENSES.txt`).

### Security

- A release build writes no texts of exceptions into the system log (logcat): they may hold the path or the name of
  a database.

## [1.0.0-rc.1] - 2026-11-14 [YANKED]

Withdrawn: published under the old name KeyVault. Replaced by `1.0.0-rc.4`.

[Unreleased]: https://github.com/BigToEstDev/kdbx-vault/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/BigToEstDev/kdbx-vault/releases/tag/v1.0.0
[1.0.0-rc.4]: https://github.com/BigToEstDev/kdbx-vault/releases/tag/v1.0.0-rc.4
