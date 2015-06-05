package org.asciidoctor.ast;

import org.asciidoctor.internal.RubyBlockListDecorator;
import org.asciidoctor.internal.RubyObjectWrapper;
import org.jruby.RubyArray;
import org.jruby.RubyFixnum;
import org.jruby.runtime.builtin.IRubyObject;

import java.util.ArrayList;
import java.util.List;

public class TableImpl extends AbstractBlockImpl implements Table {

    public TableImpl(IRubyObject rubyObject) {
        super(rubyObject);
    }

    @Override
    public boolean hasHeaderOption() {
        return getBoolean("has_header_option");
    }

    @Override
    public List<Column> getColumns() {
        RubyArray rubyBlocks = (RubyArray) getRubyProperty("columns");
        return new RubyBlockListDecorator<Column>(rubyBlocks);
    }

    @Override
    public Row getFooter() {
        Rows rows = new Rows(getRubyProperty("rows"));
        return rows.getFooter();
    }

    @Override
    public List<Row> getBody() {

        Rows rows = new Rows(getRubyProperty("rows"));
        return rows.getBody();

    }

    public Row getHeader() {
        Rows rows = new Rows(getRubyProperty("rows"));
        return rows.getHeader();
    }


    private static class Rows extends RubyObjectWrapper {

        public Rows(IRubyObject rubyNode) {
            super(rubyNode);
        }

        private Row getHeader() {
            RubyArray headerRows = (RubyArray) getRubyProperty("head");
            if (headerRows == null || headerRows.size() == 0) {
                return null;
            }
            return new RowImpl(headerRows.at(RubyFixnum.newFixnum(getRuntime(), 0)));
        }

        private List<Row> getBody() {
            List<Row> ret = new ArrayList<Row>();
            RubyArray bodyRows = (RubyArray) getRubyProperty("body");
            if (bodyRows == null || bodyRows.size() == 0) {
                return null;
            }

            for (Object rubyRow: bodyRows) {
                ret.add(new RowImpl((IRubyObject) rubyRow));
            }
            return ret;
        }

        private Row getFooter() {
            RubyArray footerRows = (RubyArray) getRubyProperty("foot");
            if (footerRows == null || footerRows.size() == 0) {
                return null;
            }
            return new RowImpl(footerRows.at(RubyFixnum.newFixnum(getRuntime(), 0)));
        }
    }
}
