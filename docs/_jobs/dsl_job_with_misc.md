---
title: Job with miscellaneous options
position: 1.5
right_code: |
  ~~~ groovy
    project(key: 'SIMPLEPROJECT', name: 'simple project') {
        plan(key: 'SIMPLEPLAN', name: 'Renamed plan') {
            description "this was a simple plan"
            enabled false

            stage(name: 'simple stage') {
                description "this is simple stage"
                manual false

                job(key: 'SIMPLEJOB', name: 'simple job') {
                    description "This was a simple job"
                    enabled false

                    tasks {
                        script() {
                            description 'echo hello world'
                            inline {
                                interpreter ScriptInterpreter.LEGACY_SH_BAT
                                scriptBody 'echo "Hello World"'
                            }
                        }
                    }

                    miscellaneous {
                        cleanWorkingDirectoryAfterEachBuild()

                        overrideDefaultHangingBuildDetectionCriteria {
                            buildTimeMultiplier 2.5
                            logQuietTime 10
                            buildQueueTimeout 60
                        }

                        nCoverOutputWillBeProduced {
                            nCoverXmlDirectory 'a/b/c'
                        }

                        cloverCodeCoverage {
    //                        cloverIsAlreadyIntegratedIntoBuild(cloverXmlLocation: 'a/b/c')
                            automaticallyIntegrateCloverIntoBuild(cloverLicense: 'LICENSE') {
                                generateCloverHistoricalReport()
                                generateJSONReport()
                            }
                        }

                        patternMatchLabelling {
                            regexPattern '[a-z]+'
                            labels 'test'
                        }
                    }
                }
            }
        }
    }
  ~~~
  {: title="DSL" }

---


