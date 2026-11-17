# KDBX Vault

[![Release](https://img.shields.io/github/v/release/BigToEstDev/kdbx-vault)](https://github.com/BigToEstDev/kdbx-vault/releases/latest)
[![CI](https://github.com/BigToEstDev/kdbx-vault/actions/workflows/ci.yml/badge.svg)](https://github.com/BigToEstDev/kdbx-vault/actions/workflows/ci.yml)
[![License: GPL-3.0-only](https://img.shields.io/badge/license-GPL--3.0--only-blue)](LICENSE)

A free, open-source, offline password manager for KeePass (KDBX 4) databases, for Android and desktop
(Windows, macOS, Linux).

Website: <https://bigtoestdev.github.io/kdbxvault/>

## Download

Installers are published on the [Releases](https://github.com/BigToEstDev/kdbx-vault/releases/latest) page and on
the [website](https://bigtoestdev.github.io/kdbxvault/releases).

| Platform | File | Requirements |
|---|---|---|
| Android | `kdbx-vault-<version>.apk` | Android 10 (API 29) or newer; arm64-v8a, armeabi-v7a |
| Windows | `kdbx-vault-<version>-windows-x64.msi` | x86_64 |
| macOS | `kdbx-vault-<version>-macos-arm64.dmg` | Apple Silicon |
| Linux | `kdbx-vault-<version>-linux-amd64.deb` | x86_64, Debian / Ubuntu |

Android and Windows are tested; macOS and Linux builds are not tested yet. The desktop installers are not signed
yet: Windows SmartScreen and macOS Gatekeeper show a warning — the release notes say how to proceed.

On Android the app does not update itself: [Obtainium](https://obtainium.imranr.dev) can follow this repository
and install new releases.

## Features

- Create a database, or open an existing one with a password, a key file, or both.
- Entries and groups: custom fields, attachments, history, recycle bin.
- Search, password generator and password strength check.
- Unlock with biometrics on Android; the database locks after a period of inactivity.
- Local sync without a network: merge a copy of the database in either direction, import another database,
  save or share a copy.
- One code base for Android and desktop (Compose Multiplatform); the database engine is written in Rust.

## Compatibility

The database is a regular KDBX 4 file: it is meant to be opened in KeePassXC, KeePass and KeePassDX as well.
Formats, ciphers and key files are those of the engine,
[kdbx-core](https://github.com/BigToEstDev/kdbx-core#compatibility).

## Security and privacy

- Works offline: the Android app has no network permission; nothing is sent anywhere.
- The database stays a file the user chooses: no account, no cloud, no telemetry.

### Verifying a download

- Every file of a release is listed in `SHA256SUMS` and has a build provenance attestation:
  `gh attestation verify <file> -R BigToEstDev/kdbx-vault`.
- Every release APK is signed with the same key. Its certificate SHA-256 fingerprint:

  ```
  27:BD:70:DD:2D:0A:3F:EC:0B:DB:75:C8:C9:5A:B7:CF:88:77:2D:7A:50:85:D0:33:3C:22:41:E0:DF:76:3A:89
  ```

  Check it with `apksigner verify --print-certs <file>.apk` (Android SDK build tools) or an app such as
  AppVerifier; a different fingerprint means the file is not ours — do not install it.

### Reporting a vulnerability

Please use [private vulnerability reporting](https://github.com/BigToEstDev/kdbx-vault/security/advisories/new) on
GitHub, not a public issue. Other bugs and questions go to
[issues](https://github.com/BigToEstDev/kdbx-vault/issues).

## Repository layout

| Path | Contents |
|---|---|
| `composeApp` | The shared app: navigation and the screens put together for Android and desktop |
| `app` | The Android application |
| `desktopApp` | The desktop application |
| `welcome`, `vault`, `settings` | Feature modules: a closed database, an open database, settings — each split into `domain`, `data` and `pres` |
| `core` | Shared modules: the boundary with the engine, file access, theme, navigation |
| `rust/pass-ffi` | The native bridge to the engine: JNI on Android, a shared library on desktop |
| `build-logic` | Gradle convention plugins |

## Building

Built and tested on Windows x86_64.

| Tool | Version | Notes |
|---|---|---|
| JDK | 17 | downloaded by Gradle (toolchains) when missing |
| Gradle | 9.4.1 | through the wrapper, `gradlew` |
| Android SDK | compile and target 37, build tools 36.0.0 | |
| Android NDK | 28.2.13676358 | pinned in the build |
| Rust | 1.98.1 | pinned in `rust-toolchain.toml` — install it with `rustup toolchain install` |
| cargo-ndk | 4.1.2 | `cargo install cargo-ndk` |

The engine is a path dependency: check out [kdbx-core](https://github.com/BigToEstDev/kdbx-core) next to this
repository, in a folder named `pass-rust-core`.

```
<parent>/
  pass-app/        this repository
  pass-rust-core/  kdbx-core
```

The version of the engine this repository is built with in CI and in releases is the tag in `core.ref`; check out the
same tag to build exactly what a release was built from.

Gradle builds the bridge itself, for every Android ABI or for the desktop host:

```bash
./gradlew assembleDebug        # Android debug APK
./gradlew :desktopApp:run      # desktop app
./gradlew allTests             # tests of the shared modules
```

Changes between versions are listed in [`CHANGELOG.md`](CHANGELOG.md).

## Contributing

Pull requests are not accepted. Bug reports and questions are welcome in
[issues](https://github.com/BigToEstDev/kdbx-vault/issues).

## Licence

Copyright (C) 2026 BigToEstDev

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
License as published by the Free Software Foundation, version 3 (GPL-3.0-only).

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

The full text is in [`LICENSE`](LICENSE).

The app links the GPLv3 engine [kdbx-core](https://github.com/BigToEstDev/kdbx-core), so the app and every build of
it are distributed under the GPLv3. The licences of the libraries every build ships are in the file
`kdbx-vault-<version>-THIRD_PARTY_LICENSES.txt` of each release and on the "About" screen of the app.

The licence covers the code, not the name: "KDBX Vault" and its logo are not licensed under the GPLv3. Forks are
welcome under their own name.
