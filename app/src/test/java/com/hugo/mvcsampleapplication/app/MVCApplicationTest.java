package com.hugo.mvcsampleapplication.app;

import com.hugo.mvcsampleapplication.utils.dependencyinjection.components.ApplicationComponent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by hugo on 2/25/16.
 */
public class MVCApplicationTest {

  @Mock
  private ApplicationComponent applicationComponent;
  private MVCApplication mvcApplication;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mvcApplication = new MVCApplication();
  }

  @Test
  public void testSetAndGetApplicationComponent() throws Exception {
    ApplicationComponent nullApplicationComponent = mvcApplication.getApplicationComponent();
    assertNull(nullApplicationComponent);

    mvcApplication.setApplicationComponent(applicationComponent);
    applicationComponent = mvcApplication.getApplicationComponent();
    assertNotNull(applicationComponent);
  }


}