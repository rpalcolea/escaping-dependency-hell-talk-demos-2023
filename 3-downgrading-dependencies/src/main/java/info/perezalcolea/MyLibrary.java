package info.perezalcolea;

import com.google.common.collect.Iterators;

import java.util.Collection;
import java.util.Iterator;

public class MyLibrary {
    private Collection<String> items;

    public final Iterator<String> items() {
        if(items == null || items.isEmpty()) {
            return Iterators.emptyIterator();
        }
        return items.iterator();
    }
}
