package org.asciidoctor

import org.asciidoctor.ast.AbstractBlock
import org.asciidoctor.ast.DocumentRuby
import org.asciidoctor.ast.Section
import org.asciidoctor.internal.IOUtils
import org.asciidoctor.util.ClasspathResources
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.junit.runner.RunWith
import spock.lang.Specification

@RunWith(ArquillianSputnik)
class WhenAnAsciidoctorDocumentIsLoaded extends Specification {

    private static final String DOCUMENT = '''= Document Title

preamble

== Section A

paragraph

--
Exhibit A::
+
[#tiger.animal]
image::tiger.png[Tiger]
--

image::cat.png[Cat]

== Section B

paragraph'''

    private static final String ROLE = '''['quote', 'author', 'source', role='famous']

A famous quote.
    '''

    private static final String REFTEXT = '''[reftext='the first section']
== Section One

content'''

    public static final String ENCODING = 'encoding'

    public static final String UTF8 = 'UTF-8'

    public static final String ROLE_FAMOUS = 'famous'

    public static final String NOTE = 'note'

    public static final String TARGET = 'target'

    public static final String TARGET_JPG = 'target.jpg'

    @ArquillianResource
    private ClasspathResources classpath

    @ArquillianResource
    private Asciidoctor asciidoctor

    def 'should return section blocks'() {

        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, [:])
        Section section = document.blocks().get(1)

        then:
        section.index() == 0
        section.sectname() == 'sect1'
        !section.special()
    }

    def 'should return document title'() {
        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, [:])

        then:
        document.doctitle() == 'Document Title'
    }

    def 'should find elements from document'() {
        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, [:])
        Map<Object, Object> selector = ['context': ':image']
        List<AbstractBlock> findBy = document.findBy(selector)

        then:
        findBy.size() == 2
        findBy[0].attributes[TARGET] == 'tiger.png'
    }


    def 'should return options from document'() {
        given:
        Map<String, Object> options = OptionsBuilder.options().compact(true).asMap()

        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, options)

        then:
        document.options['compact']
    }

    def 'should return node name document for document'() {
        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, [:])
        then:
        document.nodeName == 'document'
    }

    def 'should return that it is not inline'() {
        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, [:])

        then:
        !document.inline
    }

    def 'should return that it is a block'() {
        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, [:])

        then:
        document.block
    }

    def 'should be able to manipulate attributes'() {
        given:
        Map<String, Object> options = OptionsBuilder.options()
                .attributes(AttributesBuilder.attributes().dataUri(true))
                .compact(true).asMap()

        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, options)

        then:
        document.attributes.containsKey(ENCODING)
        document.isAttr(ENCODING, UTF8, false)
        document.getAttr(ENCODING, '', false) == UTF8
    }

    def 'should be able to get roles'() {
        when:
        DocumentRuby document = asciidoctor.load(ROLE, [:])
        AbstractBlock abstractBlock = document.blocks().get(0)

        then:
        abstractBlock.getRole() == ROLE_FAMOUS
        abstractBlock.hasRole(ROLE_FAMOUS)
        //abstractBlock.isRole()
        abstractBlock.roles.contains(ROLE_FAMOUS)
    }

    def 'should be able to get reftext from section'() {
        when:
        DocumentRuby document = asciidoctor.load(REFTEXT, [:])
        AbstractBlock abstractBlock = document.blocks().get(0)

        then:
        abstractBlock.getReftext() == 'the first section'
        abstractBlock.isReftext()
    }

    def 'should show ref URI when loaded with dataURI false'() {
        given:
        Map<String, Object> options = OptionsBuilder.options()
                .attributes(AttributesBuilder.attributes().dataUri(false))
                .compact(true).asMap()

        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, options)

        then:
        document.iconUri(NOTE) == './images/icons/note.png'
    }

    def 'should show data URI when loaded with option dataUri true'() {
        given:
        Map<String, Object> options = OptionsBuilder.options().safe(SafeMode.SAFE)
                .attributes(AttributesBuilder.attributes().dataUri(true).icons('font'))
                .compact(true).asMap()

        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, options)

        then:
        document.iconUri(NOTE) == 'data:image/png:base64,'
    }

    def 'should be able to get media uri'() {
        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, [:])

        then:
        document.mediaUri(TARGET) == TARGET
    }

    def 'should be able to get image uri'() {
        given:
        Map<String, Object> options = OptionsBuilder.options().safe(SafeMode.SAFE)
                .attributes(AttributesBuilder.attributes().dataUri(false))
                .compact(true).asMap()

        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, options)

        then:
        document.imageUri(TARGET_JPG) == TARGET_JPG
        document.imageUri(TARGET_JPG, 'imagesdir') == TARGET_JPG
    }

    def 'should be able to normalize web path'() {
        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, [:])

        then:
        document.normalizeWebPath(TARGET, null, true) == TARGET
    }

    def 'should be able to read asset'() {
        given:
        Map<String, Object> options = OptionsBuilder.options().safe(SafeMode.SAFE)
                .attributes(AttributesBuilder.attributes().dataUri(false))
                .compact(true).asMap()

        when:
        DocumentRuby document = asciidoctor.load(DOCUMENT, options)
        File inputFile = classpath.getResource('rendersample.asciidoc')
        String content = document.readAsset(inputFile.absolutePath, [:])

        then:
        content == IOUtils.readFull(new FileReader(inputFile))
    }
}