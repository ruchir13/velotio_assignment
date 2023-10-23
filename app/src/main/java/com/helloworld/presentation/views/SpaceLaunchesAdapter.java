package com.helloworld.presentation.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.helloworld.AppUtils;
import com.helloworld.R;
import com.helloworld.databinding.ItemSpaceLaunchBinding;
import com.helloworld.domain.entities.SpacexLaunch;

import java.util.ArrayList;
import java.util.List;

public class SpaceLaunchesAdapter extends RecyclerView.Adapter<SpaceLaunchesAdapter.ViewHolder> {
    private List<SpacexLaunch> spaceLaunches;
    private OnItemClickListener onClick;

    public SpaceLaunchesAdapter(OnItemClickListener onClick) {
        this.onClick = onClick;
        this.spaceLaunches = new ArrayList<>();
    }

    public void setData(List<SpacexLaunch> launches) {
        spaceLaunches = launches;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(SpacexLaunch launch);

        void onFabItemClick(SpacexLaunch launch);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSpaceLaunchBinding binding;

        public ViewHolder(ItemSpaceLaunchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SpacexLaunch item) {
            binding.missionName.setText(item.getName());
            binding.launchStatus.setText(item.isSuccess() ? R.string.launch_success : R.string.launch_failure);
            binding.rocketName.setText(item.getRocketModel().getName());
            binding.launchDate.setText(AppUtils.getDate(item.getDate_utc()));
            AppUtils.loadThumbImage(item.getLinks().getPatch().getSmall(), binding.missionPatchImage);
            binding.favButton.setImageResource(item.isFavorite() ? android.R.drawable.star_on : android.R.drawable.star_off);
            binding.favButton.setOnClickListener(v -> {
                if (onClick != null) {
                    item.setFavorite(!item.isFavorite());
                    notifyItemChanged(getAdapterPosition());
                    onClick.onFabItemClick(item);
                }
            });
            binding.getRoot().setOnClickListener(v -> {
                if (onClick != null) {
                    onClick.onItemClick(item);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSpaceLaunchBinding binding = ItemSpaceLaunchBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpacexLaunch rocket = spaceLaunches.get(position);
        holder.bind(rocket);
    }

    @Override
    public int getItemCount() {
        return spaceLaunches.size();
    }
}
