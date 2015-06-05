package org.asciidoctor.ast;

import java.util.List;

public interface Table {

    boolean hasHeaderOption();

    List<Column> getColumns();

    Row getHeader();

    Row getFooter();

    List<Row> getBody();

}
