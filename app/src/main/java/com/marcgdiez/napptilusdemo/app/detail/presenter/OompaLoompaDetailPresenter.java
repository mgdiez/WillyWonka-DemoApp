package com.marcgdiez.napptilusdemo.app.detail.presenter;

import android.content.Intent;
import android.net.Uri;
import com.marcgdiez.napptilusdemo.R;
import com.marcgdiez.napptilusdemo.app.detail.usecase.GetOompaDetailUseCase;
import com.marcgdiez.napptilusdemo.app.detail.view.OompaDetailView;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasState;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.interactor.Interactor;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.subscriber.DefaultSubscriber;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import javax.inject.Inject;

public class OompaLoompaDetailPresenter extends Presenter<OompaDetailView> {
  private final GetOompaDetailUseCase getOompasLoompasUseCase;
  private final OompaLoompasStoryController oompaLoompasStoryController;

  @Inject public OompaLoompaDetailPresenter(Interactor<OompaLoompa> getOompasLoompasUseCase,
      OompaLoompasStoryController oompaLoompasStoryController) {

    this.getOompasLoompasUseCase = (GetOompaDetailUseCase) getOompasLoompasUseCase;
    this.oompaLoompasStoryController = oompaLoompasStoryController;
  }

  @Override protected void initialize() {
    OompaLoompa selectedOompa = oompaLoompasStoryController.getStoryState().getSelectedOompa();
    if (selectedOompa.getDescription() != null && !selectedOompa.getDescription().isEmpty()) {
      showOompa(selectedOompa);
    } else {
      getOompasLoompasUseCase.execute(selectedOompa.getId(), new DefaultSubscriber<OompaLoompa>() {
        @Override public void onNext(OompaLoompa oompaLoompa) {
          super.onNext(oompaLoompa);
          OompaLoompasState storyState = oompaLoompasStoryController.getStoryState();
          storyState.setSelectedOompa(oompaLoompa);
          showOompa(oompaLoompa);
        }

        @Override protected void onError(String errorMessage) {
          System.out.println(errorMessage);
        }
      });
    }
  }

  private void showOompa(OompaLoompa selectedOompa) {
    view.showOompa(selectedOompa.getId(), selectedOompa.getName(), selectedOompa.getProfession(),
        selectedOompa.getGender(), selectedOompa.getEmail(), selectedOompa.getDescription(),
        selectedOompa.getImage());
  }

  @Override public void stop() {
    getOompasLoompasUseCase.unsubcribe();
  }

  public void onEmailButtonClicked() {
    Intent intent = new Intent(Intent.ACTION_SENDTO);
    intent.setData(Uri.parse(
        "mailto:" + oompaLoompasStoryController.getStoryState().getSelectedOompa().getEmail()));
    intent.putExtra(Intent.EXTRA_SUBJECT,
        view.getContext().getResources().getString(R.string.email_subject));
    if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
      view.getContext().startActivity(intent);
    }
  }
}
