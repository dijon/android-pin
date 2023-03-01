import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import java.util.concurrent.Callable;

public class PINClass implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {

    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinFifthDigitEditText;
    private EditText mPinSixthDigitEditText;
    private EditText mPinHiddenEditText;
    private Activity activity;
    private Context context;
    private ViewGroup insertPoint;
    private Callable callable;
    private Dialog dialog;

    public PINClass(Context context, Dialog dialog, Callable callable) {
        this.context = context;
        this.activity = getActivity(context);
        this.callable = callable;
        this.dialog = dialog;
        init();
        setPINListeners();
    }

    private void init() {
        mPinFirstDigitEditText = dialog.findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = dialog.findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = dialog.findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = dialog.findViewById(R.id.pin_forth_edittext);
        mPinFifthDigitEditText = dialog.findViewById(R.id.pin_fifth_edittext);
        mPinSixthDigitEditText = dialog.findViewById(R.id.pin_sixth_edittext);
        mPinHiddenEditText = dialog.findViewById(R.id.pin_hidden_edittext);
        getFocus();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getFocus();
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            switch (id) {
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        clearPin();
                    }
                    break;
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setDefaultPinBackground(mPinFirstDigitEditText);
        setDefaultPinBackground(mPinSecondDigitEditText);
        setDefaultPinBackground(mPinThirdDigitEditText);
        setDefaultPinBackground(mPinForthDigitEditText);
        setDefaultPinBackground(mPinFifthDigitEditText);
        setDefaultPinBackground(mPinSixthDigitEditText);
        if (s.length() == 0) {
            setFocusedPinBackground(mPinFirstDigitEditText);
            mPinFirstDigitEditText.setText("");
            mPinSecondDigitEditText.setText("");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 1) {
            setFocusedPinBackground(mPinSecondDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText("");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 2) {
            setFocusedPinBackground(mPinThirdDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 3) {
            setFocusedPinBackground(mPinForthDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 4) {
            setFocusedPinBackground(mPinFifthDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText(s.charAt(3) + "");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 5) {
            setFocusedPinBackground(mPinSixthDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText(s.charAt(3) + "");
            mPinFifthDigitEditText.setText(s.charAt(4) + "");
            mPinSixthDigitEditText.setText("");
        }
        else if (s.length() == 6) {
            setDefaultPinBackground(mPinSixthDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText(s.charAt(3) + "");
            mPinFifthDigitEditText.setText(s.charAt(4) + "");
            mPinSixthDigitEditText.setText(s.charAt(5) + "");
            if (callable != null) {
                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getFocus() {
        if (mPinHiddenEditText == null)
            return;
        setFocusedPinBackground(mPinFirstDigitEditText);
        showKeyboard(context);
        mPinHiddenEditText.setFocusable(true);
        mPinHiddenEditText.setFocusableInTouchMode(true);
        mPinHiddenEditText.requestFocus();
    }

    private void setPINListeners() {
        mPinHiddenEditText.addTextChangedListener(this);

        mPinFirstDigitEditText.setOnFocusChangeListener(this);
        mPinSecondDigitEditText.setOnFocusChangeListener(this);
        mPinThirdDigitEditText.setOnFocusChangeListener(this);
        mPinForthDigitEditText.setOnFocusChangeListener(this);
        mPinFifthDigitEditText.setOnFocusChangeListener(this);
        mPinSixthDigitEditText.setOnFocusChangeListener(this);

        mPinFirstDigitEditText.setOnKeyListener(this);
        mPinSecondDigitEditText.setOnKeyListener(this);
        mPinThirdDigitEditText.setOnKeyListener(this);
        mPinForthDigitEditText.setOnKeyListener(this);
        mPinFifthDigitEditText.setOnKeyListener(this);
        mPinSixthDigitEditText.setOnKeyListener(this);
        mPinHiddenEditText.setOnKeyListener(this);
    }

    private void setDefaultPinBackground(EditText editText) {
        editText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorGray)));
    }

    private void setFocusedPinBackground(EditText editText) {
        editText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent)));
    }

    public String getText() {
        return mPinHiddenEditText.getText().toString();
    }

    public void clearPin() {
        mPinHiddenEditText.setText("");
        getFocus();
    }

    public void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private Activity getActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }
        return null;
    }
}
