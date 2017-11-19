package com.marcgdiez.napptilusdemo.data.oompaloompa.repository;

import com.marcgdiez.napptilusdemo.data.oompaloompa.exception.OompaLoompaRepositoryException;
import com.marcgdiez.napptilusdemo.data.oompaloompa.local.OompaLoompaDataStore;
import com.marcgdiez.napptilusdemo.data.oompaloompa.network.OompaLoompaDataSource;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.marcgdiez.napptilusdemo.entity.OompaLoompaPage;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OompaLoompaRepositoryShould {

  private TestSubscriber<OompaLoompaPage> testOompaLoompaPageSubscriber;
  private TestSubscriber<OompaLoompa> testOompaLoompaSubscriber;

  private OompaLoompaDataStore mockDataStore;
  private OompaLoompaDataSource mockDataSource;
  private OompaLoompaRepository oompaLoompaRepository;

  private OompaLoompaPage fakeOompaLoompaPage;
  private OompaLoompa fakeOompaLompa;

  @Before public void setUp() {
    testOompaLoompaPageSubscriber = TestSubscriber.create();
    testOompaLoompaSubscriber = TestSubscriber.create();

    mockDataStore = Mockito.mock(OompaLoompaDataStore.class);
    mockDataSource = Mockito.mock(OompaLoompaDataSource.class);
    oompaLoompaRepository = new OompaLoompaRepositoryImpl(mockDataStore, mockDataSource);

    fakeOompaLompa = createFakeOompa();
    fakeOompaLoompaPage = createFakeOompaLoompaPage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void throw_exception_on_invalid_arguments() {
    new OompaLoompaRepositoryImpl(null, null);
  }

  @Test public void throw_exception_on_invalid_oompa_id() {
    oompaLoompaRepository.getOompaLoompa(-1).subscribe(testOompaLoompaSubscriber);
    testOompaLoompaSubscriber.assertError(OompaLoompaRepositoryException.class);
  }

  @Test public void throw_exception_on_invalid_oompas_page() {
    oompaLoompaRepository.getOompaLoompas(0).subscribe(testOompaLoompaPageSubscriber);
    testOompaLoompaPageSubscriber.assertError(OompaLoompaRepositoryException.class);
  }

  @Test public void avoid_crash_if_network_is_not_available_oompas_page() {
    when(mockDataSource.getOompaLoompas(1)).thenReturn(Observable.error(new IOException()));
    when(mockDataStore.getOompaLoompas(1)).thenReturn(Observable.just(fakeOompaLoompaPage));

    oompaLoompaRepository.getOompaLoompas(1).subscribe(testOompaLoompaPageSubscriber);

    testOompaLoompaPageSubscriber.assertNoErrors();
    testOompaLoompaPageSubscriber.assertCompleted();
    testOompaLoompaPageSubscriber.assertValue(fakeOompaLoompaPage);
  }

  @Test public void send_error_if_description_not_available() {
    when(mockDataSource.getOompaLoompaDetail(1)).thenReturn(Observable.error(new IOException()));
    when(mockDataStore.getOompaLoompaDetail(1)).thenReturn(
        Observable.just(Mockito.mock(OompaLoompa.class)));

    oompaLoompaRepository.getOompaLoompa(1).subscribe(testOompaLoompaSubscriber);

    testOompaLoompaSubscriber.assertTerminalEvent();
    testOompaLoompaSubscriber.assertNotCompleted();
  }

  @Test public void send_local_information_first() {
    when(mockDataSource.getOompaLoompaDetail(1)).thenReturn(Observable.error(new IOException()));
    when(mockDataStore.getOompaLoompaDetail(1)).thenReturn(Observable.just(fakeOompaLompa));

    oompaLoompaRepository.getOompaLoompa(1).subscribe(testOompaLoompaSubscriber);

    testOompaLoompaSubscriber.assertNoErrors();
    testOompaLoompaSubscriber.assertCompleted();
    testOompaLoompaSubscriber.assertValue(fakeOompaLompa);
  }

  @Test public void request_network_if_description_not_local() {
    when(mockDataSource.getOompaLoompaDetail(1)).thenReturn(Observable.just(fakeOompaLompa));
    when(mockDataStore.getOompaLoompaDetail(1)).thenReturn(
        Observable.just(Mockito.mock(OompaLoompa.class)));

    oompaLoompaRepository.getOompaLoompa(1).subscribe(testOompaLoompaSubscriber);

    testOompaLoompaSubscriber.assertNoErrors();
    testOompaLoompaSubscriber.assertCompleted();
    testOompaLoompaSubscriber.assertValue(fakeOompaLompa);
  }

  @Test public void persist_page_from_network() {
    when(mockDataSource.getOompaLoompas(1)).thenReturn(Observable.just(fakeOompaLoompaPage));
    when(mockDataStore.getOompaLoompas(1)).thenReturn(Observable.empty());

    oompaLoompaRepository.getOompaLoompas(1).subscribe(testOompaLoompaPageSubscriber);

    testOompaLoompaPageSubscriber.assertNoErrors();
    testOompaLoompaPageSubscriber.assertCompleted();
    testOompaLoompaPageSubscriber.assertValue(fakeOompaLoompaPage);
    verify(mockDataStore).persistOompas(fakeOompaLoompaPage);
  }

  @Test public void persist_oompa_details_from_network() {
    when(mockDataSource.getOompaLoompaDetail(1)).thenReturn(Observable.just(fakeOompaLompa));
    when(mockDataStore.getOompaLoompaDetail(1)).thenReturn(
        Observable.just(Mockito.mock(OompaLoompa.class)));

    oompaLoompaRepository.getOompaLoompa(1).subscribe(testOompaLoompaSubscriber);

    testOompaLoompaSubscriber.assertNoErrors();
    testOompaLoompaSubscriber.assertCompleted();
    testOompaLoompaSubscriber.assertValue(fakeOompaLompa);
    verify(mockDataStore).persistOompa(fakeOompaLompa);
  }

  private OompaLoompa createFakeOompa() {
    return new OompaLoompa(1, "Marc", "M", "image", "Developer", "marcgdiez@gmail.com",
        "Descriptions");
  }

  private OompaLoompaPage createFakeOompaLoompaPage() {
    return new OompaLoompaPage(1, 5, new ArrayList<>());
  }
}