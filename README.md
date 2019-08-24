Scala
-----
>This project contains Scala 2.13 feature tests. It also details a foundational Scala development environment.

Environment
-----------
> The following items are commonplace in successful Scala shops.

1. **Git:** Establish a Github ( https://github.com/features ) account. Gitlab ( https://about.gitlab.com ) is a
 nice alternative. Both Github and Gitlab can be installed on an internal network.
2. **IDE:** Roughly 80% of developers in the Scala space use Jetbrains Intellij IDEA ( https://www.jetbrains.com/idea/ ).
 The community edition is free, while the ultimate edition is subscription based. The Scala Eclipse IDE ( http://scala-ide.org )
 is a poor alternative, IMHO. VS Code with Metals is a young and upcoming lightweight option. To each his/her own.;)
3. **Scala Build Tool:** Known as SBT ( https://www.scala-sbt.org/1.x/docs/index.html ), it’s a build and dependency
 management tool that can do so much more via plugins. It’s similar to Maven and Gradle, but Scala focused. It’s a must have!
4. **Maven Repository:** SBT requires network access to the public Maven Repository ( https://mvnrepository.com ) to obtain
 versioned libraries. A continually updated copy of the public Maven Repository can be set up and maintained internally via
 products like Nexus ( https://www.sonatype.com/nexus-repository-sonatype ) and JFrog Artifactory ( https://jfrog.com/artifactory/ ).
5. **Scala REPL:** The Scala REPL allows Scala developers to write code in a Unix/Windows terminal. Some Scala developers
 use it a lot, while others never do. It’s a nice tool to use for quick prototypes. It requires the installation of 
 Scala ( https://www.scala-lang.org/download/ ).
6. **Scala Spark REPL:** The Scala Spark REPL, is a specialized version of the Scala REPL that allows Scala-Spark developers to write
 Spark code in a Unix/Windows terminal. It requires the local installation of Spark ( https://spark.apache.org/releases/spark-release-2-4-3.html ).
7. **Slack:** A messaging-collaboration tool ( https://slack.com ) that allows developers to communicate ( quietly ) in the same room
 or across the country.
8. **VPN:** Secure VPN provides developers with outside access to a corporate development network.
9. **Local Services:** It’s often convenient to build Scala integrations tests against local services, such a Kafka, Cassandra
 or any other ( Big Data ) service. The same integrations tests can be run in a properly configured CI/CD environment ( see below ).
10. **CI/CD:** Known as continuous integration and deployment. Using a tool like Jenkins ( https://jenkins.io ), a team can
 set up a build project for each project housed in a Git repository. Based on a schedule, Jenkins will pull a Git repository,
 rebuild the project, run tests and package it using SBT. This is basic continuous integration. Using Jenkins components,
 build pipelines and stages can be constructed. Continuous deployment is a controversial topic, but available. Github and Gitlab
 have CI/CD features as well.
11. **Kanban Board:** Github, for each repository, provides a free Kanban Board that allows developers to manage project tasks.
 Gitlab also provides a Kanban Board for each repository. Other agile tools exist, but are considered overkill by a growing
 number of developers. Atlassian ( https://www.atlassian.com ) provides a popular set of agile tools.
12. **Laptop:** Scala developers typically use a MacBook Pro or Linux laptop, configured with 16GB+. Today's developers
must be mobile, able to work anywhere at anytime.
13. **Homebrew:** The ultimate software package manager for Apple OSX ( https://brew.sh ).
14. **Pathfinder:** The ultimate file system browser for Apple OSX ( https://cocoatech.com/#/ ).