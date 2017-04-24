package org.zalando.riptide;

import java.util.Arrays;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.http.HttpStatus.Series.SUCCESSFUL;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.zalando.riptide.Bindings.on;
import static org.zalando.riptide.Navigators.series;
import static org.zalando.riptide.Route.pass;

@RunWith(Parameterized.class)
public class BaseUrlQueryTest {

    private final Rest unit;
    private final MockRestServiceServer server;

    private final HttpMethod method;
    private final Executor executor;

    public BaseUrlQueryTest(final HttpMethod method, final Executor executor) {
        final MockSetup setup = new MockSetup("https://api.example.com/api");
        this.unit = setup.getRest();
        this.server = setup.getServer();

        this.method = method;
        this.executor = executor;
    }

    interface Executor {
        Requester execute(Rest client);
    }

    @Parameterized.Parameters(name= "{0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {HttpMethod.GET, (Executor) Rest::get},
                {HttpMethod.HEAD, (Executor) Rest::head},
                {HttpMethod.POST, (Executor) Rest::post},
                {HttpMethod.PUT, (Executor) Rest::put},
                {HttpMethod.PATCH, (Executor) Rest::patch},
                {HttpMethod.DELETE, (Executor) Rest::delete},
                {HttpMethod.OPTIONS, (Executor) Rest::options},
                {HttpMethod.TRACE, (Executor) Rest::trace},
        });
    }

    @After
    public void tearDown() {
        server.verify();
    }

    @Test
    public void shouldQueryBaseURL() {
        server.expect(requestTo("https://api.example.com/api"))
                .andExpect(method(method))
                .andRespond(withSuccess());

        executor.execute(unit)
                .dispatch(series(),
                        on(SUCCESSFUL).call(pass()));
    }

}
