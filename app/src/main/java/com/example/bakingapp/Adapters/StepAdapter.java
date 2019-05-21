package com.example.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.Pojos.Step;
import com.example.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Context mContext;
    private List<Step> mStepList;
    private static final String LOG_TAG = StepAdapter.class.getSimpleName();

    public StepAdapter(Context context, ArrayList<Step> stepList) {
        this.mContext = context;
        this.mStepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.step_list_row, viewGroup, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder viewHolder, int position) {
        Log.v(LOG_TAG, "onBindViewHolder for Reviews called.");
        Step stepListItem = mStepList.get(position);
        String stepShortDescription = stepListItem.getShortDescription();
        String stepLongDescription = stepListItem.getDescription();

        viewHolder.stepShortDescriptionTextView.setText(stepShortDescription);
        viewHolder.stepLongDescriptionTextView.setText(stepLongDescription);
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

// Here you would go to either step detail or video I think
//            String url = step.getReviewUrl();
//            Intent reviewUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            mContext.startActivity(reviewUrlIntent);
        }
    }
}
