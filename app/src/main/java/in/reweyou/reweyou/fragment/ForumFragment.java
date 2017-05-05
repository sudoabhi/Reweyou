package in.reweyou.reweyou.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.reweyou.reweyou.CreateActivity;
import in.reweyou.reweyou.R;
import in.reweyou.reweyou.adapter.FeeedsAdapter;
import in.reweyou.reweyou.model.IssueModel;

/**
 * Created by master on 24/2/17.
 */

public class ForumFragment extends Fragment {


    private static final String TAG = ForumFragment.class.getName();
    private Activity mContext;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_feeds, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CreateActivity.class));
            }
        });
        return layout;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            mContext = (Activity) context;
        else throw new IllegalArgumentException("Context should be an instance of Activity");
    }

    @Override
    public void onDestroy() {
        mContext = null;
        super.onDestroy();

    }

    public float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isAdded()) {
            final FeeedsAdapter feeedsAdapter = new FeeedsAdapter(mContext);
            recyclerView.setAdapter(feeedsAdapter);
            AndroidNetworking.get("https://reweyou.in/reviews/samplefeed.php")
                    .setTag("report")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsParsed(new TypeToken<List<IssueModel>>() {
                    }, new ParsedRequestListener<List<IssueModel>>() {

                        @Override
                        public void onResponse(final List<IssueModel> list) {
                            feeedsAdapter.add(list);
                        }

                        @Override
                        public void onError(final ANError anError) {
                            Log.e(TAG, "run: error: " + anError.getErrorDetail());


                        }
                    });

        }
    }


}
