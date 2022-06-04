# publish into local m2 folder (tested)
./gradlew publishToMavenLocal

# --spring.config.location : directory with / at the end
# --spring.config.name : base file name
./gradlew bootJar bootRun --args='--spring.config.location=file:///full_path/ --spring.config.name=base_filename'

java -jar demo.jar --spring.config.location=file:///full_path/ --spring.config.name=base_filename

