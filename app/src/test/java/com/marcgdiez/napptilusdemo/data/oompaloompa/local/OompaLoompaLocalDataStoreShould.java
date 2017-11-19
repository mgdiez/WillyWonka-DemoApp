package com.marcgdiez.napptilusdemo.data.oompaloompa.local;

import com.marcgdiez.napptilusdemo.data.VoMapper;
import io.realm.Realm;
import io.realm.log.RealmLog;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(RobolectricTestRunner.class)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({ Realm.class, RealmLog.class }) public class OompaLoompaLocalDataStoreShould {

  @Rule public PowerMockRule rule = new PowerMockRule();
  Realm mockRealm;

  @Mock VoMapper voMapper;
  private OompaLoompaLocalDataStore dataStore;

  @Before public void setUp() {
    mockStatic(RealmLog.class);
    mockStatic(Realm.class);
    Realm mockRealm = PowerMockito.mock(Realm.class);
    when(Realm.getDefaultInstance()).thenReturn(mockRealm);
    this.mockRealm = mockRealm;

    MockitoAnnotations.initMocks(this);
    dataStore = new OompaLoompaLocalDataStore(voMapper);
  }

  @Test(expected = IllegalArgumentException.class) public void throw_exception_at_null_mapper() {
    new OompaLoompaLocalDataStore(null);
  }

  @Test public void get_default_instance_ok() {
    assertThat(Realm.getDefaultInstance(), is(mockRealm));
  }
}