package com.example.bakingapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

public class MasterListFragment extends Fragment {
    View.OnClickListener mCallback;

    public interface onClickListener {
        void onListItemSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (View.OnClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                + " must implement OnClickListener");
    }
}

    // Mandatory empty constructor
    public MasterListFragment() {
    }

}
