# publish into local m2 folder (tested)
./gradlew publishToMavenLocal

# --spring.config.location : directory with / at the end
# --spring.config.name : base file name
./gradlew bootJar bootRun --args='--spring.config.location=file:///full_path/ --spring.config.name=base_filename'

java -jar demo.jar --spring.config.location=file:///full_path/ --spring.config.name=base_filename

fixes select2 height :
1. select2.full.min.js
2. <span class="select2-selection" role="combobox"
3. add style="height: 38px;"

final result should be like this
<span class="select2-selection" role="combobox" style="height: 38px;"