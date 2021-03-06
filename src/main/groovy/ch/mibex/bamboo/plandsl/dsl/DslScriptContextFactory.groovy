package ch.mibex.bamboo.plandsl.dsl

import org.apache.tools.ant.types.FileSet
import org.apache.tools.ant.types.PatternSet

class DslScriptContextFactory {

    private DslScriptContextFactory() {}

    static Set<DslScriptContext> createContexts(List<String> dslLocations,
                                                boolean inlineScript,
                                                String dslText,
                                                File rootDir,
                                                List<String> additionalClassPaths) {
        Set<DslScriptContext> scriptContexts = new LinkedHashSet<>()

        if (inlineScript) {
            scriptContexts << new DslScriptContext(dslText)
        } else {
            List<URL> classPaths = resolveClassPaths(additionalClassPaths, rootDir)

            dslLocations.each { target ->
                List<String> srcFiles = collectFilesForAntPattern(rootDir, target)

                if (srcFiles.isEmpty()) {
                    throw new DslException("no DSL scripts found at $target")
                }

                srcFiles.each { srcFile ->
                    def file = new File(rootDir, srcFile)
                    def urlRoot = file.parentFile.toURI().toURL()
                    scriptContexts << new DslScriptContext(file.name, null, [urlRoot] + classPaths as URL[])
                }
            }
        }

        scriptContexts
    }

    private static List<URL> resolveClassPaths(List<String> additionalClassPaths, File rootDir) {
        additionalClassPaths.collectMany { cp ->
            if (cp.contains('*') || cp.contains('?')) {
                collectFilesForAntPattern(rootDir, cp)
            } else {
                [cp]
            }
        }.collect {
            new File(rootDir, it.toString()).toURI().toURL()
        }
    }

    private static List<String> collectFilesForAntPattern(File rootDir, String target) {
        def fileSet = new FileSet()
        fileSet.setDir(rootDir)
        PatternSet.NameEntry include = fileSet.createInclude()
        include.setName(target)
        def ds = fileSet.getDirectoryScanner(new org.apache.tools.ant.Project())
        ds.includedFiles.toList()
    }
}
