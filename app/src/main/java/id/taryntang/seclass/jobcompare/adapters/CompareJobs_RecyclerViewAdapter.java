package id.taryntang.seclass.jobcompare.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import id.taryntang.seclass.jobcompare.JobComparisonSelectionActivity;
import id.taryntang.seclass.jobcompare.R;
import id.taryntang.seclass.jobcompare.models.JobToCompare;
import android.widget.RadioGroup.OnCheckedChangeListener;

// How to use recycler view and how to use it with adapter view
/* BEGIN CODE FROM (https://www.youtube.com/watch?v=Mc0XT58A1Z4&t=608s&ab_channel=PracticalCoding) */
public class CompareJobs_RecyclerViewAdapter extends RecyclerView.Adapter<CompareJobs_RecyclerViewAdapter.MyViewHolder> {

    View view;
    Context context;
    ArrayList<JobToCompare> jobsToCompare;
    // Only one job can be the current job; -1 means none.
    int currentJobPosition = -1;

    public CompareJobs_RecyclerViewAdapter(Context context, ArrayList<JobToCompare> jobsToCompare) {

        this.context = context;
        //sort the list that you are passing

        /* BEGIN CODE FROM (https://stackoverflow.com/questions/9109890/android-java-how-to-sort-a-list-of-objects-by-a-certain-value-within-the-object) */
        Collections.sort(jobsToCompare, new Comparator<JobToCompare>(){
            public int compare(JobToCompare job1, JobToCompare job2) {
                return Integer.valueOf(job2.getRank()).compareTo(Integer.valueOf(job1.getRank())); // To compare integer values
            }
        });
        /* BEGIN CODE FROM (https://stackoverflow.com/questions/9109890/android-java-how-to-sort-a-list-of-objects-by-a-certain-value-within-the-object) */

        this.jobsToCompare = jobsToCompare;

        for (int i = 0; i < jobsToCompare.size(); i++) {
            if (jobsToCompare.get(i).getJob().isCurrentJob()) {
                currentJobPosition = i;
                break;
            }
        }
        for (int i = 0; i < jobsToCompare.size(); i++) {
            jobsToCompare.get(i).getJob().setCurrentJob(i == currentJobPosition);
        }
    }

    @NonNull
    @Override
    public CompareJobs_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.job_comparison_row, parent, false);

        return new CompareJobs_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompareJobs_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning values to views we created in the recycler_view_row
        String title = Character.toString(jobsToCompare.get(position).getTitle().charAt(0));
        holder.jobOfferLetter.setText(title);
        holder.offerTitle.setText(jobsToCompare.get(position).getTitle());
        holder.offerCompany.setText(jobsToCompare.get(position).getCompany());
        String jobRankString = String.valueOf(jobsToCompare.get(position).getRank());
        holder.jobRank.setText(jobRankString);

        // Check whether this job has a checkmark checked
        /* BEGIN CODE FROM (https://gist.github.com/Binary-Finery/80973f7020ae0949ccb3c708ef67f389) */

        holder.selectToCompareCB.setChecked(jobsToCompare.get(position).getChecked());
        holder.selectToCompareCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean currentCheckState = jobsToCompare.get(holder.getAdapterPosition()).getChecked();
                currentCheckState = !currentCheckState;
                jobsToCompare.get(holder.getAdapterPosition()).setChecked(currentCheckState);

                holder.selectToCompareCB.setChecked(currentCheckState);
            }
        });

        /* END CODE FROM (https://gist.github.com/Binary-Finery/80973f7020ae0949ccb3c708ef67f389) */

        holder.currentJobRB.setChecked(holder.getAdapterPosition() == currentJobPosition);
        holder.currentJobRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newPosition = holder.getAdapterPosition();
                if (newPosition == RecyclerView.NO_POSITION || newPosition == currentJobPosition) {
                    return;
                }
                if (currentJobPosition != -1) {
                    jobsToCompare.get(currentJobPosition).getJob().setCurrentJob(false);
                }
                jobsToCompare.get(newPosition).getJob().setCurrentJob(true);
                currentJobPosition = newPosition;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        // The recycler view just wants to know number of items in total
        return jobsToCompare.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from our recycler_view_row layout file

        TextView jobOfferLetter;
        TextView offerTitle, offerCompany, jobRank;
        RadioButton currentJobRB;
        CheckBox selectToCompareCB;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            jobOfferLetter = itemView.findViewById(R.id.jobLetter);
            offerTitle = itemView.findViewById(R.id.jobTitle);
            offerCompany = itemView.findViewById(R.id.jobCompany);
            jobRank = itemView.findViewById(R.id.jobRank);
            currentJobRB = itemView.findViewById(R.id.currentJobRB);
            selectToCompareCB = itemView.findViewById(R.id.selectToCompareCB);

        }
    }
}

/* END CODE FROM (https://www.youtube.com/watch?v=Mc0XT58A1Z4&t=608s&ab_channel=PracticalCoding) */