package com.example.personmanagment.domain.service;

import java.util.List;

public class TestClass {

    static List<Task> TASKS;


        public static int getTaskId() {
            return TASKS.isEmpty() ? 1 : TASKS.get(TASKS.size() - 1).id + 1;
        }


    private static class Task {
        int id;
        String name;
    }

}
