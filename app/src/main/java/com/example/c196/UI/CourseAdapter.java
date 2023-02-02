package com.example.c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView=itemView.findViewById(R.id.courseListTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Courses current = mCourses.get(position);
                    Intent intent = new Intent(context, AddCourse.class);
                    intent.putExtra("id", current.getCourseId());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("start", current.getStartDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructor", current.getInstructorName());
                    intent.putExtra("phone", current.getInstructorPhone());
                    intent.putExtra("email", current.getInstructorEmail());
                    intent.putExtra("note", current.getNote());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Courses current = mCourses.get(position);
            String title = current.getCourseName();
            holder.courseItemView.setText(title + " Term:" + current.getTermId());
        } else {
            holder.courseItemView.setText("No available courses");
        }
    }
    public void setCourses(List<Courses> courses) {
        mCourses=courses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {

        if(mCourses != null) {
            return mCourses.size();
        }
        else return 0;
    }
}
