package com.marcgdiez.napptilusdemo.app.list.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.marcgdiez.napptilusdemo.R;
import com.marcgdiez.napptilusdemo.entity.OompaLoompa;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class OompaLoompaAdapter
    extends RecyclerView.Adapter<OompaLoompaAdapter.OompaLoompaViewHolder> {

  private List<OompaLoompa> items;

  @Inject public OompaLoompaAdapter() {
    items = new ArrayList<>();
    setHasStableIds(true);
  }

  public void setItems(List<OompaLoompa> items) {
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public OompaLoompaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oompa_loompa, parent, false);
    return new OompaLoompaViewHolder(view);
  }

  @Override public void onBindViewHolder(OompaLoompaViewHolder holder, int position) {
    OompaLoompa oompaLoompa = items.get(position);
    Context context = holder.itemView.getContext();

    holder.name.setText(oompaLoompa.getName());
    Picasso.with(context)
        .load(oompaLoompa.getImage())
        .placeholder(R.mipmap.napptilus)
        .error(R.mipmap.napptilus)
        .into(holder.image);
    holder.profession.setText(oompaLoompa.getProfession());
    int drawable =
        oompaLoompa.getGender().equals("F") ? R.drawable.gender_female : R.drawable.gender_male;
    holder.gender.setImageDrawable(context.getResources().getDrawable(drawable));
  }

  @Override public int getItemCount() {
    return items != null ? items.size() : 0;
  }

  static class OompaLoompaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.backgroundImage) ImageView image;
    @BindView(R.id.gender) ImageView gender;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.profession) TextView profession;

    public OompaLoompaViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
