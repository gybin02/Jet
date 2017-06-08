# Jet
标签注解库；
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[ ![Download](https://api.bintray.com/packages/gybin02/maven/jet/images/download.svg) ](https://bintray.com/gybin02/maven/jet/_latestVersion)

使用注解功能 来实现去除一些重复的模板代码，让Code更简单；

比如 类似 butterknife功能； 自动初始化Field

命名来自 WordPress的Jetpack;
![](https://github.com/gybin02/Jet/blob/master/image/jetback.jpg)

每个注解做的事情要很简单，符合kiss原则
## 已完成
### @JFindView([ViewId])

运行时注入，支持 自动初始化 View的findViewById 

### @JFindViewOnClick([ViewId])

支持 自动初始化View的 findViewById 和  onClick
> Activity 需要 implement View.OnClickListener.class;

### @JIntent([key])

 使用Annotation Runtime实现getIntent功能来读取Intent里面的数据；
 
> 支持自动从 Intent 里取值，比如Intent.getStringExtra([String]) 等 

> 支持的参数类型具体如下（包括默认值）

```java 
           return intent.getStringExtra(value)

           return intent.getCharExtra(value, '\0');
           
           return intent.getCharExtra(value, '\0');

            return intent.getByteExtra(value, (byte) 0);
   
            return intent.getShortExtra(value, (short) 0);
 
            return intent.getIntExtra(value, 0);

            return intent.getLongExtra(value, 0);
        
   
            return intent.getFloatExtra(value, 0);
        

            return intent.getDoubleExtra(value, 0);

            return intent.getBooleanExtra(value, false);

            return intent.getSerializableExtra(value);
     
            return intent.getBundleExtra(value);

            return intent.getStringArrayExtra(value);
            
            return intent.getStringExtra(value);
```
#### 使用例子
```java
    //原来代码 
   public class DemoActivity implement View.OnClickListener{
     //Android 代码

      private String testString;
      private boolean testBoolean;
      private int testInt;
      private long testLong;
    
      private Button btnHello;
      private Button btn_test;
    

      //用值
      public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
        //Intent 初始化
          Intent intent = getIntent();
          testString = intent.getIntExtra("testString", 0);
          testInt = intent.getStringExtra("testInt");
          testBoolean = intent.getStirngExtra("testBoolean");
          testLong = intent.getBooleanExtra("testLong");
         
         //View  初始化
        btnHello= findViewById(R.layout.btn_hello);
        btn_test= findViewById(R.layout.btn_test);
        btnHello.setOnClick(this);
        Log.i("old",test1);
      }
      
      public void onClick(View v){
          int id= v.getId;
          if(id== hello_world){
            //TODO Do Something
          }
      }

      //TODO:如果需要取参的越来越多，代码会是什么样的呢？逻辑上是不是很复杂
```
使用 Jet注解实现：
```java
    @JIntent("testString")
    private String testString;
    @JIntent("testBoolean")
    private boolean testBoolean;
    @JIntent("testInt")
    private int testInt;
    @JIntent("testLong")
    private long testLong;
    
    @JFindViwOnClick("btn_hello")
    private Button btnHello;
    @JFindViw("btn_test")
    private Button btn_test;
      
      //用值
      public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //初始化后，既可使用所有属性
        Jet.init(this);
      }
      
      public void onClick(View v){
          int id= v.getId;
          if(id== hello_world){
            //TODO Do Something
          }
      }

```
#### NON-ACTIVITY BINDING 

You can also perform binding on arbitrary objects by supplying your own view root.


你可以任意绑定对象，只要你提供一个View Root;
```java
public class FancyFragment extends Fragment {
  @JFindView(R.id.button1) Button button1;
  @JFindView(R.id.button2) Button button2;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fancy_fragment, container, false);
    Jet.bind(this, view);
    // TODO Use fields...
    return view;
  }
}

```
Another use is simplifying the view holder pattern inside of a list adapter.
```java
public class MyAdapter extends BaseAdapter {
  @Override public View getView(int position, View view, ViewGroup parent) {
    ViewHolder holder;
    if (view != null) {
      holder = (ViewHolder) view.getTag();
    } else {
      view = inflater.inflate(R.layout.whatever, parent, false);
      holder = new ViewHolder(view);
      view.setTag(holder);
    }

    holder.name.setText("John Doe");
    // etc...

    return view;
  }

  static class ViewHolder {
    @JFindView(R.id.title) TextView name;
    @JFindView(R.id.job_title) TextView jobTitle;

    public ViewHolder(View view) {
      Jet.bind(this, view);
    }
  }
}
You can see this implementation in action in the provided sample.


```
 

###  @JImplement

实现 根据接口类，自动调用实现类功能，类似`Summer`功能 比Summer简单；只需要一个注释，可以用于跨module功能调用，但是不仅于此 更多功能可以自己发掘；；使用Java 动态代理实现；2017.05.23 finish

#### 使用方法：
接口类
```java
@JImplement("com.seeker.tony.myapplication.proxy.TestImpl")
public interface ITest {
    public void  test();
    
    public String getValue();
}
 ```
实现类：
```java
//@JProvider 可有可无，只是用来标识实现类，避免被认为是无功能调用被删掉；
@JProvider
public class TestImpl {

    private static final String TAG = "TestImpl";

    public void test() {
        Log.d(TAG, "test Method  invoke");
    }

    public String getValue() {
        String str = "HelloWorld";
        Log.d(TAG, "getValue Method  invoke： " + str);
        return str;
    }
}
```
调用方法：
```java
                    ITest iTest = JetProxy.getInstance().create(ITest.class);
                    iTest.test();
                    iTest.getValue();
```

### Download
```groovy
dependencies {
   //内部使用
   //compile 'com.meiyou.framework:jet:0.0.7-SNAPSHOT'
   compile 'com.meiyou.framework:jet:1.0.0'

}
```
Snapshots of the development version are available in Sonatype's snapshots repository.

### 待实现，

待实现区域，列了一些我想到的通用功能， 但是项目里面肯定还存在很多通用的功能；欢迎 各位 提Issue，让项目更强大；

*  @JTrycatch  

AspectJ来实现
安全调用方法：给方法 自动加入 try Catch ;

已经实现，参考： [Jet-AOP](http://git.meiyou.im/Android/JetAop) 工程；

*  类似Retrofit的 RestFull 请求库实现；
@GET， @Post; @Head等； 
https://github.com/Tamicer/Tamic_Retrofit 

### 常见问题
* 性能测试；O(1)方法，20个@JFindView 属性初始化，耗时50ms;比直接FindViewById多花5ms,性能损耗基本可以忽略；
* Fragment实现； 已经支持 2017.0525
* ViewHolder实现； 已经支持 2017.0525
* 运行时注入，可以改为 编译时注入
* 有些注解实现 需要使用AOP技术；可以参考[Jet-AOP]（http://git.meiyou.im/Android/JetAop) 工程；
* 

### License

Copyright 2017 zhengxiaobin@xiaoyouzi.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
