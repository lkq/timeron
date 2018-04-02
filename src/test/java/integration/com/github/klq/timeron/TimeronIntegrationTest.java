package integration.com.github.klq.timeron;

import com.github.lkq.timeron.Timer;
import com.github.lkq.timeron.hierarchy.lv3.Son;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeronIntegrationTest {
    @Test
    void testAll() throws JSONException {
        Timer timer = new Timer();
        Son son = timer.intercept(Son.class);
        timer.measure(son.tagInSon(""));

        Son kingson = timer.on(new Son("kingson"));
        String retVal = kingson.tagInSon("test");

        assertThat(retVal, is("tagInSon-test"));

        String stats = timer.getStats();
        JSONAssert.assertEquals("[{\"com.github.lkq.timeron.hierarchy.lv3.Son.tagInSon\":{\"avg\":1234}}]", stats,
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("[0].com.github.lkq.timeron.hierarchy.lv3.Son.tagInSon.avg", (o1, o2) -> ((int)o2) > 0)));
    }
}
