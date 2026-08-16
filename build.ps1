Set-Location $PSScriptRoot
Write-Host "正在构建大狗叫..." -ForegroundColor Green

# 清理旧文件
Write-Host "正在清理旧文件..." -ForegroundColor Yellow
if (Test-Path ./build) {
    Remove-Item -Recurse -Force ./build
    Write-Host "旧文件已清理。" -ForegroundColor Green
}
# 创建build目录
Write-Host "正在创建build目录..." -ForegroundColor Yellow
New-Item -ItemType Directory -Path ./build/classes | Out-Null
New-Item -ItemType Directory -Path ./build/jar | Out-Null

# 编译源码
Write-Host "正在编译源码..." -ForegroundColor Yellow
& 'C:/Program Files/Javas/JDK25/bin/javac' `
--release 25 `
-d ./build/classes `
-sourcepath ./src/java/cn/yuang2714/dagou_recyclebin/ `
./src/java/cn/yuang2714/dagou_recyclebin/*.java
if ($LASTEXITCODE -ne 0) {
    Write-Host "编译失败。退出码：${$LASTEXITCODE}" -ForegroundColor Red
    exit $LASTEXITCODE
}


# 创建jar包
Write-Host "正在创建jar包..." -ForegroundColor Yellow
& 'C:/Program Files/Javas/JDK25/bin/jar' `
cvfe `
./build/jar/DagouRecycleBin.jar `
Main `
-C ./build/classes/ . `
-C ./src/resources/ .
if ($LASTEXITCODE -ne 0) {
    Write-Host "jar失败。退出码：${$LASTEXITCODE}" -ForegroundColor Red
    exit $LASTEXITCODE
}

#用jpackage打包成exe
Write-Host "正在打包成exe..." -ForegroundColor Yellow
& 'C:/Program Files/Javas/JDK25/bin/jpackage' `
--verbose `
--input ./build/jar `
--main-jar DagouRecycleBin.jar `
--name DagouRecycleBin `
--dest ./build/libs `
--app-version "1.0.0" `
--copyright MIT `
--description "一只会吃文件的大狗。" `
--vendor None `
--main-class Main `
--type app-image `
--resource-dir ./src/resources
if ($LASTEXITCODE -ne 0) {
    Write-Host "打包失败。退出码：${$LASTEXITCODE}" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "正在生成压缩包..." -ForegroundColor Yellow
& 'C:\Program Files\7-Zip\7z.exe' `
a `
-mx7 `
build.zip `
.\build\libs\DagouRecycleBin
if ($LASTEXITCODE -ne 0) {
    Write-Host "压缩失败。退出码：${$LASTEXITCODE}" -ForegroundColor Red
    exit $LASTEXITCODE
}
Move-Item ./build.zip ./build/libs/

Write-Host "构建完成" -ForegroundColor Green