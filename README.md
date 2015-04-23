# Leslie
Yet another view binder for android applications.

<img src="http://uproxx.files.wordpress.com/2013/09/leslie-knope-dance.gif" width="25%" height="25%">


# Adding Library as a Dependency - Gradle
The easiest way to add `Leslie` to your project is via Gradle, you just need to add the following dependency to your `build.gradle`:

     dependencies {  
        repositories {
           mavenCentral()
         }
        compile 'com.github.vinitius:leslie:+'
       }

# Adding Library as a Dependency - Eclipse <img src="http://25.media.tumblr.com/35a8cb9885a9b9395752761839e5bd11/tumblr_mh5l0vxW9p1qd6sgco1_250.gif" width="10%" height="10%">
I guess you can always copy the `java` files and paste them in your project or you can build the project with gradle and grab its `aar` output to add in your buildpath.

# Usage
`Leslie` will perform an automatic view bind for your `Activity`, `Fragment` or whatever based on each view's tag defined in your `xml` layout. Not so clear, right? Let's see some examples:

Consider this file as a valid layout for some activity. Let's call it: `main_activity.xml`

```xml
    <TextView
        android:id="@+id/txt"
        android:text="@string/hello_world" android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:tag="mTxtHello"
        />
    <ListView
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:tag="mList"
        ></ListView>
```

Now, consider the following activity: `MainActivity.java`

```java
    private TextView mTxtHello;
    private ListView mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Leslie.with(context).bind(R.layout.main_activity).to(this);
        
        //Now , just do whatever you want with all the views inside your layout. No setContentView,no findViewById,no annotations...
        Toast.makeText(this,mTxtHello.getTag().toString(),Toast.LENGHT_LONG).show();
        Toast.makeText(this,mList.getTag().toString(),Toast.LENGHT_LONG).show();
    }
```

Now, consider the following fragment: `MainFragment.java`

```java
    private TextView mTxtHello;
    private ListView mList;
    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState) {
        
        View v = Leslie.with(context).bind(R.layout.main_activity).to(this);
        
        //Now , just do whatever you want with all the views inside your layout. No setContentView,no findViewById,no annotations...
        Toast.makeText(this,mTxtHello.getTag().toString(),Toast.LENGHT_LONG).show();
        Toast.makeText(this,mList.getTag().toString(),Toast.LENGHT_LONG).show();
        
        return v;
    }
```

**Note**: The `tag` attribute of each view must be exactly the same name of the corresponding `field` in the `class`

**Note 2**: After `Leslie` is done with the binding , you can do whatever you want with the `tag` attribute in each view

**Note 3**: All views you want to bind from the `xml` must contain a valid `id` attribute, as it has always been. No changes here.

# Leslie/UI
If you have no problem extending new `classes`, `Leslie` also offers an easier way:
 - `LeslieActivity` (abstract class which uses abstract method `getLayoutResource()` along with `Leslie`.)
 ```java
 public class MainActivity extends LeslieActivity{

    private TextView mTxtHello;
    private ListView mList;
    
    @Override
    public int getLayoutResource() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,mTxtHello.getTag().toString(),Toast.LENGHT_LONG).show();
        Toast.makeText(this,mList.getTag().toString(),Toast.LENGHT_LONG).show();
        
    }
}
 ```
 
 - `LeslieFragment` (abstract class which uses abstract method `getLayoutResource()` along with `Leslie`.)
 ```java
 public class MainFragment extends LeslieFragment{

    private TextView mTxtHello;
    private ListView mList;
    
    @Override
    public int getLayoutResource() {
        return R.layout.main_activity;
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState) {
        super.onCreateView(inflater,parent,savedInstanceState);
        Toast.makeText(this,mTxtHello.getTag().toString(),Toast.LENGHT_LONG).show();
        Toast.makeText(this,mList.getTag().toString(),Toast.LENGHT_LONG).show();
        
        return getPostBindView();
        
    }
}
 ```
 
 - `LeslieSingleBaseAdapter` (abstract class which provides default implementation of `BaseAdapter` and the view holder pattern using `Leslie`.)
 ```java
 public class ModelAdapter extends LeslieSingleBaseAdapter<Model>{

    private TextView mTitle;
    private TextView mName;

    public ModelAdapter(Context context, List collection) {
        super(context, collection);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.model_item;
    }

    @Override
    public void setValuesForViewHolderItem(Model item, int position, View convertView, ViewGroup parent) {

        mTitle.setText(item.getTitle());
        mName.setText(item.getName());

    }


}
 ```
   - In this case, the `Model` class could be a POJO like this:
  ```java
  public class Model implements Serializable{


    private String name;
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
  ```
   - And the `model_item.xml` would be:
  ```xml
  <TextView
        android:id="@+id/name"
        android:text="@string/hello_world" android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:tag="mName"
        />
    <TextView
        android:id="@+id/title"
        android:text="@string/hello_world" android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:tag="mTitle"
        />
  ```
  
   - And finally, set the adapter as you always have:
  ```java
  ...
  ModelAdapter adapter = new ModelAdapter(context,yourListOfModels);
  mList.setAdapter(adapter);
  ...
  ```
  
# License    
      Copyright 2015 Vinítius Salomão

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

  
  
  
