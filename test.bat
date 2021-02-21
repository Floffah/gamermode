mkdir test
xcopy /Y target\GamerMode-0.0.1-shaded.jar test\GamerMode.jar
cd test
java -jar GamerMode.jar -gui
cd ../