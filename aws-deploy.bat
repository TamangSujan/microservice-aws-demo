@echo off
setlocal enabledelayedexpansion

REM Stop execution if any command fails
set "ERRORLEVEL=0"

aws --endpoint-url=http://localhost:4566 cloudformation --delete-stack ^
    --stack-name aws-template

if %ERRORLEVEL% neq 0 (
    echo Error: CloudFormation aws-template delete failed.
    exit /b %ERRORLEVEL%
)

aws --endpoint-url=http://localhost:4566 cloudformation deploy ^
    --stack-name aws-template ^
    --template-file ".\template\LocalStack.template.json"

if %ERRORLEVEL% neq 0 (
    echo Error: CloudFormation deployment failed.
    exit /b %ERRORLEVEL%
)

echo Script executed successfully.
exit /b 0