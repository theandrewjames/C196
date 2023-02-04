package com.example.c196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>  {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView=itemView.findViewById(R.id.assessmentListTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessments current = mAssessments.get(position);
                    Intent intent = new Intent(context, AddAssessment.class);
                    intent.putExtra("assessmentID", current.getAssessmentId());
                    intent.putExtra("courseID", current.getCourseId());
                    intent.putExtra("name", current.getTitle());
                    intent.putExtra("start", current.getStartDate());
                    intent.putExtra("end", current.getEndDate());
                    intent.putExtra("type", current.getType());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessments> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context) {
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessments current = mAssessments.get(position);
            String title = current.getTitle();
            holder.assessmentItemView.setText(title + " , Course ID:" + current.getCourseId());
        } else {
            holder.assessmentItemView.setText("No available assessments");
        }
    }
    public void setAssessments(List<Assessments> assessments) {
        mAssessments=assessments;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mAssessments != null) {
            return mAssessments.size();
        }
        else return 0;
    }
}
