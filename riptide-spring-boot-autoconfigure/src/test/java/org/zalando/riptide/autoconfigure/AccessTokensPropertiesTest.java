package org.zalando.riptide.autoconfigure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DefaultTestConfiguration.class, webEnvironment = NONE)
@TestPropertySource(properties = "riptide.oauth.access-token-url: http://example.com")
public final class AccessTokensPropertiesTest {

    @Test
    public void shouldRun() {
        // if the application context is booting up, I'm happy
    }

}