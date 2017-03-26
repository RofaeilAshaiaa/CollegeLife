package rofaeil.ashaiaa.idea.collegelife.MainFragments.AddRemoveSubjects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rofaeil.ashaiaa.idea.collegelife.R;


public class AddRemoveSubjectsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.add_delete_subjects_fragment, container, false);
        return RootView;
    }
}
