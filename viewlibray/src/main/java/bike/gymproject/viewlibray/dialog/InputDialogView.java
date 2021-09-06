package bike.gymproject.viewlibray.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import bike.gymproject.viewlibray.R;

/**
 * Created by Admin
 * Date 2021/9/2
 */
public class InputDialogView extends AppCompatDialog implements View.OnClickListener {


    private TextView titleTv;

    private EditText inputEdit;

    private Button cancelBtn,confirmBtn;

    private InputDialogListener inputDialogListener;

    public void setInputDialogListener(InputDialogListener inputDialogListener) {
        this.inputDialogListener = inputDialogListener;
    }

    public InputDialogView(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_input_dialog);


        initViews();
    }

    private void initViews() {
        titleTv = findViewById(R.id.inputTitleTv);
        inputEdit = findViewById(R.id.inputEditTv);
        cancelBtn = findViewById(R.id.inputCancelBtn);
        confirmBtn = findViewById(R.id.inputConfirmBtn);
        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.inputCancelBtn){
            dismiss();
        }

        if(view.getId() == R.id.inputConfirmBtn){
            String inputStr = inputEdit.getText().toString();
            if(inputDialogListener != null)
                inputDialogListener.inputData(inputStr);
            dismiss();
        }
    }

    public void setTitleTv(String tv){
        titleTv.setText(tv);
    }

    public void setTitleTv(int id){
        titleTv.setText(id);
    }


    public void setInputTxt(String txt){
        if(!TextUtils.isEmpty(txt))
            inputEdit.setText(txt);
    }

    public void setCancelBtnStr(String str){
        cancelBtn.setText(str);
    }

    public void setCancelBtnStr(int rsId){
        cancelBtn.setText(rsId);
    }

    public void setConfirmBtnStr(String str){
        confirmBtn.setText(str);
    }

    public void setConfirmBtnStr(int rsId){
        confirmBtn.setText(rsId);
    }


    public void setHidStr(String str){
        inputEdit.setHint(str);
    }

    public void setHidStr(int rsId){
        inputEdit.setHint(rsId);
    }

    public interface InputDialogListener{
        void inputData(String str);
    }
}
