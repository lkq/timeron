package integration.com.github.klq.timeron;

import com.github.lkq.timeron.Timer;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeronIntegrationTest {
    @Test
    void testAll() {
        Timer timer = new Timer();
        Son son = timer.intercept(Son.class);
        timer.measure(son.tagInSon(""));

        Son kingson = timer.on(new Son("kingson"));
        String retVal = kingson.tagInSon("test");

        assertThat(retVal, is("tagInSon-test"));
    }
}
