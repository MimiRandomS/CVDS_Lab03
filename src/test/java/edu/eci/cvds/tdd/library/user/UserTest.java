package edu.eci.cvds.tdd.library.user;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void shouldValidateInfoUser() {
        User user = new User();
        user.setName("Juan");
        user.setId("1234");
        assertTrue(user.getName().equals("Juan"));
        assertTrue(user.getId().equals("1234"));
    }
}
