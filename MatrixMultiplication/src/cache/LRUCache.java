package cache;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private Lock mu = new ReentrantLock();

    public int get(int key) {
        mu.lock();
        //System.out.println("get: " + key);
        Node p = Map.get(key);
        if(p == null) {
            mu.unlock();
            return -1;
        }
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
        mu.unlock();
        return p.value;
    }

    public void put(int key, int value) {
        mu.lock();
        //System.out.println("put: " + key + " " + value);
        Node p = Map.get(key);
        if (p != null) {
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
            p.value = value;
            mu.unlock();
            return;
        }
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
            tail.next.prev = null;
            tail.next = null;
            length--;
        }
        mu.unlock();
//        Node cur = first;
//        for(int x = 0; x < length; x++) {
//            System.out.println(cur.key + " " + cur.next  + " " + cur.prev);
//            cur = cur.next;
//        }
    }
}