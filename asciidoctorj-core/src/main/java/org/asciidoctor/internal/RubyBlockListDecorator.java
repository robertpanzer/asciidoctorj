package org.asciidoctor.internal;

import org.asciidoctor.ast.AbstractNode;
import org.asciidoctor.ast.AbstractNodeImpl;
import org.asciidoctor.ast.NodeConverter;
import org.jruby.RubyArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



public class RubyBlockListDecorator<T extends AbstractNode> implements List<T> {

    private final RubyArray rubyBlockList;

    public RubyBlockListDecorator(RubyArray rubyBlockList) {
        this.rubyBlockList = rubyBlockList;
    }

    @Override
    public int size() {
        return rubyBlockList.size();
    }

    @Override
    public boolean isEmpty() {
        return rubyBlockList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof AbstractNodeImpl)) {
            return false;
        }
        return rubyBlockList.contains(((AbstractNodeImpl) o).getRubyObject());
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public T next() {
                return get(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        return getJavaBlockList().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return getJavaBlockList().toArray(a);
    }

    private List<T> getJavaBlockList() {
        List<T> javaBlockList = new ArrayList<T>(rubyBlockList.size());
        Object[] ret = new Object[size()];
        for (int i = 0; i < rubyBlockList.size(); i++) {
            javaBlockList.add((T) NodeConverter.createASTNode(rubyBlockList.get(i)));
        }
        return javaBlockList;
    }

    @Override
    public boolean add(T abstractBlock) {
        return rubyBlockList.add(((AbstractNodeImpl) abstractBlock).getRubyObject());
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof AbstractNodeImpl)) {
            return false;
        }
        return rubyBlockList.remove(((AbstractNodeImpl) o).getRubyObject());
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return rubyBlockList.containsAll(getDelegateCollection(c));
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return rubyBlockList.addAll(getDelegateCollection(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return rubyBlockList.addAll(index, getDelegateCollection(c));
    }

    private Collection<Object> getDelegateCollection(Collection<?> c) {
        Collection<Object> delegateList = new ArrayList<Object>(c.size());
        for (Object o: c) {
            if (o instanceof AbstractNodeImpl) {
                delegateList.add(((AbstractNodeImpl) o).getRubyObject());
            } else {
                delegateList.add(o);
            }
        }
        return delegateList;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return rubyBlockList.removeAll(getDelegateCollection(c));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return rubyBlockList.retainAll(getDelegateCollection(c));
    }

    @Override
    public void clear() {
        rubyBlockList.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RubyBlockListDecorator) {
            return rubyBlockList.equals(((RubyBlockListDecorator) o).rubyBlockList);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return rubyBlockList.hashCode();
    }

    @Override
    public T get(int index) {
        return (T) NodeConverter.createASTNode(rubyBlockList.get(index));
    }

    @Override
    public T set(int index, T element) {
        Object oldObject = rubyBlockList.set(index, ((AbstractNodeImpl) element).getRubyObject());
        return (T) NodeConverter.createASTNode(oldObject);
    }

    @Override
    public void add(int index, T element) {
        rubyBlockList.add(index, ((AbstractNodeImpl) element).getRubyObject());
    }

    @Override
    public T remove(int index) {
        Object oldObject = rubyBlockList.remove(index);
        if (oldObject == null) {
            return null;
        } else {
            return (T) NodeConverter.createASTNode(oldObject);
        }
    }

    @Override
    public int indexOf(Object o) {
        if (o instanceof AbstractNodeImpl) {
            return rubyBlockList.indexOf(((AbstractNodeImpl) o).getRubyObject());
        } else {
            return -1;
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o instanceof AbstractNodeImpl) {
            return rubyBlockList.lastIndexOf(((AbstractNodeImpl) o).getRubyObject());
        } else {
            return -1;
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
