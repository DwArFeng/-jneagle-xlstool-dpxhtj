@echo off

cd ..
java -classpath "lib\*;libext\*" ^
-Dlog.dir=".\logs" ^
-Dlog.consoleEncoding=GBK ^
-Dlog.fileEncoding=UTF-8 ^
${mainClass}

@pause
