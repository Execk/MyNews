package com.lescour.ben.mynews.controller.fragment;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.lescour.ben.mynews.R;
import com.lescour.ben.mynews.model.Article;
import com.lescour.ben.mynews.model.Multimedium;

import java.util.List;

public class ArticleSearchRecyclerViewAdapter extends RecyclerView.Adapter<ArticleSearchRecyclerViewAdapter.ViewHolder> {

    private final List<Article> articles;
    private final BaseFragment.OnListFragmentInteractionListener mListener;
    private RequestManager glide;

    public ArticleSearchRecyclerViewAdapter(List<Article> articles, BaseFragment.OnListFragmentInteractionListener listener, RequestManager glide) {
        this.articles = articles;
        mListener = listener;
        this.glide = glide;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.article = articles.get(position);
        holder.articleImg.setImageResource(R.drawable.ic_launcher_background);
        /**if (holder.article.getMultimedia().isEmpty()) {
            holder.articleImg.setImageResource(R.drawable.ic_launcher_background);
        } else {
            String imgUrl = "https://static01.nyt.com/" + holder.article.getMultimedia().get(9).getUrl();
            holder.showArticleImg(imgUrl, this.glide);
        }*/
        holder.articleSectionSubsection.setText(getSectionAndSubsection(holder.article));
        holder.articleDate.setText(getDateWhitNewFormat(holder.article));
        holder.articleDescription.setText(holder.article.getHeadline().getMain());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.article);
                }
            }
        });
    }

    private String getSectionAndSubsection(Article article) {
        String str;
        if (article.getSubsectoinName() == null) {
            str = article.getSectionName();
        } else {
            str = article.getSectionName() + " > " + article.getSubsectoinName();
        }
        return str;
    }

    private String getDateWhitNewFormat(Article article) {
        String rawDate = article.getPubDate();
        String year = rawDate.substring(0,4);
        String month = rawDate.substring(5,7);
        String day = rawDate.substring(8,10);
        return day + "/" + month + "/" + year;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.img_article) ImageView articleImg;
        @BindView(R.id.article_sectionSubsection) TextView articleSectionSubsection;
        @BindView(R.id.article_date) TextView articleDate;
        @BindView(R.id.article_title) TextView articleDescription;
        public Article article;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        public void showArticleImg(String imgUrl, RequestManager glide) {
            glide.load(imgUrl).into(articleImg);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + articleDate.getText() + "'";
        }
    }
}
