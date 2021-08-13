package tools;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentEnumMap<K extends Enum<K>, V> extends EnumMap<K, V> implements Serializable
{
    private static final long serialVersionUID = 11920818021L;
    private final ReentrantReadWriteLock reentlock;
    private final Lock rL;
    private final Lock wL;
    
    public ConcurrentEnumMap(final Class<K> keyType) {
        super(keyType);
        this.reentlock = new ReentrantReadWriteLock();
        this.rL = this.reentlock.readLock();
        this.wL = this.reentlock.writeLock();
    }
    
    @Override
    public void clear() {
        this.wL.lock();
        try {
            super.clear();
        }
        finally {
            this.wL.unlock();
        }
    }
    
    @Override
    public EnumMap<K, V> clone() {
        return super.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public boolean containsKey(final Object key) {
        this.rL.lock();
        try {
            return super.containsKey(key);
        }
        finally {
            this.rL.unlock();
        }
    }
    
    @Override
    public boolean containsValue(final Object value) {
        this.rL.lock();
        try {
            return super.containsValue(value);
        }
        finally {
            this.rL.unlock();
        }
    }
    
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        this.rL.lock();
        try {
            return super.entrySet();
        }
        finally {
            this.rL.unlock();
        }
    }
    
    @Override
    public V get(final Object key) {
        this.rL.lock();
        try {
            return super.get(key);
        }
        finally {
            this.rL.unlock();
        }
    }
    
    @Override
    public Set<K> keySet() {
        this.rL.lock();
        try {
            return super.keySet();
        }
        finally {
            this.rL.unlock();
        }
    }
    
    @Override
    public V put(final K key, final V value) {
        this.wL.lock();
        try {
            return super.put(key, value);
        }
        finally {
            this.wL.unlock();
        }
    }
    
    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        this.wL.lock();
        try {
            super.putAll(m);
        }
        finally {
            this.wL.unlock();
        }
    }
    
    @Override
    public V remove(final Object key) {
        this.wL.lock();
        try {
            return super.remove(key);
        }
        finally {
            this.wL.unlock();
        }
    }
    
    @Override
    public int size() {
        this.rL.lock();
        try {
            return super.size();
        }
        finally {
            this.rL.unlock();
        }
    }
    
    @Override
    public Collection<V> values() {
        this.rL.lock();
        try {
            return super.values();
        }
        finally {
            this.rL.unlock();
        }
    }
}
