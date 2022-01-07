# copy generated .aar file to app module
#
# command for run in cmd: powershell -ExecutionPolicy Bypass -File copy_sdk_to_app_windows.ps1
$currentDirectory = $MyInvocation.MyCommand.Path | Split-Path -Parent
New-Item -Path "$currentDirectory\app\libs" -ItemType Directory -Force
Copy-Item -Path "$currentDirectory\heart-rate-chicken-embryos-sdk\build\outputs\aar\heart-rate-chicken-embryos-sdk-debug.aar" -Destination "$currentDirectory\app\libs" -Force