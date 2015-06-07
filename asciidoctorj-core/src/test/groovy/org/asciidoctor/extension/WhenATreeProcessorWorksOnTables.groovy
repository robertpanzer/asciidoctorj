package org.asciidoctor.extension

import org.asciidoctor.Asciidoctor
import org.asciidoctor.OptionsBuilder
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.jsoup.Jsoup
import org.junit.runner.RunWith
import spock.lang.Specification

@RunWith(ArquillianSputnik)
class WhenATreeProcessorWorksOnTables extends Specification {

    private static final String EMPTY_DOCUMENT = '= Document without table'

    public static final String TABLE = 'table'

    public static final String TD = 'td'

    @ArquillianResource
    private Asciidoctor asciidoctor

    def "then the extension should be able to create tables"() {

        given: 'an empty document and a tree processor that creates a simple table'
        asciidoctor.javaExtensionRegistry().treeprocessor(TableCreatorTreeProcessor)

        when: 'the document is rendered'
        String content = asciidoctor.convert(EMPTY_DOCUMENT, OptionsBuilder.options().headerFooter(false))

        then: 'the resulting document has a table'
        org.jsoup.nodes.Document document = Jsoup.parse(content)

        document.getElementsByTag(TABLE).size() == 1

        and: 'this table has one cell with the text A1'

        org.jsoup.nodes.Element table = document.getElementsByTag(TABLE)[0]
        table.getElementsByTag(TD).size() == 1
        table.getElementsByTag(TD)[0].text() == 'A1'
    }

}
