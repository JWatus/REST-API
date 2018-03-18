package org.infoshare.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserStoreTest {

    @Test
    public void getBase() throws Exception {
    }

    @Test
    public void init() throws Exception {
        // given
        UserStore subject = new UserStore();
        // when
        subject.init();
        // then
        assertThat(subject.getBase().size(), is(2));
    }

    @Test
    public void add() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

}