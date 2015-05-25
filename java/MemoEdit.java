package com.example.cdsteer.memoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cdsteer on 30/03/15.
 */
public class MemoEdit extends Activity{
    private RadioGroup memoModeGroup;
    private RadioButton memoModeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getIntent().getExtras() == null) {
            addMemoView();
        } else {
            editMemoView();
        }
        RadioGroup radGrp = (RadioGroup) findViewById(R.id.memo_mode);
        radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case R.id.radio_low:
                        hideAlarm();
                        break;
                    case R.id.radio_medium:
                        hideAlarm();
                        break;
                    case R.id.radio_urgent:
                        showAlarm();
                        break;
                    default:
                        hideAlarm();
                        break;
                }
            }
        });
    }

    private void editMemoView() {
        setContentView(R.layout.memo_edit);
        final int id = getIntent().getIntExtra("MEMO_ID", 0);
        final Toast updateToast = Toast.makeText(this, "Memo Updated", Toast.LENGTH_SHORT);
        final Toast deleteToast = Toast.makeText(this, "Memo Deleted", Toast.LENGTH_SHORT);

        populateFields(id);

        final Button delButton = (Button) findViewById(R.id.del_button);
        delButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DbHelper.deleteMemo(id);
                deleteToast.show();
                finish();
            }
        });

        final Button canButton = (Button) findViewById(R.id.can_button);
        canButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        final Button updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] details = getDetails();
                DbHelper.updateMemo(id, details[0], details[1], details[2], details[3]);
                updateToast.show();
                finish();
            }
        });
    }

    private void addMemoView() {
        setContentView(R.layout.memo_add);
        hideAlarm();
        final Toast addToast = Toast.makeText(this, "Memo Added", Toast.LENGTH_SHORT);

        final Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] details = getDetails();
                DbHelper.addMemo(details[0], details[1], details[2], details[3]);
                addToast.show();
                finish();
            }
        });
    }

    private String[] getDetails() {
        String[] details;
        details = new String[4];
        EditText text = (EditText) findViewById(R.id.memo_content);
        details[0] = text.getText().toString().trim();
        memoModeGroup = (RadioGroup) findViewById(R.id.memo_mode);
        int selectedId = memoModeGroup.getCheckedRadioButtonId();
        memoModeButton = (RadioButton) findViewById(selectedId);
        details[1] = (String) memoModeButton.getText();
        if (details[1].equals("Urgent")) {
            text = (EditText) findViewById(R.id.date_input);
            details[2] = text.getText().toString().trim();
            text = (EditText) findViewById(R.id.alarm_input);
            details[3] = text.getText().toString().trim();
        }else{
            details[2] = ""; details[3] = "";
        }
        return details;
    }

    private void populateFields(int id) {
        EditText editText = (EditText)findViewById(R.id.memo_content);
        editText.setText(DbHelper.getMemoContent(id), TextView.BufferType.EDITABLE);

        RadioButton radioButton;
        String priority = DbHelper.getMemoPriority(id);
        if (priority.equals("Normal")){
            radioButton = (RadioButton) findViewById(R.id.radio_low);
            radioButton.setChecked(true);
            hideAlarm();
        } else if (priority.equals("Important")){
            radioButton = (RadioButton) findViewById(R.id.radio_medium);
            radioButton.setChecked(true);
            hideAlarm();
        } else if (priority.equals("Urgent")){
            radioButton = (RadioButton) findViewById(R.id.radio_urgent);
            radioButton.setChecked(true);
        }

        editText = (EditText)findViewById(R.id.date_input);
        editText.setText(DbHelper.getMemoDate(id), TextView.BufferType.EDITABLE);

        editText = (EditText)findViewById(R.id.alarm_input);
        editText.setText(DbHelper.getMemoTime(id), TextView.BufferType.EDITABLE);
    }

    private void hideAlarm(){
        TextView hiding = (TextView) findViewById(R.id.alarm_input);
        hiding.setVisibility(View.INVISIBLE);
        hiding = (TextView) (findViewById(R.id.date_input));
        hiding.setVisibility(View.INVISIBLE);
        hiding = (TextView) (findViewById(R.id.date_text));
        hiding.setVisibility(View.INVISIBLE);
        hiding = (TextView) (findViewById(R.id.time_text));
        hiding.setVisibility(View.INVISIBLE);
        hiding = (TextView) (findViewById(R.id.alarm_text));
        hiding.setVisibility(View.INVISIBLE);
    }

    private void showAlarm(){
        TextView hiding = (TextView) findViewById(R.id.alarm_input);
        hiding.setVisibility(View.VISIBLE);
        hiding = (TextView) (findViewById(R.id.date_input));
        hiding.setVisibility(View.VISIBLE);
        hiding = (TextView) (findViewById(R.id.time_text));
        hiding.setVisibility(View.VISIBLE);
        hiding = (TextView) (findViewById(R.id.date_text));
        hiding.setVisibility(View.VISIBLE);
        hiding = (TextView) (findViewById(R.id.alarm_text));
        hiding.setVisibility(View.VISIBLE);
    }

}
