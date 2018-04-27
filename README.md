# mfsjea.jvm
[![Build Status](https://img.shields.io/travis/RanolP/mfsjea.jvm.svg)](https://travis-ci.org/RanolP/mfsjea.jvm)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2FRanolP%2Fmfsjea.jvm.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2FRanolP%2Fmfsjea.jvm?ref=badge_shield)
[![license](https://img.shields.io/github/license/RanolP/mfsjea.jvm.svg)](https://github.com/RanolP/mfsjea.jvm.svg/blob/master/LICENSE)
[![GitHub release](https://img.shields.io/github/release/RanolP/mfsjea.jvm.svg)](https://github.com/RanolP/mfsjea.jvm.svg/releases)

한/영 전환을 하지 않고 영문 상태로 친 타자를 고쳐 줍니다.
지원하는 자판 내에서 어떤 영문 자판으로 어떤 한글 타자를 치든 바로잡아 줍니다.

※ 'mfsjea'은 영문 쿼티 상태에서 세벌식으로 '한영'을 타자한 결과입니다.

본 프로젝트는 [이 프로젝트](https://github.com/Lee0701/mfsjea)의 JVM 판입니다.

## Getting Started
Maven/Gradle 을 통해 본 라이브러리를 사용할 수 있습니다.

### Prerequisites
 * Gradle 혹은 Maven

### Installing

#### Via jcenter bintray
최신 버전 : [![Bintray](https://api.bintray.com/packages/ranol-github/maven/mfsjea.jvm/images/download.svg)](https://bintray.com/ranol-github/maven/mfsjea.jvm/_latestVersion)


Maven
```xml
<repository>
    <id>jcenter</id>
    <name>jcenter-bintray</name>
    <url>http://jcenter.bintray.com</url>
</repository>

<dependency>
    <groupId>com.github.RanolP</groupId>
    <artifactId>mfsjea.jvm</artifactId>
    <version>0.1.3</version>
</dependency>
```

Gradle
```gradle
repositories {
    jcenter()
}

dependencies {
    compile 'com.github.RanolP:mfsjea.jvm:0.1.3'
}
```

#### Via jitpack
최신 버전 : ![JitPack](https://img.shields.io/jitpack/v/RanolP/mfsjea.jvm.svg)

Maven
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.RanolP</groupId>
    <artifactId>mfsjea.jvm</artifactId>
    <version>0.1.3</version>
</dependency>
```

Gradle
```gradle
repositories {
    maven {
        url 'https://jitpack.io'
    }
}

dependencies {
    compile 'com.github.RanolP:mfsjea.jvm:0.1.3'
}
```

## Built With
 * Gradle - 의존성 관리 시스템
 * Dokka - 문서화 도구
 * Travis - 지속적 통합 서비스
 * Fossa - 라이선스 스캔
 * Jitpack - 쉽고 간편한 패키지 저장소

## Contributing
언제나 개선은 환영이야!

## Versioning
이 프로젝트는 [SemVer 2.0.0](https://semver.org/lang/ko/)을 준수합니다. 존재하는 버전은 태그에서 확인하세요.

## Authors
 * **Ranol** - *프로젝트 총괄* - [RanolP](https://github.com/RanolP)

[기여자 목록](https://github.com/RanolP/mfsjea.jvm/contributors)에서 더 많은 기여자를 확인하세요.

## License
이 프로젝트는 Apache 2.0 라이선스로 관리됩니다. - [라이선스](https://github.com/RanolP/mfsjea.jvm/blob/master/LICENSE) 파일에서 자세한 사항을 확인하세요.


[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2FRanolP%2Fmfsjea.jvm.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2FRanolP%2Fmfsjea.jvm?ref=badge_large)
