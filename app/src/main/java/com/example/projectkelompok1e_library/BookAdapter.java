package com.example.projectkelompok1e_library;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<BookItem> result;
    private Activity activity;

    public BookAdapter(List<BookItem> result, Activity activity) {
        this.result = result;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(result.get(position));
    }

    @Override
    public int getItemCount() {
        return result != null ? result.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_author, tv_description;
        private ImageView posterView;
        private Button btn_baca;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_description = itemView.findViewById(R.id.tv_description);
            posterView = itemView.findViewById(R.id.posterView);
            btn_baca = itemView.findViewById(R.id.btn_baca);

            btn_baca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        BookItem bookItem = result.get(position);
                        Intent intent = new Intent(activity, PdfViewerActivity.class);
                        intent.putExtra("pdf_file_name", "laut_bercerita");
                        activity.startActivity(intent);
                    }
                }
            });
        }

        public void bind(BookItem bookItem) {
            tv_title.setText(bookItem.getTitle());
            tv_author.setText(bookItem.getAuthor());
            tv_description.setText(bookItem.getDescription());
            Glide.with(activity).load(bookItem.getImage_url()).into(posterView);
        }
    }
}
