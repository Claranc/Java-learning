package cache;

import java.util.HashMap;

public class LRUCache {
    public class Node {
        public int key;
        public int value;
        public Node next;
        public Node prev;
        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    private HashMap<Integer, Node> Map = new HashMap<>();
    private int cap;
    private int length = 0;
    private Node first;
    private Node tail;
    public LRUCache(int capacity) {
        cap = capacity;
    }

    public int get(int key) {
        Node p = Map.get(key);
        if(p == null || length == 0) {
            return -1;
        } else {
            if(p != first) {
                if(p == tail) {
                    tail = p.prev;
                }
                p.prev.next = p.next;
                if(p.next != null) {
                    p.next.prev = p.prev;
                }
                p.next = first;
                first.prev = p;
                first = p;
            }
            return p.value;
        }
    }

    public void put(int key, int value) {
        Node p = Map.get(key);
        if (p != null) {
            get(key);
            p.value = value;
        } else {
            Node node = new Node(key, value);
            if(length == 0) {
                first = node;
                tail = node;
            } else {
                node.next = first;
                first.prev = node;
                first = node;
            }
            length++;
            Map.put(key, node);
            while (length > cap) {
                Map.remove(tail.key);
                tail = tail.prev;
                tail.next = null;
                length--;
            }
        }
    }
}