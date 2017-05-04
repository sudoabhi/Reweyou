package in.reweyou.reweyou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;
import java.util.List;

import in.reweyou.reweyou.GroupActivity;
import in.reweyou.reweyou.R;
import in.reweyou.reweyou.model.ForumModel;

/**
 * Created by master on 1/5/17.
 */

public class ForumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    List<ForumModel> messagelist;

    public ForumAdapter(Context context) {
        this.context = context;
        this.messagelist = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ForumViewHolder forumViewHolder = (ForumViewHolder) holder;
        Glide.with(context).load(messagelist.get(position).getImage()).into(forumViewHolder.backgroundImage);
        forumViewHolder.groupName.setText(messagelist.get(position).getForum_name());
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<ForumModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        notifyDataSetChanged();
    }

    private class ForumViewHolder extends RecyclerView.ViewHolder {
        private ImageView backgroundImage;
        private TextView groupName;
        private TextView members;
        private TextView threads;
        private LinearLayout container;

        public ForumViewHolder(View inflate) {
            super(inflate);

            backgroundImage = (ImageView) inflate.findViewById(R.id.img);
            groupName = (TextView) inflate.findViewById(R.id.groupname);
            members = (TextView) inflate.findViewById(R.id.members);
            threads = (TextView) inflate.findViewById(R.id.threads);
            container = (LinearLayout) inflate.findViewById(R.id.container);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, GroupActivity.class));
                }
            });
        }
    }
}