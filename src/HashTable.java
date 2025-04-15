public class HashTable {
    static class Entry {
        int hash;
        Object key;
        Object value;
        Entry next;

        public Entry(int hash, Object key, Object value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }

    Entry[] table = new Entry[16];
    int size = 0;
    float loadFactor = 0.75f;
    int threshold = (int) (loadFactor * table.length);

    // 根据hash码获取value
    Object get(int hash, Object key) {
        int index = hash & (table.length - 1);
        Entry p = table[index];
        while (p != null) {
            if (p.key.equals(key)) {
                return p.value;
            }
             p = p.next;
        }
        return null;
    }

    // 向哈希表中存入新key，value，如果key重复则更新value
    void put(int hash, Object key, Object value) {
        int index = hash & (table.length - 1);
        Entry p = table[index];
        while (p != null) {
            if (p.key.equals(key)) {
                p.value = value;
                return;
            }
            p = p.next;
        }
        if (p == null) {
            table[index] = new Entry(hash, key, value);
        } else {
            p.next = new Entry(hash, key, value);
        }
        size++;
        if (size > threshold) {
            resize();
        }
    }

    Object remove(int hash, Object key) {
        int index = hash & (table.length - 1);
        Entry p = table[index];
        Entry p1 = null;
        while (p != null) {
            if (p.key.equals(key)) {
                if (p1 == null) {
                    table[index] = p.next;
                } else {
                    p1.next = p.next;
                }
                size--;
                return p.value;
            }
            p1 = p;
            p = p.next;
        }
        return null;
    }

    public void resize() {
        Entry[] newTable = new Entry[table.length << 1];
        for (int i = 0; i < table.length; i++) {
            Entry p = table[i];
            if (p != null) {
                Entry a = null;
                Entry b = null;
                Entry aHead = null;
                Entry bHead = null;
                while (p != null) {
                    if ((p.hash & table.length) == 0) {
                       if (a != null) {
                           a.next = p;
                       } else {
                           aHead = p;
                       }
                       a = p;
                    } else {
                        if (b != null) {
                            b.next = p;
                        } else {
                            bHead = p;
                        }
                        b = p;
                    }
                    p = p.next;
                }
                if (a != null) {
                    a.next = null;
                    newTable[i] = aHead;
                }
                if (b != null) {
                    b.next = null;
                    newTable[i + table.length] = bHead;
                }
            }
        }
        table = newTable;
        threshold = (int) (loadFactor * table.length);
    }

    public Object get(Object key) {
        int hash = key.hashCode();
        return get(hash, key);
    }

    public void put(Object key, Object value) {
        int hash = key.hashCode();
        put(hash, key, value);
    }

    public Object remove(Object key) {
        int hash = key.hashCode();
        return remove(hash, key);
    }


    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = new String("abc");

        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
    }
}
