package tools;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ArrayMap<K, V> extends AbstractMap<K, V> implements Serializable
{
    public static long serialVersionUID;
    private transient Set<? extends Map.Entry<K, V>> entries;
    private ArrayList<Entry<K, V>> list;
    
    public ArrayMap() {
        this.entries = null;
        this.list = new ArrayList<Entry<K, V>>();
    }
    
    public ArrayMap(final Map<K, V> map) {
        this.entries = null;
        this.list = new ArrayList<Entry<K, V>>();
        this.putAll((Map<? extends K, ? extends V>)map);
    }
    
    public ArrayMap(final int initialCapacity) {
        this.entries = null;
        this.list = new ArrayList<Entry<K, V>>(initialCapacity);
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.entries == null) {
            this.entries = new AbstractSet<Entry<K, V>>() {
                @Override
                public void clear() {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public Iterator<Entry<K, V>> iterator() {
                    return ArrayMap.this.list.iterator();
                }
                
                @Override
                public int size() {
                    return ArrayMap.this.list.size();
                }
            };
        }
        return (Set<Map.Entry<K, V>>)this.entries;
    }
    
    @Override
    public V put(final K key, final V value) {
        final int size = this.list.size();
        Entry<K, V> entry = null;
        int i;
        if (key == null) {
            for (i = 0; i < size; ++i) {
                entry = this.list.get(i);
                if (entry.getKey() == null) {
                    break;
                }
            }
        }
        else {
            for (i = 0; i < size; ++i) {
                entry = this.list.get(i);
                if (key.equals(entry.getKey())) {
                    break;
                }
            }
        }
        V oldValue = null;
        if (i < size) {
            oldValue = entry.getValue();
            entry.setValue(value);
        }
        else {
            this.list.add(new Entry<K, V>(key, value));
        }
        return oldValue;
    }
    
    static {
        ArrayMap.serialVersionUID = 9179541993413738569L;
    }
    
    public static class Entry<K, V> implements Map.Entry<K, V>, Serializable
    {
        public static long serialVersionUID;
        protected K key;
        protected V value;
        
        public Entry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public K getKey() {
            return this.key;
        }
        
        @Override
        public V getValue() {
            return this.value;
        }
        
        @Override
        public V setValue(final V newValue) {
            final V oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry e = (Map.Entry)o;
            if (this.key == null) {
                if (e.getKey() != null) {
                    return false;
                }
            }
            else if (!this.key.equals(e.getKey())) {
                return false;
            }
            if ((this.value != null) ? this.value.equals(e.getValue()) : (e.getValue() == null)) {
                return true;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            final int keyHash = (this.key == null) ? 0 : this.key.hashCode();
            final int valueHash = (this.value == null) ? 0 : this.value.hashCode();
            return keyHash ^ valueHash;
        }
        
        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
        
        static {
            Entry.serialVersionUID = 9179541993413738569L;
        }
    }
}
