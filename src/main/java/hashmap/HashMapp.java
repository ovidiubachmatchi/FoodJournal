package hashmap;

import java.util.LinkedList;

public class HashMapp {
    LinkedList<Entry>[] hashMap = new LinkedList[2];
    int size = 0;
    public void put(Key key, Value value) {
        if (size >= hashMap.length) resize();
    int ix = getIndex(key) % hashMap.length;
    if (hashMap[ix] == null) {
        hashMap[ix] = new LinkedList<>();
        hashMap[ix].add(new Entry(key, value));
        size++;
        return;
    }
    else {
        for(Entry entry : hashMap[ix]) {
            if (entry.key.equals(key)) {
                entry.value=value;
                size++;
                return;
            }
        }
    }
    }
    public Value get(Key key) {
        int ix = getIndex(key) % hashMap.length;
        if (hashMap[ix] == null) return null;
        for (Entry entry : hashMap[ix])
            if (entry.key.equals(key))
                return entry.value;
    return null;
    }
    public void remove(Key key) {
        if (key == null) return;
        int ix = getIndex(key) % hashMap.length;
        if (hashMap[ix] == null) return;
        Entry toRemove = null;
        for (Entry entry : hashMap[ix])
            if (entry.key.equals(key))
                {toRemove = entry; break;}
    if (hashMap[ix] == null)
        return;
    hashMap[ix].remove(toRemove);
    }
    public boolean containsKey(Key key) {
        if (key == null) return false;
        int ix = getIndex(key) % hashMap.length;
        if (hashMap[ix] == null) return false;
        for (Entry entry : hashMap[ix])
            if (entry.key.equals(key))
                return true;
        return false;
    }
    public void resize() {
        LinkedList<Entry>[] oldHashMap = hashMap;
        hashMap = new LinkedList[size*2];
        size = 0;
        for (int i = 0; i<oldHashMap.length; i++)
        {
            if (oldHashMap[i] == null) continue;
            for(Entry entry : oldHashMap[i])
                put(entry.key, entry.value);
        }
    }
    public int getIndex(Key key)
    {
        return key.hashCode();
    }
    public int size() { return size; }
}
