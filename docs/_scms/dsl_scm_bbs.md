---
title: Bitbucket example
position: 1.1
right_code: |
  ~~~groovy
  project("BITBUCKETSERVER") {
      name "Simple project"

      plan("BITBUCKETSERVER") {
          name "Simple plan"
          description "this is a simple plan"
          enabled true

          scm {
              bitbucketServer(name: "myBitbucketServerRepo") {
                  projectKey "project_1"
                  repoSlug "rep_1"
                  branch "develop"
                  serverName "bitbucketServer"
                  repositoryUrl "ssh://git@localhost:7999/project_1/rep_1.git"

                  advancedOptions {
                      enableRepositoryCachingOnRemoteAgents true
                      useShallowClones true
                      useSubmodules true
                      commandTimeoutInMinutes 20
                      verboseLogs true
                      fetchWholeRepository true
                      quietPeriod {
                          waitTimeInSeconds 120
                          maximumRetries 3
                      }
                      includeExcludeFiles(MatchType.EXCLUDE_ALL_MATCHING_CHANGES) {
                          filePattern "exe"
                      }
                      excludeChangesetsRegex "FIXES .*"
                      webRepository {
                          fisheye {
                              url "http://localhost:7990"
                              repositoryPath "a/b/c"
                              repositoryName "d"
                          }
                      }
                  }

              }
          }
      }
  }
  ~~~
  {: title="DSL" }

---