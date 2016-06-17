package com.sourav.loktraandroidtest.activities;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sourav.loktraandroidtest.R;
import com.sourav.loktraandroidtest.helpers.beans.Ajaxhelper;
import com.sourav.loktraandroidtest.helpers.beans.response.CommitResponseBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity {

    private RecyclerView commitRecyclerView;

    private EditText search;

    ArrayList<CommitResponseBean> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        commitRecyclerView = (RecyclerView) findViewById(R.id.commitrecyclerview);
        search = (EditText) findViewById(R.id.searchEdittext);
        new GetResponse().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<CommitResponseBean> search = filter(s.toString(),response);
                CommitAdapter commitAdapter = new CommitAdapter(search);
                commitRecyclerView.setAdapter(commitAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public ArrayList<CommitResponseBean> filter (String s, ArrayList<CommitResponseBean> response) {
        ArrayList<CommitResponseBean> searchresult = new ArrayList<>();
        for(int i=0; i<response.size(); i++) {
            if(response.get(i).getCommit().getAuthor().getName().contains(s) || response.get(i).getCommit().getMessage().contains(s)) {
                searchresult.add(response.get(i));
            }
        }
        return  searchresult;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    class GetResponse extends AsyncTask<Void, Void, ArrayList<CommitResponseBean>> {
        @Override
        protected ArrayList<CommitResponseBean> doInBackground(Void... params) {
            response = Ajaxhelper.ajax();
            return response;
        }

        @Override
        protected void onPostExecute(ArrayList<CommitResponseBean> commitResponseBeen) {
            super.onPostExecute(commitResponseBeen);
            CommitAdapter commitAdapter = new CommitAdapter(commitResponseBeen);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            commitRecyclerView.setLayoutManager(mLayoutManager);
            commitRecyclerView.setItemAnimator(new DefaultItemAnimator());
            commitRecyclerView.setAdapter(commitAdapter);
        }
    }

    public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.MyViewHolder> {

        private ArrayList<CommitResponseBean> commitList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView commitname, committername, commitdate;
            public ImageView profilepic;

            public MyViewHolder(View view) {
                super(view);
                commitname = (TextView) view.findViewById(R.id.commitname);
                committername = (TextView) view.findViewById(R.id.committername);
                commitdate = (TextView) view.findViewById(R.id.commitdate);
               // profilepic = (ImageView) view.findViewById(R.id.profileImageView);
            }
        }


        public CommitAdapter(ArrayList<CommitResponseBean> commitList) {
            this.commitList = commitList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_commit, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            CommitResponseBean commit = commitList.get(position);
            holder.committername.setText(commit.getCommit().getAuthor().getName());
            holder.commitname.setText(commit.getCommit().getMessage());
            holder.commitdate.setText(commit.getCommit().getCommitter().getDate());
           // Picasso.with(getApplicationContext()).load("https://avatars.githubusercontent.com/u/" + commit.getAuthor().getId() + "?v=3").into(holder.profilepic);
        }

        @Override
        public int getItemCount() {
            return commitList.size();
        }

    }
}
