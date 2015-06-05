package org.asciidoctor.ast;

import org.jruby.runtime.builtin.IRubyObject;

public class ColumnImpl extends AbstractNodeImpl implements Column {

    public ColumnImpl(IRubyObject rubyNode) {
        super(rubyNode);
    }

    @Override
    public String getStyle() {
        return getString("style");
    }

    @Override
    public Table getTable() {
        return (Table) getParent();
    }
}
