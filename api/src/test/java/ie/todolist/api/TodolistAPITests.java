package ie.todolist.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodolistAPITests {


    @BeforeEach
    public void setup(){
        System.out.println("setting up");
    }

    @Test
    public void listAllTodolists(){

        assert(true);
    }
}
