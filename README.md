# android-pin - Easily create multiple PIN views

A simple JAVA class that can create multiple PIN views in a layout and auto executes code on entering PIN

## Installation
1. Copy files from `res` folder to your specific folders in your project
2. In your JAVA file, create a Dialog and inflate the `pin_view.xml`

## Usage
### 1. Creating PIN

```
private PINClass pinClass;
private Dialog pinDialog;
...

dialogPin = new Dialog(MainActivity.this);
showPinView();

...

private void showPinView() {
    dialogPin.setContentView(R.layout.pin_view);
    dialogPin.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    dialogPin.setCancelable(false);

    TextView closeDialog = dialogPin.findViewById(R.id.closePinScreen);

    closeDialog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogPin.dismiss();
        }
    });

    pinClass = new PINClass(this, dialogPin, new Callable() {
        @Override
        public Object call() {
            submitPIN();
            return null;
        }
    });

    dialogPin.show();

    // force to display keyboard on Dialog window
    Window window = dialogPin.getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
}
```
**NOTE:** You can keep the last argument as null, if you don't want any code to be auto executed

### 2. Other Functions

- `pinClass.getText();`
- `pinClass.clearPin();`
- `pinClass.getFocus();`

## Credits
This project is based on [Parveshdhull](https://github.com/Parveshdhull/android-pin)