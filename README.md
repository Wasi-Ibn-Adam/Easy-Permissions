Easy-Permissions
===============

A fast easy and perfect way to handle permissions. 

To handle permissions their are two ways:

*   WPremission
*   WCompatActivity

WPremission
-----------
```
   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
       
        WPermission permission=new WPermission(MainActivity.this,resultListener,requestListener);
        
        permission.request().single().camera(12);

    }
```
 Also to get result just add this line and then handle the working of each permission in resultListener, requestListener definiations.
```
  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        permission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
    }
``` 

WCompatActivity
---------------
Using WCompatActivity is even more easy to use

extend your activity with WCompatActivity and call request() function no more headache 

```
public class MainActivity extends WCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        request().single().camera(12);
        
    }
```

and response of the permission can seen by overloading the callbacks for example
```
    @Override
    public void onGrantedAll(@NonNull String[] pers, int rCode) {
        super.onGrantedAll(pers, rCode);
    }

    @Override
    public void onDeniedAll(@NonNull String[] pers, int rCode) {
        super.onDeniedAll(pers, rCode);
    }
```

There are a number of callbacks, which can be used for different purposes 

all callbacks Include:

*  firstTimeRequested(@NonNull String[] pers, int rCode)
*  curGranted(@NonNull String[] pers, int rCode)
*  prevGranted(@NonNull String[] pers, int rCode)
*  onGrantedAll(@NonNull String[] pers, int rCode)
*  curDenied(@NonNull String[] pers, int rCode)
*  prevDenied(@NonNull String[] pers, int rCode)
*  onDeniedAll(@NonNull String[] pers, int rCode)
*  onComplete(int rCode)
*  unableToAskPermission(@NonNull String[] pers, int rCode)  // NEVER ASK AGAIN
*  rationalPopUpCancelled(int rCode)
*  onError(String message)

Gradle
------
```
dependencies {
    ...
    implementation 'com.github.Wasi-Ibn-Adam:Easy-Permissions:1.0.1'
}
```


