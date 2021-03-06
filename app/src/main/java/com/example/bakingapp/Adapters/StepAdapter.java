package com.example.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.Activities.StepDetailActivity;
import com.example.bakingapp.Model.Step;
import com.example.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Context mContext;
    private ArrayList<Step> mStepList;
    private static final String LOG_TAG = StepAdapter.class.getSimpleName();

    public StepAdapter(Context context, ArrayList<Step> stepList) {
        this.mContext = context;
        this.mStepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.v(LOG_TAG, "onCreateViewHolder for steps called.");
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.step_list_row, viewGroup, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder viewHolder, int position) {
        Step stepListItem = mStepList.get(position);
        String stepShortDescription = stepListItem.getShortDescription();
        String stepLongDescription = stepListItem.getDescription();

        viewHolder.stepShortDescriptionTextView.setText(stepShortDescription);
        viewHolder.stepLongDescriptionTextView.setText(stepLongDescription);
        Log.v(LOG_TAG, "onBindViewHolder for Steps called.");
    }

    @Override
    public int getItemCount() {
        Log.v(LOG_TAG, "getItemCount called in Step Adapter.");
        return mStepList != null ? mStepList.size() : 0;
    }

    public List<Step> getSteps() {
        Log.v(LOG_TAG, "getSteps called in Step Adapter.");
        return mStepList;
    }

    public void setSteps(ArrayList<Step> stepList) {
        mStepList = stepList;
        notifyDataSetChanged();
        Log.v(LOG_TAG, "setSteps called in Step Adapter.");
    }
    // Inner class for creating ViewHolders
    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the author and content TextViews
        TextView stepShortDescriptionTextView;
        TextView stepLongDescriptionTextView;

        public StepViewHolder(View itemView) {
            super(itemView);

            stepShortDescriptionTextView = itemView.findViewById(R.id.step_list_short_description);
            stepLongDescriptionTextView = itemView.findViewById(R.id.step_list_long_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int stepPosition = getAdapterPosition();
            Step step = mStepList.get(stepPosition);

            Bundle b = new Bundle();
            b.putParcelable("step", step); // maybe this is redundant if I have mStepList already
            b.putInt("step_position", stepPosition);
            b.putParcelableArrayList("steps", mStepList);

            // Intent opens StepDetailActivity
            final Intent intent = new Intent(mContext, StepDetailActivity.class);
            intent.putExtras(b);

            mContext.startActivity(intent);
        }
    }
}
