Scala
-----
>This project contains Scala 2.13 feature tests.

Test
----
* sbt clean test

Run
---
* sbt clean compile run

Environment
-----------
> The following list details commonplace items found in productive Scala shops.

1. **Git:** Establish a Github ( https://github.com/features ) or Gitlab ( https://about.gitlab.com ) account.
Both Github and Gitlab can be installed on an internal network.
2. **IDE:** Commonly used IDEs include: Jetbrains Intellij IDEA ( https://www.jetbrains.com/idea/ ) and 
VS Code ( https://code.visualstudio.com ) with Metals ( https://scalameta.org/metals/ ).
3. **SBT:** The Scala Build Tool ( https://www.scala-sbt.org/1.x/docs/index.html ) is a build and dependency
management tool that's extensible via plugins. It’s similar to Maven and Gradle, but Scala focused and dominant
in the Scala space.
4. **Maven Repository:** SBT requires network access to the public Maven repository ( https://mvnrepository.com ) to obtain
 versioned libraries. A continually updated copy of the public Maven Repository can be set up and maintained internally via
 products like Nexus ( https://www.sonatype.com/nexus-repository-sonatype ) and JFrog Artifactory ( https://jfrog.com/artifactory/ ).
 Ideally, versioned apps and components would be published to an internal Maven repository for easy internal sharing.
5. **Scala REPL:** The Scala REPL allows Scala developers to write code in a Unix or Windows terminal. Some Scala developers
 use it a lot, while others never do. It’s a nice tool to use for quick prototypes. It requires the local installation of 
 Scala ( https://www.scala-lang.org/download/ ).
6. **Spark REPL:** The Scala Spark REPL, is a specialized version of the Scala REPL that allows Scala-Spark developers to write
 Spark code in a Unix or Windows terminal. It requires the local installation of Spark ( https://spark.apache.org/downloads.html ).
7. **Slack:** A messaging-collaboration tool ( https://slack.com ) that allows developers to communicate ( quietly ) in the same room
or across the world.
8. **VPN:** Secure VPN provides developers with external access to a private, or internal, development network(s).
9. **Local Services:** It’s often convenient to build Scala prototypes and integration tests against local services, such a Kafka,
Cassandra, Postgresql and/or other services. The same integration tests can be run in a properly configured CI/CD network environment.
10. **CI/CD:** Continuous integration and deployment. Jenkins ( https://jenkins.io ) allows a team to set up a build project
for each project housed in a Git repository. Based on a schedule, Jenkins will pull a Git repository, rebuild the project, run tests
and package it. Continuous deployment is an optional feature. Github, Gitlab and other companies provide CI/CD services.
11. **Agile:** Github provides a free Kanban Board for each repository, allowing developers to manage project tasks.
Gitlab also provides a Kanban Board for each repository. Other agile tools exist, but are considered overkill by a growing
number of developers. Atlassian ( https://www.atlassian.com ) provides a popular ( yet overly robust ) set of agile tools.
12. **Laptop:** Scala developers typically use a MacBook Pro, configured with a 15" screen, 32GB of RAM and **Admin** rights. Today's
developers must be mobile, capable of working anywhere at anytime. Windows and Linux laptops represent additional options.
13. **Homebrew:** The ultimate software package manager for Apple OSX ( https://brew.sh ).
14. **SdkMan:** A lightweight quasi-alternative to Homebrew ( https://sdkman.io ).
15. **Pathfinder:** The ultimate file system browser for Apple OSX ( https://cocoatech.com/#/ ).
16. **Linux:** The world literally runs on Linux. So having access to a Linux development server is ideal.