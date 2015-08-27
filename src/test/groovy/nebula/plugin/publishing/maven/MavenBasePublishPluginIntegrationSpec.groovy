/*
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nebula.plugin.publishing.maven

import nebula.test.IntegrationSpec

class MavenBasePublishPluginIntegrationSpec extends IntegrationSpec {
    def setup() {
        buildFile << """\
            ${applyPlugin(MavenBasePublishPlugin)}

            version = '0.1.0'
            group = 'test.nebula'
        """.stripIndent()

        settingsFile << '''\
            rootProject.name = 'basepublishingtest'
        '''.stripIndent()
    }

    def 'Check name appears in pom'() {
        when:
        runTasksSuccessfully('generatePomFileForNebulaPublication')

        then:
        def pomFile = new File(projectDir, 'build/publications/nebula/pom-default.xml')
        def pom = new XmlSlurper().parse(pomFile)

        pom.name == 'basepublishingtest'
    }

    def 'Check description appears in pom'() {
        given:
        buildFile << '''\
            description = 'Test description'
        '''.stripIndent()

        when:
        runTasksSuccessfully('generatePomFileForNebulaPublication')

        then:
        def pomFile = new File(projectDir, 'build/publications/nebula/pom-default.xml')
        def pom = new XmlSlurper().parse(pomFile)

        pom.description == 'Test description'
    }
}