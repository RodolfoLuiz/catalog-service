package com.catalog.infrastructure;

import com.catalog.application.UseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void test() {
        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});

    }
}
