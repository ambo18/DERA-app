package com.app.dorav4.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.dorav4.R;

public class MapFragment extends Fragment {
    FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Set starting fragment to ReportsFragment
        fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mapContainer, new DisastersFragment());
        fragmentTransaction.commit();

        return view;
    }
}