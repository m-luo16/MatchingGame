package me.powerchelle.productmatch;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private Context context;
    private List<Product> products;
    private int flipped = 0;
    private String flippedId;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, List<Product> products) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_front, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_front);
            imageView = itemView.findViewById(R.id.icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (!products.get(getAdapterPosition()).getFlipped()) {
                Glide.with(v).load(products.get(getAdapterPosition()).getImgSrc()).into(imageView);
                cardView.setCardBackgroundColor(context.getColor(R.color.colorAccent));
                products.get(getAdapterPosition()).setFlipped(true);
            } else {
                cardView.setCardBackgroundColor(context.getColor(R.color.cardview_dark_background));
                products.get(getAdapterPosition()).setFlipped(false);
                imageView.setImageResource(R.drawable.shopify_bag);
            }
        }
    }

    Product getProduct(int pos) {
        return this.products.get(pos);
    }
}
