package nus.iss.practicetest.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.practicetest.utils.Util;

@Repository
public class TaskRepo {
    
    @Autowired
    @Qualifier(Util.REDIS_STRING)
    RedisTemplate<String, String> template;

    HashOperations<String, String, String> hashOps;

    // CRUD
    @SuppressWarnings("null")
    public void createTask(String taskId, String task) {
        hashOps = template.opsForHash();
        hashOps.put(Util.TASK_DB, taskId, task);
    }

    @SuppressWarnings("null")
    public String retrieveTask(String taskId) {
        hashOps = template.opsForHash();
        return hashOps.get(Util.TASK_DB, taskId);
    }

    public List<String> retrieveTasks() {
        hashOps = template.opsForHash();
        return hashOps.values(Util.TASK_DB);
    }

    @SuppressWarnings("null")
    public void updateTask(String taskId, String task) {
        hashOps = template.opsForHash();
        hashOps.put(Util.TASK_DB, taskId, task);
    }

    public void deleteTask(String taskId) {
        hashOps = template.opsForHash();
        hashOps.delete(Util.TASK_DB, taskId);
    }

}
