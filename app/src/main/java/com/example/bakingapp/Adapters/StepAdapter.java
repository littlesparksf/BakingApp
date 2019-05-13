package com.example.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.bakingapp.Pojos.Step;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Step> mStepList;
    private static final String LOG_TAG = StepAdapter.class.getSimpleName();

    public StepAdapter(Context context, ArrayList<Step> stepList) {
        this.mContext = context;
        this.mStepList = stepList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Step step = mStepList.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public List<Step> getRecipes() {
        return mStepList;
    }

    public void setSteps(List<Step> stepList) {
        mStepList = stepList;
        notifyDataSetChanged();
    }
}
