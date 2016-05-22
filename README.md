# codelockandroid

Code Lock Android is and iOS home screen lock pattern.

![](https://github.com/arlindiDev/codelockandroid/blob/master/codelock.gif)

It basicly is used as a EditText except that it looks like a home screen lock.

How to use PinCodeView?
--------

XML
--------
```
    <codelock.PinCodeView
        android:id="@+id/pincodeview"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:layout_centerInParent="true" />
```

JAVA
--------
```

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PinCodeView pinCodeView = (PinCodeView) findViewById(R.id.pincodeview);
        pinCodeView.setOnCodeCompleteListener(new PinCodeView.OnCodeCompleteListener() {
            @Override
            public void onCodeComplete(PinCodeView pinCodeView1,String code) {
                if(!code.equals("asdf")){
                    pinCodeView1.wrongCode();
                }
            }
        });
    }
```
