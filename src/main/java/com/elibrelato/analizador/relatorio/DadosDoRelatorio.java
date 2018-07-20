package com.elibrelato.analizador.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author elibrelato@gmail.com
 */
public class DadosDoRelatorio implements Collection {
    
    private final Collection dados = new ArrayList();

    @Override
    public int size() {
        return dados.size();
    }

    @Override
    public boolean isEmpty() {
        return dados.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return dados.contains(o);
    }

    @Override
    public Iterator iterator() {
        return dados.iterator();
    }

    @Override
    public Object[] toArray() {
        return dados.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return dados.toArray(a);
    }

    @Override
    public boolean add(Object e) {
        return dados.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return dados.remove(o);
    }

    @Override
    public boolean containsAll(Collection c) {
        return dados.containsAll(c);
    }

    @Override
    public boolean addAll(Collection c) {
        return dados.addAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return dados.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return dados.retainAll(c);
    }

    @Override
    public void clear() {
        dados.clear();
    }
}
