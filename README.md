# Dialog

提供可以快速建立`Dialog`


[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[ ![Download](https://api.bintray.com/packages/peng571/maven/tool-dialog/images/download.svg) ](https://bintray.com/peng571/pengyr_library/tool-dialog/_latestVersion)

## 取得方法

#### Gradle

if useing gradle, add this line into apps build.gradle

    implementation 'org.pengyr.tool:tool-dialog:[VERSION]'

#### Maven

> 還沒放上去

or useing maven

    <dependency>
        <groupId>org.pengyr.tool</groupId>
        <artifactId>tool-dialog</artifactId>
        <version>[VERSION]</version>
        <type>pom</type>
    </dependency>


## 使用方法

這邊介紹幾個常用的方法，詳情可以看sample裡面的範例。

### OkDialog

可以用一行程式碼就建立出一個含有訊息和確認按鈕的對話框。

```
	OkDialog dialog = OKDialog.newInstance(R.string.ok_dialog_message, OK_RESULT)
```

並可直接使用`show(activity | fragment)`來跳出`Dialog`。

```
	dialog.show(this);
```

也可以以這樣的形式填入自帶的確認文字。

```
	OKDialog.newInstance(
		R.string.ok_dialog_message, 
		R.string.ok_dialog_action, 
		OK_RESULT)
	.show(this);
```
 

### YesNoDialog

可以用一行就建立出一個帶有自訂訊息，與是與否按鈕的對話框。

```
    YesNoDialog.newInstance(R.string.yesno_dialog_message, YESNO_RESULT).show(this);
```

同樣的，也可以帶入自訂的是或否文字來提醒使用者。

```
    YesNoDialog.newInstance(
    	R.string.yesno_dialog_message, 
    	R.string.yesno_dialog_yes_action, 
    	R.string.yesno_dialog_no_action,
    	YESNO_RESULT)
    .show(this);
```


### DialogHolder

這邊用頁面來指`Activity`或者`Fragment`，並用原頁面來指呼叫該`Dialog`的頁面。當透過頁面來呼叫`Dialog`時，可以透過在原頁面實作以下幾個介面來取得`Dialog`的回應的`requestCode`。

> 目前只支援AppCompatActivity及繼承自他的Activity

其中傳回的`requestCode`，就是創建dialog時自訂的，如上面的`YESNO_RESULT`和`OK_RESULT`。
如果在一個頁面會同時使用多個dialog，則可以以自訂不同的`requestCode`來作為識別。

```
	switch(requestCode){
		case OK_RESULT:
			// do when is from ok dialog
			break;
		case YES_NO_RESILT:
			// do when is from yes no dialog
			break;
	}

```


#### DialogYesHolder

當`OkDialog`的確認紐，或者是`YesNoDialog`的主紐被按下時，會回應到原頁面的`DialogYesHolder.doOnDialogYesClick(requestCode)`

#### DialogNoHolder

當`YesNoDialog`的取消紐被按下時，會回應到原頁面的`DialogNoHolder.doOnDialogNoClick(requestCode)`。

#### DialogFinishHolder

##### doOnDialogCancel

當Dialog被使用指按下back鍵，或者是按下dialog以外的畫面時，則會取消dialog，如同一般的dialog。
同樣的也可以`setCancelable(boolean)`來設定是否允許使用者取消。
取消時會回應到原頁面的`DialogFinishHolder.doOnDialogCancel(requestCode)`，其中要注意的是，若原頁面沒有特別使用DialogNoHolder，則當YesNoDialog按下副紐等同於直接取消。

##### doOnDialogDismiss

當dialog結束時呼叫，此套件下的每個dialog結束時都會呼叫`DialogFinishHolder.doOnDialogDismiss(requestCode)`


## PickerDialog

待補。
 







