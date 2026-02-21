package leetcode.editor.cn;

import java.util.concurrent.*;
import java.util.Map.Entry;

public class ExpiringCa<K, V> {
    // 缓存存储键值对，以及对应的过期时间戳
    private final ConcurrentHashMap<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ExpiringCa() {
        // 定期清理过期条目
        scheduler.scheduleAtFixedRate(this::cleanUp, 1, 1, TimeUnit.SECONDS);
    }

    // 设置缓存值，并指定有效时间
    public void put(K key, V value, long ttl, TimeUnit unit) {
        long expiryTime = System.currentTimeMillis() + unit.toMillis(ttl);
        cache.put(key, new CacheEntry<>(value, expiryTime));
    }

    // 获取缓存值，过期返回null
    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            cache.remove(key);
            return null;
        }
        return entry.value;
    }

    // 删除缓存值
    public void remove(K key) {
        cache.remove(key);
    }

    // 清理过期条目
    private void cleanUp() {
        for (Entry<K, CacheEntry<V>> entry : cache.entrySet()) {
            if (entry.getValue().isExpired()) {
                cache.remove(entry.getKey());
            }
        }
    }

    // 关闭调度器
    public void shutDown() {
        scheduler.shutdown();
    }

    // 内部类表示缓存条目
    private static class CacheEntry<V> {
        final V value;
        final long expiryTime;

        CacheEntry(V value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    // 测试主程序
    public static void main(String[] args) throws InterruptedException {
        ExpiringCa<String, String> cache = new ExpiringCa<>();
        cache.put("key1", "value1", 3, TimeUnit.SECONDS);
        System.out.println("Initial value: " + cache.get("key1")); // Should print value1
        Thread.sleep(4000);
        System.out.println("After expiry: " + cache.get("key1")); // Should print null
        cache.shutDown();
    }
}
