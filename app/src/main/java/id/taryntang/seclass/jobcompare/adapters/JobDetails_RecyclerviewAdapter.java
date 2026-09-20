package id.taryntang.seclass.jobcompare.adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.taryntang.seclass.jobcompare.QuarterListView;
import id.taryntang.seclass.jobcompare.R;
import id.taryntang.seclass.jobcompare.models.JobDetails;

public class JobDetails_RecyclerviewAdapter extends RecyclerView.Adapter<JobDetails_RecyclerviewAdapter.JobDetailsViewHolder> {
    Context context;
    ArrayList<JobDetails> jobDetailsArrayList;
    String[] jobDetails;
    String[] placeholders, jobDetailValues;

    private static final int STATE_POSITION = 3;
    private static final String[][] US_STATES = {
            {"Alabama", "AL"}, {"Alaska", "AK"}, {"Arizona", "AZ"}, {"Arkansas", "AR"},
            {"California", "CA"}, {"Colorado", "CO"}, {"Connecticut", "CT"}, {"Delaware", "DE"},
            {"District of Columbia", "DC"}, {"Florida", "FL"}, {"Georgia", "GA"}, {"Hawaii", "HI"},
            {"Idaho", "ID"}, {"Illinois", "IL"}, {"Indiana", "IN"}, {"Iowa", "IA"},
            {"Kansas", "KS"}, {"Kentucky", "KY"}, {"Louisiana", "LA"}, {"Maine", "ME"},
            {"Maryland", "MD"}, {"Massachusetts", "MA"}, {"Michigan", "MI"}, {"Minnesota", "MN"},
            {"Mississippi", "MS"}, {"Missouri", "MO"}, {"Montana", "MT"}, {"Nebraska", "NE"},
            {"Nevada", "NV"}, {"New Hampshire", "NH"}, {"New Jersey", "NJ"}, {"New Mexico", "NM"},
            {"New York", "NY"}, {"North Carolina", "NC"}, {"North Dakota", "ND"}, {"Ohio", "OH"},
            {"Oklahoma", "OK"}, {"Oregon", "OR"}, {"Pennsylvania", "PA"}, {"Rhode Island", "RI"},
            {"South Carolina", "SC"}, {"South Dakota", "SD"}, {"Tennessee", "TN"}, {"Texas", "TX"},
            {"Utah", "UT"}, {"Vermont", "VT"}, {"Virginia", "VA"}, {"Washington", "WA"},
            {"West Virginia", "WV"}, {"Wisconsin", "WI"}, {"Wyoming", "WY"}};

    public JobDetails_RecyclerviewAdapter (Context context, ArrayList<JobDetails> jobDetailsArrayList) {
        this.context = context;
        this.jobDetailsArrayList = jobDetailsArrayList;
    }
    @NonNull
    @Override
    public JobDetails_RecyclerviewAdapter.JobDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.job_details_row, parent, false);
        return new JobDetails_RecyclerviewAdapter.JobDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobDetails_RecyclerviewAdapter.JobDetailsViewHolder holder, int position) {
        holder.detailLbl.setText(jobDetails[position]);
        if (jobDetailsArrayList.isEmpty()) {
            holder.enterTxtView.setHint(placeholders[position]);
            holder.enterTxtView.getText().clear();
        } else {
            holder.enterTxtView.setText(jobDetailValues[position]);
        }
        bindStateDropdown(holder.enterTxtView, position == STATE_POSITION);
    }

    // The State row is picked from a list of US states instead of typed.
    private void bindStateDropdown(EditText field, boolean isStateRow) {
        field.setFocusable(!isStateRow);
        field.setFocusableInTouchMode(!isStateRow);
        field.setCursorVisible(!isStateRow);
        field.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                isStateRow ? android.R.drawable.arrow_down_float : 0, 0);
        if (!isStateRow) {
            field.setOnClickListener(null);
            return;
        }
        field.setHint("Select State");
        field.setOnClickListener(view -> {
            String[] names = new String[US_STATES.length];
            int selected = -1;
            String current = field.getText().toString().trim();
            for (int i = 0; i < US_STATES.length; i++) {
                names[i] = US_STATES[i][0] + " (" + US_STATES[i][1] + ")";
                if (US_STATES[i][1].equalsIgnoreCase(current)) {
                    selected = i;
                }
            }
            QuarterListView list = new QuarterListView(context);
            list.setAdapter(new ArrayAdapter<>(context,
                    android.R.layout.simple_list_item_single_choice, names));
            list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // branded scroll bar that stays visible
            list.setScrollbarFadingEnabled(false);
            list.setScrollBarSize((int) (8 * context.getResources().getDisplayMetrics().density));
            list.setVerticalScrollbarThumbDrawable(
                    ContextCompat.getDrawable(context, R.drawable.scrollbar_thumb));
            list.setVerticalScrollbarTrackDrawable(
                    ContextCompat.getDrawable(context, R.drawable.scrollbar_track));
            list.setVerticalScrollBarEnabled(true);
            if (selected >= 0) {
                list.setItemChecked(selected, true);
                list.setSelection(selected);
            }
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Select State")
                    .setView(list)
                    .setNegativeButton("Cancel", null)
                    .create();
            list.setOnItemClickListener((parent, itemView, which, id) -> {
                field.setText(US_STATES[which][1]);
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        jobDetails = context.getResources().getStringArray(R.array.job_details_fields);
        placeholders = context.getResources().getStringArray(R.array.job_details_placeholders);
        if (!jobDetailsArrayList.isEmpty()) {
            JobDetails jobDetail = jobDetailsArrayList.get(0);
            jobDetailValues = new String[]{jobDetail.getTitle(), jobDetail.getCompany(),
                    jobDetail.getCity(), jobDetail.getState(),
                    String.valueOf(jobDetail.getSalary()), String.valueOf(jobDetail.getBonus()),
                    String.valueOf(jobDetail.getLeaveTime()), String.valueOf(jobDetail.getNoOfShares()),
                    String.valueOf(jobDetail.getHomeBuyingFund()), String.valueOf(jobDetail.getWellnessFund())};
        }
        return jobDetails.length;
    }

    public static class JobDetailsViewHolder extends RecyclerView.ViewHolder {

        EditText detailLbl;
        EditText enterTxtView;

        public JobDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            detailLbl = itemView.findViewById(R.id.editTextTextPersonName);
            enterTxtView = itemView.findViewById(R.id.enterTitleTextViewId);
        }
    }
}
