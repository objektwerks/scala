Scala
-----
>This project contains Scala 2.13.4 feature tests.

Test
----
* sbt clean test

Run
---
* sbt clean run

Environment
-----------
> The following list details commonplace items found in productive Scala shops.

1. **Git:** Git is the goto version control software in the world. Popular providers include: Github ( https://github.com/features )
and Gitlab ( https://about.gitlab.com ). Both can be installed on an internal network.
2. **IDE:** Common Scala IDEs include: Jetbrains Intellij IDEA ( https://www.jetbrains.com/idea/ ) and 
VS Code ( https://code.visualstudio.com ) with Metals ( https://scalameta.org/metals/ ).
3. **SBT:** The Scala Build Tool ( https://www.scala-sbt.org ) is a build and dependency management tool, extensible via plugins.
SBT is dominant in the Scala space. Mill ( http://www.lihaoyi.com/mill/ ) is a viable option. Maven and Gradle are poor options.
4. **Maven Repository:** SBT requires network access to the public Maven repository ( https://mvnrepository.com ) to obtain
versioned libraries. A continually updated copy of the public Maven Repository can be set up and maintained internally via
products like Nexus ( https://www.sonatype.com/nexus-repository-sonatype ) and JFrog Artifactory ( https://jfrog.com/artifactory/ ).
Ideally, internal versioned apps and components would be published to an internal Maven repository for easy internal sharing by
developers. Additional public repositories exist and may be required for development.
5. **Scala REPL:** The Scala REPL allows Scala developers to write code in a Unix or Windows terminal. Some Scala developers
use it a lot, while others never do. It’s a nice tool to use for quick prototypes. It requires the local installation of 
Scala ( https://www.scala-lang.org/download/ ). Another popular option is the Ammonite REPL ( http://ammonite.io ).
6. **Spark REPL:** The Scala Spark REPL is a specialized version of the Scala REPL that allows Scala-Spark developers to write
Spark code in a Unix or Windows terminal. It requires the local installation of Spark ( https://spark.apache.org/downloads.html ).
7. **Slack:** A messaging-collaboration tool ( https://slack.com ) for development teams.
8. **VPN:** VPN provides developers with secure access to public and private networks.
9. **Local Services:** It’s pragmatic and cost-effective to build Scala prototypes and run integration tests against local services,
such as Kafka, Cassandra, Postgresql, etc.
10. **CI/CD:** Continuous integration and deployment. Jenkins ( https://jenkins.io ) allows a team to set up a build project
for each project housed in a Git repository. Scheduled Jenkins jobs pull a Git repository, rebuild it, run tests, run integration
tests and package it. Continuous deployment is an optional feature. Github, Gitlab and other companies provide CI/CD services.
11. **Agile:** Github provides a free Kanban Board for each repository, allowing developers to manage project tasks. Gitlab also
provides a Kanban Board for each repository. Atlassian ( https://www.atlassian.com ) provides a popular ( overly robust ) set of
agile tools.
12. **Laptop:** Scala developers typically use a MacBook Pro, configured with a 15" screen, 16GB/32GB of RAM and **Admin** rights.
Windows and Linux laptops are also available.
13. **Homebrew:** The ultimate software package manager for Apple OSX ( https://brew.sh ).
14. **Sdkman:** A lightweight complement to Homebrew ( https://sdkman.io ) that runs on Apple OSX, Windows and Linux.
15. **Pathfinder:** The ultimate file system browser for Apple OSX ( https://cocoatech.com/#/ ).
16. **Linux:** Access to a Linux development server is ideal.