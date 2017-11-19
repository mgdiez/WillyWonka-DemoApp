package com.marcgdiez.napptilusdemo.app.detail.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.marcgdiez.napptilusdemo.R;
import com.marcgdiez.napptilusdemo.app.activity.OompaLoompasActivity;
import com.marcgdiez.napptilusdemo.app.detail.di.component.OompaDetailComponent;
import com.marcgdiez.napptilusdemo.app.detail.di.module.OompaDetailModule;
import com.marcgdiez.napptilusdemo.app.detail.presenter.OompaLoompaDetailPresenter;
import com.marcgdiez.napptilusdemo.app.detail.view.OompaDetailView;
import com.marcgdiez.napptilusdemo.app.di.component.OompaLoompasComponent;
import com.marcgdiez.napptilusdemo.app.story.OompaLoompasStoryController;
import com.marcgdiez.napptilusdemo.core.presenter.Presenter;
import com.marcgdiez.napptilusdemo.core.story.StoryController;
import com.marcgdiez.napptilusdemo.core.view.fragment.RootFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

public class OompaLoompaDetailFragment extends RootFragment implements OompaDetailView {

  @Inject OompaLoompaDetailPresenter presenter;
  @Inject OompaLoompasStoryController storyController;
  @BindView(R.id.image) ImageView image;
  @BindView(R.id.id) TextView id;
  @BindView(R.id.name) TextView name;
  @BindView(R.id.profession) TextView profession;
  @BindView(R.id.gender) TextView gender;
  @BindView(R.id.email) TextView email;
  @BindView(R.id.description) TextView description;

  private static final String EXTRA_TRANSITION_NAME = "transition_name";

  public static Fragment newInstance(String transitionName) {
    OompaLoompaDetailFragment oompaLoompaDetailFragment = new OompaLoompaDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
    oompaLoompaDetailFragment.setArguments(bundle);
    return oompaLoompaDetailFragment;
  }

  @Override protected void initializePresenter() {
    presenter.setView(this);
  }

  @Override protected int getFragmentLayoutResourceId() {
    return R.layout.fragment_oompa_detail;
  }

  @Override protected void initializeView(View view) {
    if (getActivity() != null) {
      ((OompaLoompasActivity) getActivity()).showArrowToolbar();
    }
  }

  @Override protected void initializeTransition() {
    postponeEnterTransition();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setSharedElementEnterTransition(
          TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }
  }

  @Override protected void initializeInjector() {
    OompaDetailComponent oompaDetailComponent =
        getComponent(OompaLoompasComponent.class).oompaLoompaDetailComponent(
            new OompaDetailModule());

    oompaDetailComponent.inject(this);
  }

  @Override protected StoryController getStoryController() {
    return storyController;
  }

  @Override protected Presenter getPresenter() {
    return presenter;
  }

  @Override
  public void showOompa(int id, String name, String profession, String gender, String email,
      String description, String image) {

    Bundle arguments = getArguments();
    if (arguments != null) {
      String transitionName = arguments.getString(EXTRA_TRANSITION_NAME);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.image.setTransitionName(transitionName);
      }
    }

    this.id.setText(String.valueOf(id));
    this.name.setText(name);
    this.profession.setText(profession);
    this.gender.setText(gender);
    this.email.setText(email);
    this.description.setText(description);

    Picasso.with(getContext())
        .load(image)
        .noFade()
        .error(R.mipmap.napptilus)
        .into(this.image, new Callback() {
          @Override public void onSuccess() {
            startPostponedEnterTransition();
          }

          @Override public void onError() {
            startPostponedEnterTransition();
          }
        });
  }

}
