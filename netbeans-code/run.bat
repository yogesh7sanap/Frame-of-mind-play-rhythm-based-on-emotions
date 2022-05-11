@echo off
taskkill /IM python.exe /F
start "back " python C:\\Python35\\keras_model_face\\standalone_server_face.py "C:\\Python35\\keras_model_face\\"
echo "Main Program Starts"
java -classpath ./lib/*.jar;./javacv-platform-1.4.3-bin-windows/javacv-bin/*.jar;./dist/FacePlayer.jar com.gui.Main

pause