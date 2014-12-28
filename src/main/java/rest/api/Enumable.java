package rest.api;

import java.io.ObjectStreamException;
import java.util.*;

/**
 * Created by dmitry on 28.12.14.
 */
public class Enumable {
    private static final long serialVersionUID = -2986225252004417106L;
    private static final Iterator EMPTY_ITERATOR = new IteratorAdapter((new ArrayList(0)).iterator());
    private static final transient Map enumsNameMap = new HashMap();
    private static final transient Map enumsIdMap = new HashMap();
    protected String name;
    protected Integer id;
    private transient String string;

    protected Enumable() {
    }

    private void putName(String name, Class cl) {
        Object enumMap = (Map)enumsNameMap.get(cl);
        if(enumMap == null) {
            enumsNameMap.put(cl, enumMap = new HashMap());
        }

        Object retO = ((Map)enumMap).put(name, this);

        assert retO == null : "Enum of " + name + " name is already exist. Class=" + cl;

        this.string = name;
    }

    private void putId(int id, Class cl) {
        Object enumMap = (Map)enumsIdMap.get(cl);
        if(enumMap == null) {
            enumsIdMap.put(cl, enumMap = new HashMap());
        }

        Object retO = ((Map)enumMap).put(this.id, this);

        assert retO == null : "Enum of " + id + " id is already exist. Class=" + cl;

        this.string = this.string + " (" + id + ")";
    }

    protected Enumable(String name) {
        this.name = name;
        this.id = null;
        Class cl = this.getClass();
        this.putName(name, cl);
    }

    protected Enumable(String name, int id) {
        this.name = name;
        this.id = Integer.valueOf(id);
        Class cl = this.getClass();
        this.putName(name, cl);
        this.putId(id, cl);
    }

    protected static Enumable getEnum(String name, Class cl) {
        Enumable ret = (Enumable)((Map)enumsNameMap.get(cl)).get(name);
        if(ret == null) {
            throw new NullPointerException("Getting enum is null. Name=" + name + ", Class=" + cl);
        } else {
            assert ret.getClass() == cl;

            return ret;
        }
    }

    protected static Iterator iterator(Class cl) {
        HashMap map = (HashMap)enumsNameMap.get(cl);
        if(map == null) {
            return EMPTY_ITERATOR;
        } else {
            Set retSet = ((Map)map.clone()).keySet();
            return new IteratorAdapter(retSet.iterator());
        }
    }

    protected static Enumable getEnum(int id, Class cl) {
        Enumable ret = (Enumable)((Map)enumsIdMap.get(cl)).get(new Integer(id));
        return ret;
    }

    public String toString() {
        return this.string;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id.intValue();
    }

    protected Object readResolve() throws ObjectStreamException {
        return getEnum(this.name, this.getClass());
    }
}
