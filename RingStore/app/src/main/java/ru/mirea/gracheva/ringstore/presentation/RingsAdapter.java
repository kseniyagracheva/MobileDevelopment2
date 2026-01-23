package ru.mirea.gracheva.ringstore.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.mirea.gracheva.domain.models.Ring;
import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.databinding.ItemCardRingBinding;

public class RingsAdapter extends RecyclerView.Adapter<RingsAdapter.RingViewHolder>{
    private List<Ring> rings;

    public interface OnRingClickListener{
        public void onRingClick(String ringId);
    }

    private final OnRingClickListener listener;

    public RingsAdapter(List<Ring> rings, OnRingClickListener listener) {
        this.rings = rings;
        this.listener = listener;
    }

    public static class RingViewHolder extends RecyclerView.ViewHolder {
        private final ItemCardRingBinding binding;

        public RingViewHolder(ItemCardRingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Ring ring) {
            binding.nameTextView.setText(ring.getName());
            binding.priceTextView.setText(ring.getPrice() + " руб.");

            Picasso.get()
                    .load(ring.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .fit()
                    .into(binding.ringImageView);
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
        Ring ring = rings.get(position);
        holder.bind(ring);


        holder.itemView.setOnClickListener(v->{
            listener.onRingClick(ring.getRingId());
        });
    }
    @Override
    public int getItemCount() {
        return rings.size();
    }
}
