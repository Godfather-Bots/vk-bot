//package org.sadtech.vkbot.core.repository.impl;
//
//import com.google.gson.JsonObject;
//import org.sadtech.vkbot.core.repository.RawEventRepository;
//
//import java.util.Queue;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//public class RawEventRepositoryQueue implements RawEventRepository {
//
//    private final Queue<JsonObject> jsonObjects = new ConcurrentLinkedQueue<>();
//
//    @Override
//    public void add(JsonObject jsonObject) {
//        jsonObjects.offer(jsonObject);
//    }
//
//    @Override
//    public void cleanAll() {
//        jsonObjects.clear();
//    }
//
//    public Set<JsonObject> getEventQueue() {
//        return jsonObjects;
//    }
//}
