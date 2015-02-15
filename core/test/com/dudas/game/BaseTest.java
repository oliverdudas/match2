package com.dudas.game;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by OLO on 15. 2. 2015
 */

public abstract class BaseTest {

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
