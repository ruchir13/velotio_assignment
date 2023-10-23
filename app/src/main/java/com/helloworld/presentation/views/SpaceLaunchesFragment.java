package com.helloworld.presentation.views;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.helloworld.R;
import com.helloworld.data.Status;
import com.helloworld.databinding.FragmentSpaceLaunchesBinding;
import com.helloworld.domain.entities.SpacexLaunch;
import com.helloworld.presentation.viewmodel.SpacexLaunchViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SpaceLaunchesFragment extends Fragment {
    private FragmentSpaceLaunchesBinding binding;
    private SpacexLaunchViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SpacexLaunchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSpaceLaunchesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.listLaunches(true);
        });
        setupAdapter();
        observeSpaceXLaunches();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupAdapter() {
        binding.spaceLaunchesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.spaceLaunchesRecyclerView.setHasFixedSize(true);
        SpaceLaunchesAdapter adapter = new SpaceLaunchesAdapter(new SpaceLaunchesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SpacexLaunch launch) {
                viewModel.setSpaceLaunch(launch);
                findNavController(requireView()).navigate(R.id.action_spaceLaunches_to_spaceLauncheDetail);
            }

            @Override
            public void onFabItemClick(SpacexLaunch launch) {
                viewModel.setFabStatus(launch);
            }
        });
        binding.spaceLaunchesRecyclerView.setAdapter(adapter);
    }

    private void observeSpaceXLaunches() {
        viewModel.listLaunches(false).observe(getViewLifecycleOwner(), result -> {
            if (result.status == Status.LOADING) {
                binding.swipeRefreshLayout.setRefreshing(true);
            } else if (result.status == Status.SUCCESS) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.spaceLaunchesRecyclerView.setVisibility(View.VISIBLE);
                SpaceLaunchesAdapter adapter = (SpaceLaunchesAdapter) binding.spaceLaunchesRecyclerView.getAdapter();
                if (adapter != null) {
                    adapter.setData(result.data);
                }
            } else if (result.status == Status.ERROR) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.spaceLaunchesRecyclerView.setVisibility(View.GONE);
            }
        });
    }
}
