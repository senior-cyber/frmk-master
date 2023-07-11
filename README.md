## publish into local m2 folder
```shell
./gradlew publishToMavenLocal
```

```shell
fixes select2 height :
1. select2.full.min.js
2. <span class="select2-selection" role="combobox"
3. add style="height: 38px;"

final result should be like this
<span class="select2-selection" role="combobox" style="height: 38px;"
```
