package rest.api;

import java.util.Iterator;

public class IteratorAdapter implements Iterator {
    private Iterator it = null;

    public IteratorAdapter(Iterator it) {
        this.it = it;
    }

    public boolean hasNext() {
        return this.it.hasNext();
    }

    public Object next() {
        return this.it.next();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
