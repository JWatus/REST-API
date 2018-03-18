package ws.client;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GoogleTranslateTest {

    final String key = "";

    @Test
    public void should_translate_with_google() {
        final String input = "Hi my name is John";
        final String output = "Cześć, mam na imię John";

        GoogleTranslate t = new GoogleTranslate(key);
        assertThat(t.translationFor(input), equalTo(output));
    }


}