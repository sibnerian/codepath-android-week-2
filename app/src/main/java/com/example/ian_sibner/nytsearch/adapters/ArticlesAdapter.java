package com.example.ian_sibner.nytsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ian_sibner.nytsearch.R;
import com.example.ian_sibner.nytsearch.models.Doc;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View articleView = inflater.inflate(R.layout.item_article, parent, false);
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Doc article = articles.get(position);

        // Set item views based on your views and data model
        viewHolder.articleMultimediaImageView.setImageResource(0);
        viewHolder.articleTitle.setText(article.getHeadline().getMain());
        if (!article.hasImage()) {
            viewHolder.articleMultimediaImageView.setVisibility(View.GONE);
            viewHolder.articleTitle.setTextSize(COMPLEX_UNIT_SP, 24);
            return;
        }
        Picasso
                .with(getContext())
                .load(article.getImageAddress())
                .fit().centerCrop().noFade()
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.articleMultimediaImageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView articleMultimediaImageView;
        public TextView articleTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            articleMultimediaImageView =
                    (ImageView) itemView.findViewById(R.id.articleMultimediaImageView);
            articleTitle = (TextView) itemView.findViewById(R.id.articleTitle);
        }
    }

    private List<Doc> articles;
    private Context context;

    public ArticlesAdapter(Context ctx, List<Doc> articles) {
        this.articles = articles;
        context = ctx;
    }

    private Context getContext() {
        return context;
    }


}
