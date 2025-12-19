package ru.mirea.gracheva.ringstore.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.mirea.gracheva.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.ItemMetalPriceBinding;

public class MetalPriceAdapter extends RecyclerView.Adapter<MetalPriceAdapter.ViewHolder> {
    private List<MetalPriceInfo> metalList;
    public  MetalPriceAdapter(List<MetalPriceInfo> metalList){
        this.metalList = metalList;
    }

    public void updateData(List<MetalPriceInfo> newMetalList) {
        metalList = newMetalList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemMetalPriceBinding binding;

        ViewHolder(ItemMetalPriceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MetalPriceInfo info) {
            binding.metalName.setText(info.getMetalName());
            binding.price.setText(String.valueOf(info.getPrice()));
            binding.lastUpdated.setText(info.getLastUpdated());

            Picasso.get()
                    .load(info.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .fit()
                    .into(binding.metalImageView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMetalPriceBinding binding = ItemMetalPriceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(metalList.get(position));
    }

    @Override
    public int getItemCount() {
        return metalList.size();
    }
}
