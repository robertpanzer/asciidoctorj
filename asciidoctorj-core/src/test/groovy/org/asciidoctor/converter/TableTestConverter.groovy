package org.asciidoctor.converter

import org.asciidoctor.ast.AbstractNode
import org.asciidoctor.ast.DocumentRuby
import org.asciidoctor.ast.Table

class TableTestConverter extends AbstractConverter {

    public static final int COLWIDTH = 15
    public static final String COL_SEPARATOR = '|'
    public static final String NEWLINE = '\n'

    TableTestConverter(String backend, Map<Object, Object> opts) {
        super(backend, opts)
    }

    @Override
    Object convert(AbstractNode node, String transform, Map<Object, Object> opts) {
        if (transform == null) {
            transform = node.nodeName
        }

        this."convert${transform.capitalize()}"(node)
    }

    Object convertEmbedded(DocumentRuby node) {
        node.content
    }

    Object convertTable(Table table) {
        def header =
                [ table.header.cells.collect { it.text.padRight(COLWIDTH) }.join(COL_SEPARATOR),
                  table.header.cells.collect { '-' * COLWIDTH }.join('+')]

        def body =
                table.body.collect {
                    row -> row.cells.collect { cell -> cell.text.padRight(COLWIDTH) }.join(COL_SEPARATOR)
                }

        [header, body].flatten().join(NEWLINE)

    }


}
