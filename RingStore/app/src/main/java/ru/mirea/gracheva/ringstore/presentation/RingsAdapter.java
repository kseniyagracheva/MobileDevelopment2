package ru.mirea.gracheva.ringstore.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.ringstore.databinding.ItemCardRingBinding;

public class RingsAdapter extends RecyclerView.Adapter<RingsAdapter.RingViewHolder>{
    private List<Ring> rings;

    public RingsAdapter(List<Ring> rings) {
        this.rings = rings;
    }

    public static class RingViewHolder extends RecyclerView.ViewHolder {
        private final ItemCardRingBinding binding;

        public RingViewHolder(ItemCardRingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Ring ring) {
            binding.metalTextView.setText(ring.getMetal());
            binding.priceTextView.setText(ring.getPrice() + " руб.");
        }
    }
    @NonNull
    @Override
    public RingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCardRingBinding binding = ItemCardRingBinding.inflate(inflater, parent, false);
        return new RingViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull RingViewHolder holder, int position) {
        holder.bind(rings.get(position));
    }
    @Override
    public int getItemCount() {
        return rings.size();
    }
}
