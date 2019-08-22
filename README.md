Scala
-----
>This project tests Scala 2.13 features.

Scala Environment
-----------------
1. Git Service: Ideally, your client would establish a company Github ( https://github.com/features ) account. Other Git services are available as well. Alternatively, I suspect a Github service can be set up internally. GitLab is another nice option.
2. IDE: Roughly 80% of developers in the Scala space use Jetbrains Intellij IDEA ( https://www.jetbrains.com/idea/ ). The community edition is free. The ultimate edition is subscription based. The Scala Eclipse IDE is a poor alternative.
3. Scala Build Tool: Known as SBT ( https://www.scala-sbt.org/1.x/docs/index.html ), it’s a build and dependency management tool that can do so much more via plugins. It’s similar to Maven and Gradle, but Scala focused. It’s a must have.
4. Maven Repository: SBT requires network access to the public Maven Repository ( https://mvnrepository.com ) to obtain versioned libraries. A continually updated copy of the public Maven Repository can be set up and maintained internally. Products like Nexus help with this task.
5. Scala REPL: The Scala REPL allows Scala developers to write code in a Unix/Windows terminal. Some Scala developers use it a lot, while others never do. It’s a nice tool to have for quick prototypes. It requires the installation of Scala ( https://www.scala-lang.org/download/ ).
6. Scala Spark REPL: The Scala Spark REPL, is a specialized version of the Scala REPL that allows a developer to write Spark code in a Unix/Windows terminal. It requires the local installation of Spark ( https://spark.apache.org/releases/spark-release-2-4-3.html ).
7. Slack: A live messaging tool ( https://slack.com ) that allows developers to communicate ( quietly ) in the same room or across the country.
8. VPN: Secure VPN provides access to the development network to developers working outside of the office.
9. Local Services: It’s often convenient to build Scala integrations tests against local services, such a Kafka, Cassandra or any other Big Data service. The same integrations tests can be run in a properly configured CI/CD environment ( see below ).
10. CI/CD: Known as continuous integration and deployment. Using a tool like Jenkins ( https://jenkins.io ) a team can set up a build project for each project housed in a Git repository. Based on a schedule, Jenkins will aperiodically pull a Git repository and rebuild the project, run tests and package it using SBT. This is basic continuous integration. Using Jenkins components, build pipelines can be built. Continuous deployment is controversial topic, but available. Github and GitLab have CI/CD features as well.
11. Kanban Board: Github, for each repository, provides a free Kanban Board that allows developers to manage project tasks. Other exotic agile software exists ( but is typically overkill ). GitLab also provides a Kanban Board for each repository.
12. Homebrew: The ultimate software package manager for Apple OSX ( https://brew.sh ).
13. Pathfinder: The ultimate file system browser for Apple OSX ( https://cocoatech.com/#/ ).
14. MacbookPro 15” 16GB Laptop: Scala developers use an Apple or Linux laptop. I’ve never seen a Scala developer use Windows.:)