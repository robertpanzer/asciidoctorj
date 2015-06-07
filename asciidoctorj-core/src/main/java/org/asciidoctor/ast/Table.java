package org.asciidoctor.ast;

import java.util.List;

public interface Table extends AbstractBlock {

    boolean hasHeaderOption();

    List<Column> getColumns();

    Row getHeader();

    void setHeader(Row row);

    Row getFooter();

    void setFooter(Row row);

    List<Row> getBody();

}
